package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.EnderDragonEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

public class FabricEnderDragonEvents {

    public static void register() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {

            if (!(entity instanceof EnderDragon dragon))
                return;

            if (!(world instanceof ServerLevel level))
                return;

            EnderDragonEvents.onJoin(dragon, level);
        });

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {

            float newDamage = EnderDragonEvents.modifyDragonBreathDamage(source, amount);

            if (newDamage != amount) {
                entity.hurt(source, newDamage);
                return false;
            }

            return true;
        });

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof EnderDragon dragon))
                return;

            if (!(dragon.level() instanceof ServerLevel level))
                return;

            EnderDragonEvents.onDrops(level, dragon);
        });
    }
}