package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.PiglinBruteConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.BossUtil;
import com.reggarf.mods.mob_better_config.util.LootUtil;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;

public class PiglinBruteEvents {

    public static void onSpawn(PiglinBrute brute, ServerLevel level) {
        if (CommonMobHandler.isInitialized(brute))
            return;
        CommonMobHandler.markInitialized(brute);

        PiglinBruteConfig config = ModConfigs.getPiglinBrute();

        CommonMobHandler.applyCommonAttributes(
                brute,
                config.health,
                config.armor,
                0,
                config.attackDamage,
                0,
                config.movementSpeed,
                0,
                config.knockbackResistance,
                config.attackKnockback,
                0,
                0,
                config.glowing,
                config.customName,
                false,
                0,
                config.reinforcementChance
        );

        BossUtil.tryApplyBoss(
                brute,
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

        CommonMobHandler.spawnMultiplier(
                brute,
                level,
                config.spawnMultiplier
        );
    }

    public static float modifyXP(float xp) {

        return xp * (float) ModConfigs.getPiglinBrute().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, PiglinBrute brute) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                brute,
                ModConfigs.getPiglinBrute().lootMultiplier
        );
    }
}