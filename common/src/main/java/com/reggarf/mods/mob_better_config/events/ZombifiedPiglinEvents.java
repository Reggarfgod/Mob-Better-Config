package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.ZombifiedPiglinConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import com.reggarf.mods.mob_better_config.util.helper.EntitySpawnUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.ZombifiedPiglin;

public class ZombifiedPiglinEvents {

    private static final String PROCESSED_TAG = "mbc_processed";

    public static void onSpawn(ZombifiedPiglin piglin, ServerLevel level) {

        if (piglin.getTags().contains(PROCESSED_TAG))
            return;

        if (BossUtil.isBoss(piglin))
            return;

        ZombifiedPiglinConfig config = ModConfigs.getZombifiedPiglin();

        applyAll(piglin, config);

        BossUtil.tryApplyBoss(
                piglin,
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

        piglin.addTag(PROCESSED_TAG);

        // Spawn multiplier
        for (int i = 1; i < config.spawnMultiplier; i++) {

            ZombifiedPiglin extra = EntitySpawnUtil.createMob(EntityType.ZOMBIFIED_PIGLIN, level);

            if (extra == null)
                continue;

            extra.moveTo(
                    piglin.getX(),
                    piglin.getY(),
                    piglin.getZ(),
                    piglin.getYRot(),
                    piglin.getXRot()
            );

            applyAll(extra, config);
            extra.addTag(PROCESSED_TAG);

            level.addFreshEntity(extra);
        }
    }

    private static void applyAll(ZombifiedPiglin piglin, ZombifiedPiglinConfig config) {

        CommonMobHandler.applyCommonAttributes(
                piglin,
                config.health,
                config.armor,
                0.0,
                config.attackDamage,
                0.0,
                config.movementSpeed,
                16.0,
                config.knockbackResistance,
                config.attackKnockback,
                0.6,
                0.08,
                config.glowing,
                config.customName,
                false,
                0,
                config.reinforcementChance
        );

        piglin.setHealth((float) config.health);

        // Baby
        if (config.allowBaby && piglin.getRandom().nextDouble() < config.babyChance)
            piglin.setBaby(true);
    }

    public static float modifyXP(float xp) {
        return xp * (float) ModConfigs.getZombifiedPiglin().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, ZombifiedPiglin piglin) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                piglin,
                ModConfigs.getZombifiedPiglin().lootMultiplier
        );
    }
}