package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.BlazeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.Blaze;

public class FabricBlazeEvents {

    public static void register() {

        // SPAWN
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (entity instanceof Blaze blaze && level instanceof ServerLevel serverLevel) {
                BlazeEvents.onSpawn(blaze, serverLevel);
            }

        });

        // TICK
        ServerTickEvents.END_WORLD_TICK.register(level -> {

            for (var entity : level.getAllEntities()) {

                if (entity instanceof Blaze blaze) {
                    BlazeEvents.onTick(blaze);
                }

            }

        });

        // DAMAGE
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {

            if (!(entity instanceof Blaze blaze))
                return true;

            return !BlazeEvents.onDamage(blaze, source);
        });

        // DEATH
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Blaze blaze))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            // Apply loot multiplier
            BlazeEvents.onDrops(level, blaze);

            // Apply XP multiplier
            double multiplier = ModConfigs.getBlaze().xpMultiplier;

            if (multiplier > 1.0) {

                int extraXP = (int)((multiplier - 1.0) * 10); // Blaze vanilla XP ≈ 10

                if (extraXP > 0) {
                    ExperienceOrb.award(level, blaze.position(), extraXP);
                }

            }

        });

    }
}