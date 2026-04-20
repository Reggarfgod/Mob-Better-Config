package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.EndermanEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.EnderMan;

public class FabricEndermanEvents {

    public static void register() {

        // Spawn / Join
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {

            if (!(entity instanceof EnderMan enderman))
                return;

            if (!(world instanceof ServerLevel level))
                return;

            EndermanEvents.onJoin(enderman, level);
        });


        // Tick logic
        ServerTickEvents.END_WORLD_TICK.register(level -> {

            for (Entity entity : level.getAllEntities()) {

                if (!(entity instanceof EnderMan enderman))
                    continue;

                EndermanEvents.onTick(enderman, level);
            }
        });


        // Drops
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof EnderMan enderman))
                return;

            if (!(enderman.level() instanceof ServerLevel level))
                return;

            EndermanEvents.onDrops(level, enderman);
        });
    }
}