package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.CreeperEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Creeper;

public class FabricCreeperEvents {

    public static void register() {

        // SPAWN
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (entity instanceof Creeper creeper && level instanceof ServerLevel serverLevel) {
                CreeperEvents.onSpawn(creeper, serverLevel);
            }

        });

        // TICK
        ServerTickEvents.END_WORLD_TICK.register(level -> {

            for (var entity : level.getAllEntities()) {

                if (entity instanceof Creeper creeper) {
                    CreeperEvents.onTick(creeper);
                }

            }

        });

        // DEATH / DROPS
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Creeper creeper))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            CreeperEvents.onDrops(level, creeper);
        });

    }
}