package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.GhastEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Ghast;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NeoForgeGhastEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Ghast ghast))
            return;

        if (!(ghast.level() instanceof ServerLevel level))
            return;

        GhastEvents.onSpawn(ghast, level);
    }


    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (event.getEntity() instanceof Ghast ghast)
            GhastEvents.tick(ghast);
    }


    @SubscribeEvent
    public void onLeave(EntityLeaveLevelEvent event) {

        if (event.getEntity() instanceof Ghast ghast)
            GhastEvents.onRemove(ghast);
    }
}