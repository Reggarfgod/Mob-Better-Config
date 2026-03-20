package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.SilverfishEvents;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Silverfish;

public class NeoForgeSilverfishEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Silverfish silverfish))
            return;

        if (!(silverfish.level() instanceof ServerLevel level))
            return;

        SilverfishEvents.onSpawn(silverfish, level);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof Silverfish))
            return;

        event.setDroppedExperience(
                (int) SilverfishEvents.modifyXP(event.getDroppedExperience())
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Silverfish silverfish))
            return;

        if (!(silverfish.level() instanceof ServerLevel level))
            return;

        SilverfishEvents.onDrops(level, silverfish);
    }
}