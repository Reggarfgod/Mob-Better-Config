package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.CaveSpiderEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CaveSpider;

public class FabricCaveSpiderEvents {

    public static void register() {

        // Spawn event
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (entity instanceof CaveSpider spider && level instanceof ServerLevel serverLevel) {
                CaveSpiderEvents.onSpawn(spider, serverLevel);
            }
        });

//        // Tick event
//        ServerTickEvents.END_WORLD_TICK.register(level -> {
//
//            for (var entity : level.getAllEntities()) {
//
//                if (entity instanceof LivingEntity living) {
//                    CaveSpiderEvents.onTick(living);
//                }
//            }
//        });
    }
}