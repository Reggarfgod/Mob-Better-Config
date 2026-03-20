package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.PhantomEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.Phantom;

public class FabricPhantomEvents {

    public static void register() {

        // SPAWN
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (entity instanceof Phantom phantom && level instanceof ServerLevel serverLevel) {
                PhantomEvents.onSpawn(phantom, serverLevel);
            }

        });

        // TICK
        ServerTickEvents.END_WORLD_TICK.register(level -> {

            for (var entity : level.getAllEntities()) {

                if (entity instanceof Phantom phantom) {
                    PhantomEvents.onTick(phantom);
                }

            }

        });

        // DEATH
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Phantom phantom))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            // Apply loot multiplier
            PhantomEvents.onDrops(level, phantom);

            // Apply XP multiplier
            double multiplier = ModConfigs.getPhantom().xpMultiplier;

            if (multiplier > 1.0) {

                int extraXP = (int)((multiplier - 1.0) * 5); // Phantom vanilla XP ≈ 5

                if (extraXP > 0) {
                    ExperienceOrb.award(level, phantom.position(), extraXP);
                }

            }

        });

    }
}