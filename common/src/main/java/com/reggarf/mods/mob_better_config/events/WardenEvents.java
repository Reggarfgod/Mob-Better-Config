package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.WardenConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.AABB;

public class WardenEvents {

    public static void onSpawn(Warden warden, ServerLevel level) {

        if (CommonMobHandler.isInitialized(warden))
            return;

        CommonMobHandler.markInitialized(warden);

        WardenConfig config = ModConfigs.getWarden();

        applyAll(warden, config);

        // Boss
        BossUtil.tryApplyBoss(
                warden,
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

        // Spawn multiplier (FIXED)
        CommonMobHandler.spawnMultiplier(warden, level, config.spawnMultiplier);
    }

    private static void applyAll(Warden warden, WardenConfig config) {

        CommonMobHandler.applyCommonAttributes(
                warden,
                config.health,
                config.armor,
                0.0,
                config.attackDamage,
                0.0,
                config.movementSpeed,
                config.followRange,
                config.knockbackResistance,
                config.attackKnockback,
                1.0, // step height (warden big)
                0.08,
                config.glowing,
                config.CustomName,
                false,
                0,
                0.0 // no reinforcement attr needed
        );

        warden.setHealth((float) config.health);

        if (config.fireImmune)
            warden.setRemainingFireTicks(0);
    }

    // Sonic boom damage
    public static float modifyDamage(Warden attacker, float damage) {

        WardenConfig config = ModConfigs.getWarden();

        if (config.sonicBoomDamageMultiplier == 1.0D)
            return damage;

        return (float) (damage * config.sonicBoomDamageMultiplier);
    }

    // Hurt logic
    public static void onHurt(Warden warden, ServerLevel level, DamageSource source) {

        WardenConfig config = ModConfigs.getWarden();

        LivingEntity attacker =
                source.getEntity() instanceof LivingEntity l ? l : null;

        if (attacker == null)
            return;

        warden.increaseAngerAt(
                attacker,
                config.defaultAnger + config.onHurtAngerBoost,
                false
        );
    }

    // Projectile anger
    public static void onProjectile(Warden warden, LivingEntity attacker) {

        WardenConfig config = ModConfigs.getWarden();

        warden.increaseAngerAt(
                attacker,
                config.projectileAnger,
                true
        );
    }

    public static void onTick(Warden warden, ServerLevel level) {

        WardenConfig config = ModConfigs.getWarden();

        // Darkness aura
        if (config.enableDarkness &&
                (warden.tickCount + warden.getId()) % config.darknessInterval == 0) {

            AABB box = new AABB(warden.blockPosition())
                    .inflate(config.darknessRadius);

            for (LivingEntity target :
                    level.getEntitiesOfClass(LivingEntity.class, box)) {

                if (target == warden)
                    continue;

                target.addEffect(new MobEffectInstance(
                        MobEffects.DARKNESS,
                        config.darknessDuration,
                        0,
                        false,
                        false
                ));
            }
        }

        // Sonic boom cooldown control
        if (warden.getTarget() != null &&
                warden.tickCount % config.sonicBoomInitialDelay == 0) {

            SonicBoom.setCooldown(warden, config.sonicBoomCooldown);
        }
    }

    public static float modifyXP(float xp) {
        return xp * (float) ModConfigs.getWarden().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, Warden warden) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                warden,
                ModConfigs.getWarden().lootMultiplier
        );
    }
}