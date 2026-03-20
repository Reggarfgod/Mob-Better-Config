package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.HoglinConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.hoglin.Hoglin;

public class HoglinEvents {

    public static void onSpawn(Hoglin hoglin, ServerLevel level) {
        if (CommonMobHandler.isInitialized(hoglin))
            return;
        CommonMobHandler.markInitialized(hoglin);

        HoglinConfig config = ModConfigs.getHoglin();

        CommonMobHandler.applyCommonAttributes(
                hoglin,
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
                config.customName,
                false,
                0,
                config.reinforcementChance
        );

        if (config.fireImmune)
            hoglin.setRemainingFireTicks(0);

        if (config.disableZombification)
            hoglin.setImmuneToZombification(true);

        BossUtil.tryApplyBoss(
                hoglin,
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

        /* Spawn Multiplier */

        CommonMobHandler.spawnMultiplier(
                hoglin,
                level,
                config.spawnMultiplier
        );
    }

    public static float modifyAttackDamage(float damage) {

        HoglinConfig config = ModConfigs.getHoglin();

        return damage * (float) config.attackDamageMultiplier;
    }

    public static float modifyXP(float xp) {

        HoglinConfig config = ModConfigs.getHoglin();

        return xp * (float) config.xpMultiplier;
    }

    public static void onDrops(ServerLevel level, Hoglin hoglin) {

        HoglinConfig config = ModConfigs.getHoglin();

        LootUtil.applyLootMultiplier(
                null,
                level,
                hoglin,
                config.lootMultiplier
        );
    }
}