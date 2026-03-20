package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.CreeperEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Creeper;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NeoForgeCreeperEvents {

    // SPAWN
    @SubscribeEvent
    public static void onSpawn(FinalizeSpawnEvent event) {

        if (event.getEntity() instanceof Creeper creeper &&
            creeper.level() instanceof ServerLevel level) {

            CreeperEvents.onSpawn(creeper, level);
        }
    }

    // TICK
    @SubscribeEvent
    public static void onTick(EntityTickEvent.Post event) {

        if (event.getEntity() instanceof Creeper creeper) {
            CreeperEvents.onTick(creeper);
        }
    }

    // DROPS
    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {

        if (event.getEntity() instanceof Creeper creeper &&
            creeper.level() instanceof ServerLevel level) {

            CreeperEvents.onDrops(level, creeper);
        }
    }

    // EXPLOSION SCALE
    @SubscribeEvent
    public static void onExplosion(ExplosionEvent.Start event) {

        CreeperEvents.onExplosion(event.getExplosion());
    }
}