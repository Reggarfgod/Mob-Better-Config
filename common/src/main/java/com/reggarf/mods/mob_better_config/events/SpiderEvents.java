package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.SpiderConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.spider.Spider;


public class SpiderEvents {

    public static void onSpawn(Spider spider, ServerLevel level) {
        if (CommonMobHandler.isInitialized(spider))
            return;
        CommonMobHandler.markInitialized(spider);

        SpiderConfig config = ModConfigs.getSpider();
        applyAll(spider, config);
        BossUtil.tryApplyBoss(
                spider,
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
        CommonMobHandler.spawnMultiplier(spider, level, config.spawnMultiplier);
    }

    private static void applyAll(Spider spider, SpiderConfig config) {

        RandomSource random = spider.level().getRandom();

        CommonMobHandler.applyCommonAttributes(
                spider,
                config.health,
                config.armor,
                0.0,
                config.attackDamage,
                0.0,
                config.movementSpeed,
                config.followRange,
                0.0,
                config.attackKnockback,
                0.6,
                0.08,
                config.glowing,
                config.CustomName,
                config.canBreakDoors,
                config.doorBreakMode,
                config.reinforcementChance
        );

        spider.setHealth((float) config.health);
    }

    public static void onTick(Spider spider) {

        SpiderConfig config = ModConfigs.getSpider();

        CommonMobHandler.applyCommonTickBehaviors(
                spider,
                false,
                config.fireImmune,
                false,
                false,
                false,
                false
        );
    }

    public static float modifyXP(float xp) {
        return xp * (float) ModConfigs.getSpider().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, Spider spider) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                spider,
                ModConfigs.getSpider().lootMultiplier
        );
    }
}