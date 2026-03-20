package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.ElderGuardianEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.ElderGuardian;

public class FabricElderGuardianEvents {

    public static void register() {

        // Spawn / Join
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {

            if (!(entity instanceof ElderGuardian elder))
                return;

            if (!(world instanceof ServerLevel level))
                return;

            ElderGuardianEvents.onJoin(elder, level);
        });




        // Drops
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof ElderGuardian elder))
                return;

            if (!(elder.level() instanceof ServerLevel level))
                return;

            ElderGuardianEvents.onDrops(level, elder);
        });
    }
}