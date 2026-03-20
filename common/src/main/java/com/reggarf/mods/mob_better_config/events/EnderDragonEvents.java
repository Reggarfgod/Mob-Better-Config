package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.EnderDragonConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.LootUtil;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;

public class EnderDragonEvents {

    public static void onJoin(EnderDragon dragon, ServerLevel level) {
        if (CommonMobHandler.isInitialized(dragon))
            return;
        CommonMobHandler.markInitialized(dragon);

        EnderDragonConfig config = ModConfigs.getEnderDragon();

        if (config.glowing)
            dragon.setGlowingTag(true);
    }

    public static float modifyDragonBreathDamage(DamageSource source, float damage) {

        if (!(source.getDirectEntity() instanceof AreaEffectCloud cloud))
            return damage;

        if (!(cloud.getOwner() instanceof EnderDragon))
            return damage;

        EnderDragonConfig config = ModConfigs.getEnderDragon();

        return damage * (float) config.dragonBreathDamageMultiplier;
    }

    public static void onDrops(ServerLevel level, EnderDragon dragon) {

        EnderDragonConfig config = ModConfigs.getEnderDragon();

        LootUtil.applyLootMultiplier(
                null,
                level,
                dragon,
                config.lootMultiplier
        );
    }
}