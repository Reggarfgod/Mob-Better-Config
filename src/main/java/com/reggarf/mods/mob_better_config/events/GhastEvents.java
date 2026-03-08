package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.GhastConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GhastEvents {

    private static final Map<UUID, Integer> chargeMap = new HashMap<>();
    private static final Map<UUID, Integer> cooldownMap = new HashMap<>();

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Ghast ghast))
            return;

        if (!(ghast.level() instanceof ServerLevel level))
            return;

        GhastConfig config = ModConfigs.getGhast();

        // Remove vanilla shooting goal
        ghast.goalSelector.getAvailableGoals().removeIf(goal ->
                goal.getGoal().getClass().getSimpleName().contains("GhastShootFireballGoal")
        );

        // Remove vanilla random float goal
        ghast.goalSelector.getAvailableGoals().removeIf(goal ->
                goal.getGoal().getClass().getSimpleName().contains("RandomFloatGoal")
        );

        // Attributes
        if (ghast.getAttribute(Attributes.MAX_HEALTH) != null)
            ghast.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (ghast.getAttribute(Attributes.FOLLOW_RANGE) != null)
            ghast.getAttribute(Attributes.FOLLOW_RANGE)
                    .setBaseValue(config.followRange);

        if (ghast.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            ghast.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(config.reinforcementChance);

        if (ghast.getAttribute(Attributes.ARMOR) != null)
            ghast.getAttribute(Attributes.ARMOR).setBaseValue(config.armor);

        if (ghast.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            ghast.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(config.knockbackResistance);

        if (ghast.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            ghast.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(config.attackKnockback);


        ghast.setHealth((float) config.health);
        if (config.CustomName) {
            MobNameUtil.applyRandomName(ghast);
        }
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

            if (NbtUtil.getBooleanSafe(ghast.getPersistentData(), "mob_better_config_spawned"))
                return;
        {

            for (int i = 1; i < config.spawnMultiplier; i++) {

                Ghast extra = new Ghast(EntityType.GHAST, level);
                extra.snapTo(
                        ghast.getX(),
                        ghast.getY(),
                        ghast.getZ(),
                        ghast.getYRot(),
                        ghast.getXRot()
                );

                extra.getPersistentData().putBoolean("mbc_spawned", true);
                level.addFreshEntity(extra);
            }
        }
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Ghast ghast))
            return;

        if (ghast.level().isClientSide)
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

        int soundTick = (int)(config.totalChargeTime * config.chargeSoundPercent);

        if (charge == soundTick && !ghast.isSilent()) {
            ghast.level().levelEvent(null, 1015, ghast.blockPosition(), 0);
        }

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

    @SubscribeEvent
    public void onLeave(EntityLeaveLevelEvent event) {

        if (!(event.getEntity() instanceof Ghast ghast))
            return;

        UUID id = ghast.getUUID();
        chargeMap.remove(id);
        cooldownMap.remove(id);
    }

    @SubscribeEvent
    public void onFireballDamage(LivingDamageEvent.Pre event) {

        if (!(event.getSource().getDirectEntity() instanceof LargeFireball fireball))
            return;

        if (!(fireball.getOwner() instanceof Ghast))
            return;

        GhastConfig config = ModConfigs.getGhast();

        event.setNewDamage(
                event.getNewDamage() * (float) config.fireballDamageMultiplier
        );
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        XPUtil.applyXpIfInstance(
                event,
                Ghast.class,
                ModConfigs.getGhast().xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Ghast ghast))
            return;

        if (!(ghast.level() instanceof ServerLevel level))
            return;

        LootUtil.applyLootMultiplier(
                event,
                level,
                ghast,
                ModConfigs.getGhast().lootMultiplier
        );
    }

    @SubscribeEvent
    public void onDamaged(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Ghast ghast))
            return;

        if (!(ghast.level() instanceof ServerLevel level))
            return;

        ReinforcementUtil.trySpawnReinforcement(
                ghast,
                level,
                ModConfigs.getGhast().reinforcementChance,
                8
        );
    }
}