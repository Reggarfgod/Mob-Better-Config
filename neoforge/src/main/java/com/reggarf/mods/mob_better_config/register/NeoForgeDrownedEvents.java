package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.DrownedEvents;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = "mob_better_config")
public class NeoForgeDrownedEvents {

    @SubscribeEvent
    public static void onSpawnCheck(MobSpawnEvent.PositionCheck event) {

        if (!(event.getEntity() instanceof Drowned drowned))
            return;

        if (!DrownedEvents.onSpawnCheck(drowned)) {
            event.setResult(MobSpawnEvent.PositionCheck.Result.FAIL);
        }
    }

    @SubscribeEvent
    public static void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Drowned drowned))
            return;

        if (!(event.getLevel() instanceof ServerLevel level))
            return;

        DrownedEvents.onJoin(drowned, level);
    }

    @SubscribeEvent
    public static void onTick(EntityTickEvent.Post event) {

        if (event.getEntity() instanceof Drowned drowned) {
            DrownedEvents.onTick(drowned);
        }
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent.Pre event) {

        if (!(event.getSource().getDirectEntity() instanceof ThrownTrident trident))
            return;

        if (!(trident.getOwner() instanceof Drowned drowned))
            return;

        event.setNewDamage(DrownedEvents.onDamage(drowned, event.getNewDamage()));
    }

    @SubscribeEvent
    public static void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof Drowned))
            return;

        event.setDroppedExperience(
                (int) DrownedEvents.onXP(event.getDroppedExperience())
        );
    }

    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Drowned drowned))
            return;

        if (!(drowned.level() instanceof ServerLevel level))
            return;

        DrownedEvents.onDrops(level, drowned);
    }

    @SubscribeEvent
    public static void onTridentSpawn(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof ThrownTrident trident))
            return;

        if (!(trident.getOwner() instanceof Drowned drowned))
            return;

        DrownedEvents.onTridentSpawn(drowned, trident);
    }
}