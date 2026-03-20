package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.PillagerConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Pillager;

public class PillagerEvents {

    public static void onSpawn(Pillager pillager, ServerLevel level) {

        if (CommonMobHandler.isInitialized(pillager))
            return;
        CommonMobHandler.markInitialized(pillager);

        PillagerConfig config = ModConfigs.getPillager();

        CommonMobHandler.applyCommonAttributes(
                pillager,
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
                pillager,
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
        if (config.strongerInRaid && pillager.hasActiveRaid()) {

            if (pillager.getAttribute(Attributes.MAX_HEALTH) != null)
                pillager.getAttribute(Attributes.MAX_HEALTH)
                        .setBaseValue(config.health * config.raidHealthBonus);

            if (pillager.getAttribute(Attributes.ATTACK_DAMAGE) != null)
                pillager.getAttribute(Attributes.ATTACK_DAMAGE)
                        .setBaseValue(config.attackDamage * config.raidDamageBonus);

            pillager.setHealth((float) (config.health * config.raidHealthBonus));
        }
        // Spawn multiplier
        CommonMobHandler.spawnMultiplier(pillager, level, config.spawnMultiplier);

    }


//    public static float modifyArrowDamage(AbstractArrow arrow, float damage) {
//
//        if (!(arrow.getOwner() instanceof Pillager pillager))
//            return damage;
//
//        if (!(pillager.getMainHandItem().getItem() instanceof CrossbowItem))
//            return damage;
//
//        PillagerConfig config = ModConfigs.getPillager();
//
//        return damage * (float) config.crossbowDamageMultiplier;
//    }


    public static float modifyXP(float xp) {

        return xp * (float) ModConfigs.getPillager().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, Pillager pillager) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                pillager,
                ModConfigs.getPillager().lootMultiplier
        );
    }
}