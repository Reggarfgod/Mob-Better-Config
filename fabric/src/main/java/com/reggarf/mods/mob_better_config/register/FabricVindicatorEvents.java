package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.VindicatorEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.Vindicator;

public class FabricVindicatorEvents {

    public static void register() {

        // Spawn
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {
            if (entity instanceof Vindicator vindicator && level instanceof ServerLevel serverLevel) {
                VindicatorEvents.onSpawn(vindicator, serverLevel);
            }
        });

        // Death
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Vindicator vindicator))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            // Loot
            VindicatorEvents.onDrops(level, vindicator);

            // XP
            double multiplier = ModConfigs.getVindicator().xpMultiplier;

            if (multiplier > 1.0) {

                int baseXP = 5;
                int extraXP = (int) ((multiplier - 1.0) * baseXP);

                if (extraXP > 0) {
                    ExperienceOrb.award(level, vindicator.position(), extraXP);
                }
            }
        });
    }
}