package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.EndermanEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.EnderMan;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NeoForgeEndermanEvents {

    @SubscribeEvent
    public void onJoin(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof EnderMan enderman))
            return;

        if (!(enderman.level() instanceof ServerLevel level))
            return;

        EndermanEvents.onJoin(enderman, level);
    }

    @SubscribeEvent
    public void onTeleport(EntityTeleportEvent.EnderEntity event) {

        if (!(event.getEntity() instanceof EnderMan enderman))
            return;

        double[] pos = {event.getTargetX(), event.getTargetY(), event.getTargetZ()};
        boolean[] cancel = {false};

        EndermanEvents.onTeleport(enderman, pos, cancel);

        if (cancel[0]) {
            event.setCanceled(true);
            return;
        }

        event.setTargetX(pos[0]);
        event.setTargetY(pos[1]);
        event.setTargetZ(pos[2]);
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof EnderMan enderman))
            return;

        if (!(enderman.level() instanceof ServerLevel level))
            return;

        EndermanEvents.onTick(enderman, level);
    }


    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof EnderMan enderman))
            return;

        if (!(enderman.level() instanceof ServerLevel level))
            return;

        EndermanEvents.onDrops(level, enderman);
    }

}