package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.WitherSkeletonConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class WitherSkeletonEvents {

    private static final String WITHER_TAG = "mbc_wither_flag";

    public static void onSpawn(WitherSkeleton skeleton, ServerLevel level) {

        WitherSkeletonConfig config = ModConfigs.getWitherSkeleton();

        applyAll(skeleton, config);

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

        CommonMobHandler.spawnMultiplier(skeleton, level, config.spawnMultiplier);
    }

    private static void applyAll(WitherSkeleton skeleton, WitherSkeletonConfig config) {

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


    public static float modifyXP(float xp) {
        return xp * (float) ModConfigs.getWitherSkeleton().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, WitherSkeleton skeleton) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                skeleton,
                ModConfigs.getWitherSkeleton().lootMultiplier
        );
    }
}