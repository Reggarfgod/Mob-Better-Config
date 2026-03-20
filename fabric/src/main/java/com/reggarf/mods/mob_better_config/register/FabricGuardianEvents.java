package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.GuardianEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Guardian;

public class FabricGuardianEvents {

    public static void register() {

        /* ---------------- Spawn ---------------- */

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {

            if (!(world instanceof ServerLevel level))
                return;

            if (!(entity instanceof Guardian guardian))
                return;

            GuardianEvents.onSpawn(guardian, level);
        });

        /* ---------------- Tick ---------------- */

        ServerTickEvents.END_WORLD_TICK.register(level -> {

            for (Entity entity : level.getAllEntities()) {

                if (!(entity instanceof Guardian guardian))
                    return;

                GuardianEvents.onPreTick(guardian);
                GuardianEvents.onPostTick(guardian);
            }
        });

        /* ---------------- Thorns ---------------- */

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {

            if (!(entity instanceof Guardian guardian))
                return true;

            if (!(source.getDirectEntity() instanceof LivingEntity attacker))
                return true;

            GuardianEvents.handleThorns(guardian, attacker);

            return true;
        });

        /* ---------------- Drops ---------------- */

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Guardian guardian))
                return;

            if (!(guardian.level() instanceof ServerLevel level))
                return;

            GuardianEvents.onDrops(level, guardian);
        });
    }
}