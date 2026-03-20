package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.PillagerEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.Pillager;

public class FabricPillagerEvents {

    public static void register() {

        // SPAWN
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (entity instanceof Pillager pillager &&
                level instanceof ServerLevel serverLevel) {

                PillagerEvents.onSpawn(pillager, serverLevel);
            }
        });

        // DEATH
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Pillager pillager))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            // Loot multiplier
            PillagerEvents.onDrops(level, pillager);

            // XP multiplier
            double multiplier = ModConfigs.getPillager().xpMultiplier;

            if (multiplier > 1.0) {

                int baseXP = 5; // Vanilla Pillager XP ≈ 5
                int extraXP = (int)((multiplier - 1.0) * baseXP);

                if (extraXP > 0) {
                    ExperienceOrb.award(level, pillager.position(), extraXP);
                }
            }
        });
    }
}