package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.ZoglinConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zoglin;

public class ZoglinEvents {

    private static final String PROCESSED_TAG = "mbc_processed";

    public static void onSpawn(Zoglin zoglin, ServerLevel level) {

        if (zoglin.getTags().contains(PROCESSED_TAG))
            return;

        if (BossUtil.isBoss(zoglin))
            return;

        ZoglinConfig config = ModConfigs.getZoglin();

        applyAll(zoglin, config);

        BossUtil.tryApplyBoss(
                zoglin,
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

        zoglin.addTag(PROCESSED_TAG);

        // Spawn multiplier
        for (int i = 1; i < config.spawnMultiplier; i++) {

            Zoglin extra = new Zoglin(EntityType.ZOGLIN, level);

            extra.snapTo(
                    zoglin.getX(),
                    zoglin.getY(),
                    zoglin.getZ(),
                    zoglin.getYRot(),
                    zoglin.getXRot()
            );

            applyAll(extra, config);
            extra.addTag(PROCESSED_TAG);

            level.addFreshEntity(extra);
        }
    }

    private static void applyAll(Zoglin zoglin, ZoglinConfig config) {

        CommonMobHandler.applyCommonAttributes(
                zoglin,
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

        zoglin.setHealth((float) config.health);

        if (config.fireImmune)
            zoglin.setRemainingFireTicks(0);

        if (config.allowBaby && zoglin.getRandom().nextDouble() < config.babyChance)
            zoglin.setBaby(true);
    }

    public static float modifyXP(float xp) {
        return xp * (float) ModConfigs.getZoglin().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, Zoglin zoglin) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                zoglin,
                ModConfigs.getZoglin().lootMultiplier
        );
    }
}