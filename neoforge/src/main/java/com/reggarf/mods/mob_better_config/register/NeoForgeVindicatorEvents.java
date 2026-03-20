package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.VindicatorEvents;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Vindicator;

public class NeoForgeVindicatorEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Vindicator vindicator))
            return;

        if (!(vindicator.level() instanceof ServerLevel level))
            return;

        VindicatorEvents.onSpawn(vindicator, level);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof Vindicator))
            return;

        event.setDroppedExperience(
                (int) VindicatorEvents.modifyXP(event.getDroppedExperience())
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Vindicator vindicator))
            return;

        if (!(vindicator.level() instanceof ServerLevel level))
            return;

        VindicatorEvents.onDrops(level, vindicator);
    }
}