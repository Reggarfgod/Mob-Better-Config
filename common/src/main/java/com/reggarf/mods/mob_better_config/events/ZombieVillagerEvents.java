package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.ZombieVillagerConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.ZombieVillager;

public class ZombieVillagerEvents {

    public static void onSpawn(ZombieVillager zv, ServerLevel level) {

        if (CommonMobHandler.isInitialized(zv))
            return;

        ZombieVillagerConfig config = ModConfigs.getZombieVillager();

        applyAll(zv, config);

        BossUtil.tryApplyBoss(
                zv,
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

        CommonMobHandler.markInitialized(zv);
        CommonMobHandler.spawnMultiplier(zv, level, config.spawnMultiplier);
    }

    private static void applyAll(ZombieVillager zv, ZombieVillagerConfig config) {

        RandomSource random = zv.getRandom();

        CommonMobHandler.applyCommonAttributes(
                zv,
                config.health,
                0.0,
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

        zv.setHealth((float) config.health);
    }

    public static void onTick(ZombieVillager zv) {

        ZombieVillagerConfig config = ModConfigs.getZombieVillager();

        CommonMobHandler.applyCommonTickBehaviors(
                zv,
                config.burnInDaylight,
                config.fireImmune,
                false,
                false,
                false,
                false
        );

        if (!zv.isConverting())
            return;

        double multiplier = config.cureSpeedMultiplier;

        if (multiplier == 1.0D)
            return;

        if (multiplier > 1.0D) {

            int extraTicks = (int) Math.floor(multiplier - 1.0D);

            for (int i = 0; i < extraTicks; i++) {
                zv.aiStep();
            }

        } else if (multiplier > 0.0D) {

            int delay = (int) Math.floor(1.0D / multiplier);

            if (delay > 1 && zv.tickCount % delay != 0) {
                return;
            }
        }
    }

    public static float modifyXP(float xp) {
        return xp * (float) ModConfigs.getZombieVillager().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, ZombieVillager zv) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                zv,
                ModConfigs.getZombieVillager().lootMultiplier
        );
    }
}