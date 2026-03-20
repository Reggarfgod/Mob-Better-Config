package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.GhastEvents;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ghast;

public class FabricGhastEvents {

    public static void register() {

        // Spawn
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {

            if (!(world instanceof ServerLevel level))
                return;

            if (entity instanceof Ghast ghast)
                GhastEvents.onSpawn(ghast, level);
        });


        // Tick
        ServerTickEvents.END_WORLD_TICK.register(level -> {

            for (Entity entity : level.getAllEntities()) {

                if (entity instanceof Ghast ghast)
                    GhastEvents.tick(ghast);
            }
        });


        // Remove
        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {

            if (entity instanceof Ghast ghast)
                GhastEvents.onRemove(ghast);
        });
    }
}