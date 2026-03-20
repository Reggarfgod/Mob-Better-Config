package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.BlazeEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Blaze;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NeoForgeBlazeEvents {

    @SubscribeEvent
    public static void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Blaze blaze))
            return;

        if (!(event.getLevel() instanceof ServerLevel level))
            return;

        BlazeEvents.onSpawn(blaze, level);
    }

    @SubscribeEvent
    public static void onTick(EntityTickEvent.Post event) {

        if (event.getEntity() instanceof Blaze blaze) {
            BlazeEvents.onTick(blaze);
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingIncomingDamageEvent event) {

        if (!(event.getEntity() instanceof Blaze blaze))
            return;

        if (BlazeEvents.onDamage(blaze, event.getSource())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof Blaze))
            return;

        float[] holder = new float[]{event.getDroppedExperience()};
        BlazeEvents.onXP(holder);

        event.setDroppedExperience((int) holder[0]);
    }

    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Blaze blaze))
            return;

        if (!(blaze.level() instanceof ServerLevel level))
            return;

        BlazeEvents.onDrops(level, blaze);
    }
}