package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.ZoglinEvents;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Zoglin;

public class NeoForgeZoglinEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Zoglin zoglin))
            return;

        if (!(zoglin.level() instanceof ServerLevel level))
            return;

        ZoglinEvents.onSpawn(zoglin, level);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof Zoglin))
            return;

        event.setDroppedExperience(
                (int) ZoglinEvents.modifyXP(event.getDroppedExperience())
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Zoglin zoglin))
            return;

        if (!(zoglin.level() instanceof ServerLevel level))
            return;

        ZoglinEvents.onDrops(level, zoglin);
    }
}