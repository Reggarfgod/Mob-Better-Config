package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.GhastConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.data.MobData;
import com.reggarf.mods.mob_better_config.data.MobStats;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GhastEvents {

    private static final Map<UUID, Integer> chargeMap = new HashMap<>();
    private static final Map<UUID, Integer> cooldownMap = new HashMap<>();
    private static final String SPAWN_TAG = "mob_better_config_spawned";

    public static void onSpawn(Ghast ghast, ServerLevel level) {

        GhastConfig config = ModConfigs.getGhast();
        CommonMobHandler.applyCommonAttributes(
                ghast,
                config.health,
                config.armor,
                config.armorToughness,
                config.attackDamage,
                config.attackSpeed,
                config.movementSpeed,
                config.followRange,
                config.knockbackResistance,
                config.attackKnockback,
                config.stepHeight,
                config.gravity,
                config.glowing,
                config.CustomName,
                config.canBreakDoors,
                config.doorBreakMode,
                config.reinforcementChance
        );

        // Boss
        BossUtil.tryApplyBoss(
                ghast,
                config.bossMode,
                config.forceAllBoss,
                config.bossChance,
                config.bossHealthMultiplier,
                config.bossDamageMultiplier,
                config.bossGlowing,
                config.bossCustomName,
                config.bossXpMultiplier,
                config.bossLootMultiplier
        );

        // Spawn multiplier
        MobStats stats = MobData.get(ghast);
        if (!stats.spawned) {
            stats.spawned = true;

            CommonMobHandler.spawnMultiplier(
                    ghast,
                    level,
                    config.spawnMultiplier
            );
        }
    }


    public static void tick(Ghast ghast) {

        if (ghast.level().isClientSide())
            return;

        GhastConfig config = ModConfigs.getGhast();
        UUID id = ghast.getUUID();

        if (ghast.getTarget() == null) {

            chargeMap.remove(id);
            cooldownMap.remove(id);
            ghast.setCharging(false);

            if (ghast.tickCount % 40 == 0) {

                double range = config.floatRange;

                double offsetX = (ghast.getRandom().nextDouble() - 0.5) * 2 * range;
                double offsetY = (ghast.getRandom().nextDouble() - 0.5) * 2 * range;
                double offsetZ = (ghast.getRandom().nextDouble() - 0.5) * 2 * range;

                Vec3 direction = new Vec3(offsetX, offsetY, offsetZ).normalize();

                ghast.setDeltaMovement(
                        direction.scale(0.1D * config.floatSpeedMultiplier)
                );
            }

            return;
        }

        int cooldown = cooldownMap.getOrDefault(id, 0);
        if (cooldown > 0) {
            cooldownMap.put(id, cooldown - 1);
            return;
        }

        int charge = chargeMap.getOrDefault(id, 0) + 1;
        chargeMap.put(id, charge);

        int soundTick = (int) (config.totalChargeTime * config.chargeSoundPercent);

        if (charge == soundTick && !ghast.isSilent())
            ghast.level().levelEvent(null, 1015, ghast.blockPosition(), 0);

        ghast.setCharging(charge >= soundTick);

        if (charge >= config.totalChargeTime) {

            var target = ghast.getTarget();
            Vec3 view = ghast.getViewVector(1.0F);

            double dx = target.getX() - (ghast.getX() + view.x * 4.0);
            double dy = target.getY(0.5) - ghast.getY(0.5);
            double dz = target.getZ() - (ghast.getZ() + view.z * 4.0);

            LargeFireball fireball = new LargeFireball(
                    ghast.level(),
                    ghast,
                    new Vec3(dx, dy, dz).normalize(),
                    config.explosionPower
            );

            fireball.setPos(
                    ghast.getX() + view.x * 4.0,
                    ghast.getY(0.5) + 0.5,
                    ghast.getZ() + view.z * 4.0
            );

            fireball.setDeltaMovement(
                    fireball.getDeltaMovement()
                            .scale(config.fireballVelocityMultiplier)
            );

            ghast.level().levelEvent(null, 1016, ghast.blockPosition(), 0);
            ghast.level().addFreshEntity(fireball);

            chargeMap.put(id, 0);
            cooldownMap.put(id, config.cooldownAfterShot);
            ghast.setCharging(false);
        }
    }


    public static void onRemove(Ghast ghast) {

        UUID id = ghast.getUUID();

        chargeMap.remove(id);
        cooldownMap.remove(id);
    }


    public static float modifyFireballDamage(float damage) {

        GhastConfig config = ModConfigs.getGhast();

        return damage * (float) config.fireballDamageMultiplier;
    }

}