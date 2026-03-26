package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.StrayConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.core.component.DataComponents;

public class StrayEvents {

    public static void onSpawn(Stray stray, ServerLevel level) {

        if (CommonMobHandler.isInitialized(stray))
            return;
        CommonMobHandler.markInitialized(stray);

        StrayConfig config = ModConfigs.getStray();

        applyAll(stray, config);

        // Boss
        BossUtil.tryApplyBoss(
                stray,
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

        CommonMobHandler.spawnMultiplier(stray, level, config.spawnMultiplier);
    }

    private static void applyAll(Stray stray, StrayConfig config) {

        CommonMobHandler.applyCommonAttributes(
                stray,
                config.health,
                config.armor,
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

        stray.setHealth((float) config.health);
    }

    public static void onTick(Stray stray) {

        StrayConfig config = ModConfigs.getStray();

        CommonMobHandler.applyCommonTickBehaviors(
                stray,
                config.burnInDaylight,
                config.fireImmune,
                false,
                false,
                false,
                false
        );
    }

    public static float modifyArrowDamage(AbstractArrow arrow, float damage) {

        if (!(arrow.getOwner() instanceof Stray))
            return damage;

        StrayConfig config = ModConfigs.getStray();

        if (config.arrowDamageMultiplier == 1.0D)
            return damage;

        return (float) (damage * config.arrowDamageMultiplier);
    }
    public static void onArrowSpawn(Arrow arrow) {

        if (!(arrow.getOwner() instanceof Stray))
            return;

        StrayConfig config = ModConfigs.getStray();

        // Remove vanilla potion
        arrow.getPickupItemStackOrigin()
                .set(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);

        if (!config.enableSlowness)
            return;

        arrow.addEffect(new MobEffectInstance(
                MobEffects.SLOWNESS,
                config.slownessDuration,
                config.slownessAmplifier
        ));
    }

    public static float modifyXP(float xp) {
        return xp * (float) ModConfigs.getStray().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, Stray stray) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                stray,
                ModConfigs.getStray().lootMultiplier
        );
    }
}