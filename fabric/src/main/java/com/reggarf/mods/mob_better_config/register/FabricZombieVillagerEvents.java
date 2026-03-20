package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.ZombieVillagerEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.ZombieVillager;

public class FabricZombieVillagerEvents {

    public static void register() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (entity instanceof ZombieVillager zv && level instanceof ServerLevel serverLevel) {
                ZombieVillagerEvents.onSpawn(zv, serverLevel);
            }

        });

        ServerTickEvents.END_WORLD_TICK.register(level -> {

            for (var entity : level.getAllEntities()) {

                if (entity instanceof ZombieVillager zv) {
                    ZombieVillagerEvents.onTick(zv);
                }

            }

        });

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof ZombieVillager zv))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            // Loot
            ZombieVillagerEvents.onDrops(level, zv);

            // XP
            double multiplier = ModConfigs.getZombieVillager().xpMultiplier;

            if (multiplier > 1.0) {

                int baseXP = 5;
                int extraXP = (int)((multiplier - 1.0) * baseXP);

                if (extraXP > 0) {
                    ExperienceOrb.award(level, zv.position(), extraXP);
                }
            }
        });
    }
}