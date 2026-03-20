package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.PiglinBruteEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;

public class FabricPiglinBruteEvents {

    public static void register() {

        // SPAWN
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (entity instanceof PiglinBrute brute &&
                level instanceof ServerLevel serverLevel) {

                PiglinBruteEvents.onSpawn(brute, serverLevel);
            }
        });

        // DEATH
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof PiglinBrute brute))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            // Loot multiplier
            PiglinBruteEvents.onDrops(level, brute);

            // XP multiplier
            double multiplier = ModConfigs.getPiglinBrute().xpMultiplier;

            if (multiplier > 1.0) {

                int extraXP = (int)((multiplier - 1.0) * 20); 
                // Piglin Brute vanilla XP ≈ 20

                if (extraXP > 0) {
                    ExperienceOrb.award(level, brute.position(), extraXP);
                }
            }
        });
    }
}