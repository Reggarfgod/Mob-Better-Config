package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.RavagerEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.Ravager;

public class FabricRavagerEvents {

    public static void register() {

        // SPAWN
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (entity instanceof Ravager ravager && level instanceof ServerLevel serverLevel) {
                RavagerEvents.onSpawn(ravager, serverLevel);
            }

        });

        // DEATH
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Ravager ravager))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            // Apply loot multiplier
            RavagerEvents.onDrops(level, ravager);

            // Apply XP multiplier
            double multiplier = ModConfigs.getRavager().xpMultiplier;

            if (multiplier > 1.0) {

                int extraXP = (int)((multiplier - 1.0) * 20); // Ravager vanilla XP ≈ 20

                if (extraXP > 0) {
                    ExperienceOrb.award(level, ravager.position(), extraXP);
                }

            }

        });

    }
}