package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.EnderDragonConfig;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class EnderDragonEvents {

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof EnderDragon dragon))
            return;

        if (!(dragon.level() instanceof ServerLevel))
            return;

        EnderDragonConfig config = ModConfigs.getEnderDragon();

        if (dragon.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(dragon, config);

    }

    private void applyConfig(EnderDragon dragon, EnderDragonConfig config) {

        if (config.glowing)
            dragon.setGlowingTag(true);
    }


    @SubscribeEvent
    public void onDragonBreathDamage(LivingDamageEvent.Pre event) {

        if (!(event.getSource().getDirectEntity() instanceof AreaEffectCloud cloud))
            return;

        if (!(cloud.getOwner() instanceof EnderDragon))
            return;

        EnderDragonConfig config = ModConfigs.getEnderDragon();

        float damage = event.getNewDamage() *
                (float) config.dragonBreathDamageMultiplier;

        event.setNewDamage(damage);
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof EnderDragon dragon))
            return;

        if (!(dragon.level() instanceof ServerLevel level))
            return;

        EnderDragonConfig config = ModConfigs.getEnderDragon();

        LootUtil.applyLootMultiplier(
                event,
                level,
                dragon,
                config.lootMultiplier
        );
    }
}