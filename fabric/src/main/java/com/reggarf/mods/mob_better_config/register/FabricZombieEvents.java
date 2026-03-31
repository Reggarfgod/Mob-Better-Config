package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.ZombieEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.zombie.Zombie;


public class FabricZombieEvents {

    public static void register() {

        // SPAWN
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (entity instanceof Zombie zombie && level instanceof ServerLevel serverLevel) {
                ZombieEvents.onSpawn(zombie, serverLevel);
            }

        });

        // TICK
        ServerTickEvents.END_LEVEL_TICK.register(level -> {

            // iterate only zombies (efficient enough)
            for (Zombie zombie : level.getEntitiesOfClass(Zombie.class, level.getWorldBorder().getCollisionShape().bounds())) {
                ZombieEvents.onTick(zombie);
            }

        });

        // DEATH
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (entity instanceof Zombie zombie &&
                entity.level() instanceof ServerLevel level) {

                ZombieEvents.onDrops(level, zombie);
                // Apply XP multiplier
                double multiplier = ModConfigs.getPhantom().xpMultiplier;
                if (multiplier > 1.0) {

                    int extraXP = (int)((multiplier - 1.0) * 5);

                    if (extraXP > 0) {
                        ExperienceOrb.award(level, zombie.position(), extraXP);
                    }

                }
            }

        });
    }
}