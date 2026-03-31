package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.SkeletonConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;


public class SkeletonEvents {

    public static void onSpawn(Skeleton skeleton, ServerLevel level) {
        if (CommonMobHandler.isInitialized(skeleton))
            return;
        CommonMobHandler.markInitialized(skeleton);

        SkeletonConfig config = ModConfigs.getSkeleton();

        applyAll(skeleton, config);

        // Boss
        BossUtil.tryApplyBoss(
                skeleton,
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
        CommonMobHandler.spawnMultiplier(skeleton, level, config.spawnMultiplier);
    }

    private static void applyAll(Skeleton skeleton, SkeletonConfig config) {

        RandomSource random = skeleton.level().getRandom();

        CommonMobHandler.applyCommonAttributes(
                skeleton,
                config.health,
                config.armor,
                0.0,
                config.attackDamage,
                0.0,
                config.movementSpeed,
                config.followRange,
                config.knockbackResistance,
                config.attackKnockback,
                0.6,
                0.08,
                config.glowing,
                config.CustomName,
                false,
                0,
                config.reinforcementChance
        );

        skeleton.setHealth((float) config.health);
    }

    public static void onTick(Skeleton skeleton) {

        SkeletonConfig config = ModConfigs.getSkeleton();

        CommonMobHandler.applyCommonTickBehaviors(
                skeleton,
                config.burnInDaylight,
                config.fireImmune,
                false,
                false,
                false,
                false
        );

        // Rapid fire
        if (config.rapidFire && skeleton.getTarget() != null) {
            skeleton.setAggressive(true);
        }
    }

    public static void onArrow(AbstractArrow arrow) {

        if (!(arrow.getOwner() instanceof Skeleton))
            return;

        SkeletonConfig config = ModConfigs.getSkeleton();

        if (config.bowPowerMultiplier != 1.0D) {
            double base = 2.0D;
            arrow.setBaseDamage(base * config.bowPowerMultiplier);
        }
    }

    public static float modifyXP(float xp) {
        return xp * (float) ModConfigs.getSkeleton().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, Skeleton skeleton) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                skeleton,
                ModConfigs.getSkeleton().lootMultiplier
        );
    }
}