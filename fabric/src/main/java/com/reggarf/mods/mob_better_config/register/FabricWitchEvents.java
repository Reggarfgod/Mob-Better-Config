package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.WitchEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;


public class FabricWitchEvents {

    public static void register() {

        // SPAWN
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (entity instanceof Witch witch && level instanceof ServerLevel serverLevel) {
                WitchEvents.onSpawn(witch, serverLevel);
            }

        });

        // TICK
        ServerTickEvents.END_LEVEL_TICK.register(level -> {

            for (var entity : level.getAllEntities()) {

                if (entity instanceof Witch witch) {
                    WitchEvents.onTick(witch);
                }

            }

        });

        // POTION IMPACT
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (entity instanceof AbstractThrownPotion potion && level instanceof ServerLevel serverLevel) {
                WitchEvents.onPotionImpact(potion, serverLevel);
            }

        });

        // DEATH
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Witch witch))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            WitchEvents.onDrops(level, witch);

            double multiplier = ModConfigs.getWitch().xpMultiplier;

            if (multiplier > 1.0) {

                int baseXP = 5;
                int extraXP = (int)((multiplier - 1.0) * baseXP);

                if (extraXP > 0) {
                    ExperienceOrb.award(level, witch.position(), extraXP);
                }
            }
        });
    }
}