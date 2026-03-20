package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.ShulkerEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.Shulker;

public class FabricShulkerEvents {

    public static void register() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (entity instanceof Shulker shulker && level instanceof ServerLevel serverLevel) {
                ShulkerEvents.onSpawn(shulker, serverLevel);
            }

        });

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Shulker shulker))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            // Loot
            ShulkerEvents.onDrops(level, shulker);

            // XP
            double multiplier = ModConfigs.getShulker().xpMultiplier;

            if (multiplier > 1.0) {

                int baseXP = 5; // vanilla shulker XP
                int extraXP = (int)((multiplier - 1.0) * baseXP);

                if (extraXP > 0) {
                    ExperienceOrb.award(level, shulker.position(), extraXP);
                }
            }

        });

    }
}