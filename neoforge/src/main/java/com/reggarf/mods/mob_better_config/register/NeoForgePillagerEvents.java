package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.PillagerEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.illager.Pillager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

public class NeoForgePillagerEvents {

    @SubscribeEvent
    public static void spawn(FinalizeSpawnEvent event) {

        if (event.getEntity() instanceof Pillager pillager &&
                pillager.level() instanceof ServerLevel level) {

            PillagerEvents.onSpawn(pillager, level);
        }
    }

//    @SubscribeEvent
//    public static void arrowDamage(LivingDamageEvent.Pre event) {
//
//        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow))
//            return;
//
//        float damage = PillagerHandler.modifyArrowDamage(
//                arrow,
//                event.getNewDamage()
//        );
//
//        event.setNewDamage(damage);
//    }

    @SubscribeEvent
    public static void xp(LivingExperienceDropEvent event) {

        if (event.getEntity() instanceof Pillager) {

            float xp = event.getDroppedExperience();
            xp = PillagerEvents.modifyXP(xp);
            event.setDroppedExperience((int) xp);
        }
    }

    @SubscribeEvent
    public static void drops(LivingDropsEvent event) {

        if (event.getEntity() instanceof Pillager pillager &&
                pillager.level() instanceof ServerLevel level) {

            PillagerEvents.onDrops(level, pillager);
        }
    }
}