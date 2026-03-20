package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.ZoglinEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.Zoglin;

public class FabricZoglinEvents {

    public static void register() {

        // SPAWN
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (entity instanceof Zoglin zoglin && level instanceof ServerLevel serverLevel) {
                ZoglinEvents.onSpawn(zoglin, serverLevel);
            }

        });

        // DEATH
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Zoglin zoglin))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            // Loot
            ZoglinEvents.onDrops(level, zoglin);

            // XP
            double multiplier = ModConfigs.getZoglin().xpMultiplier;

            if (multiplier > 1.0) {

                int baseXP = 5;
                int extraXP = (int)((multiplier - 1.0) * baseXP);

                if (extraXP > 0) {
                    ExperienceOrb.award(level, zoglin.position(), extraXP);
                }
            }
        });
    }
}