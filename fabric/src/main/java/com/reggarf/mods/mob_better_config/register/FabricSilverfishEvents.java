package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.SilverfishEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.Silverfish;

public class FabricSilverfishEvents {

    public static void register() {

        // Spawn / Load
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (entity instanceof Silverfish silverfish && level instanceof ServerLevel serverLevel) {
                SilverfishEvents.onSpawn(silverfish, serverLevel);
            }

        });

        // Death (XP + Loot)
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Silverfish silverfish))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            // Loot
            SilverfishEvents.onDrops(level, silverfish);

            // XP
            double multiplier = ModConfigs.getSilverfish().xpMultiplier;

            if (multiplier > 1.0) {

                int baseXP = 5; // vanilla silverfish XP
                int extraXP = (int) ((multiplier - 1.0) * baseXP);

                if (extraXP > 0) {
                    ExperienceOrb.award(level, silverfish.position(), extraXP);
                }
            }

        });

    }
}