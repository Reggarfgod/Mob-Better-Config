package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.CaveSpiderConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.BossUtil;
import com.reggarf.mods.mob_better_config.util.LootUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.spider.CaveSpider;


public class CaveSpiderEvents {

    public static void onSpawn(CaveSpider spider, ServerLevel level) {
        if (CommonMobHandler.isInitialized(spider))
            return;
        CommonMobHandler.markInitialized(spider);

        CaveSpiderConfig config = ModConfigs.getCaveSpider();

        CommonMobHandler.applyCommonAttributes(
                spider,
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

        CommonMobHandler.spawnMultiplier(
                spider,
                level,
                config.spawnMultiplier
        );
    }

    public static void onDrops(ServerLevel level, CaveSpider spider) {

        CaveSpiderConfig config = ModConfigs.getCaveSpider();

        LootUtil.applyLootMultiplier(
                null,
                level,
                spider,
                config.lootMultiplier
        );
    }
}