package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.PhantomEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Phantom;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NeoForgePhantomEvents {

    @SubscribeEvent
    public static void onSpawn(FinalizeSpawnEvent event) {

        if (event.getEntity() instanceof Phantom phantom &&
            phantom.level() instanceof ServerLevel level) {

            PhantomEvents.onSpawn(phantom, level);
        }
    }

    @SubscribeEvent
    public static void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof Phantom phantom))
            return;

        float xp = event.getDroppedExperience();
        xp = PhantomEvents.modifyXP(xp);
        event.setDroppedExperience((int) xp);
    }

    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Phantom phantom))
            return;

        if (!(phantom.level() instanceof ServerLevel level))
            return;

        PhantomEvents.onDrops(level, phantom);
    }

    @SubscribeEvent
    public static void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Phantom phantom))
            return;

        PhantomEvents.onTick(phantom);
    }
}