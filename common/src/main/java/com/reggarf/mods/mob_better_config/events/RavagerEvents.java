package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.RavagerConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Ravager;

public class RavagerEvents {

    public static void onSpawn(Ravager ravager, ServerLevel level) {
        if (CommonMobHandler.isInitialized(ravager))
            return;
        CommonMobHandler.markInitialized(ravager);

        RavagerConfig config = ModConfigs.getRavager();

        CommonMobHandler.applyCommonAttributes(
                ravager,
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
                config.doorBreak,
                config.doorBreakMode,
                config.reinforcementChance
        );

        BossUtil.tryApplyBoss(
                ravager,
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

        // Raid bonus
        if (config.strongerInRaid && ravager.hasActiveRaid()) {

            if (ravager.getAttribute(Attributes.MAX_HEALTH) != null)
                ravager.getAttribute(Attributes.MAX_HEALTH)
                        .setBaseValue(config.health * config.raidHealthBonus);

            if (ravager.getAttribute(Attributes.ATTACK_DAMAGE) != null)
                ravager.getAttribute(Attributes.ATTACK_DAMAGE)
                        .setBaseValue(config.attackDamage * config.raidDamageBonus);

            ravager.setHealth((float) (config.health * config.raidHealthBonus));
        }

        // Spawn multiplier
        CommonMobHandler.spawnMultiplier(ravager, level, config.spawnMultiplier);
    }

    public static float modifyXP(float xp) {

        return xp * (float) ModConfigs.getRavager().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, Ravager ravager) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                ravager,
                ModConfigs.getRavager().lootMultiplier
        );
    }
}