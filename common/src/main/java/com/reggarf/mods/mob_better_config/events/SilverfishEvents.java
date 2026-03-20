package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.SilverfishConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.BossUtil;
import com.reggarf.mods.mob_better_config.util.LootUtil;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Silverfish;

public class SilverfishEvents {

    public static void onSpawn(Silverfish silverfish, ServerLevel level) {
        if (CommonMobHandler.isInitialized(silverfish))
            return;
        CommonMobHandler.markInitialized(silverfish);

        SilverfishConfig config = ModConfigs.getSilverfish();

        // Apply attributes (handles INIT_TAG internally)
        applyAll(silverfish, config);

        // Boss system
        BossUtil.tryApplyBoss(
                silverfish,
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
        CommonMobHandler.spawnMultiplier(silverfish, level, config.spawnMultiplier);
    }

    private static void applyAll(Silverfish silverfish, SilverfishConfig config) {

        CommonMobHandler.applyCommonAttributes(
                silverfish,
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
                config.CustomName,
                false,
                0,
                config.reinforcementChance
        );

        silverfish.setHealth((float) config.health);

        if (!config.enableMergeWithStone) {
            silverfish.removeAllGoals(goal ->
                    goal.getClass().getSimpleName().equals("SilverfishMergeWithStoneGoal")
            );
        }

        if (!config.enableWakeFriends) {
            silverfish.removeAllGoals(goal ->
                    goal.getClass().getSimpleName().equals("SilverfishWakeUpFriendsGoal")
            );
        }
    }

    public static void onTick(Silverfish silverfish) {

        SilverfishConfig config = ModConfigs.getSilverfish();

        CommonMobHandler.applyCommonTickBehaviors(
                silverfish,
                false,
                config.fireImmune,
                false,
                false,
                false,
                false
        );
    }

    public static float modifyXP(float xp) {
        return xp * (float) ModConfigs.getSilverfish().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, Silverfish silverfish) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                silverfish,
                ModConfigs.getSilverfish().lootMultiplier
        );
    }
}