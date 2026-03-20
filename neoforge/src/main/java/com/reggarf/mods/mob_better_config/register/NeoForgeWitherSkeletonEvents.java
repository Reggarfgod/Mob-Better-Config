package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.WitherSkeletonEvents;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class NeoForgeWitherSkeletonEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof WitherSkeleton skeleton))
            return;

        if (!(skeleton.level() instanceof ServerLevel level))
            return;

        WitherSkeletonEvents.onSpawn(skeleton, level);
    }

//    @SubscribeEvent
//    public void onTick(EntityTickEvent.Post event) {
//
//        if (event.getEntity() instanceof LivingEntity living) {
//            WitherSkeletonEvents.onTick(living);
//        }
//    }
//
//    @SubscribeEvent
//    public void onDamage(LivingDamageEvent.Post event) {
//
//        if (event.getSource().getEntity() instanceof WitherSkeleton &&
//            event.getEntity() instanceof LivingEntity target) {
//
//            WitherSkeletonEvents.onMeleeHit(target);
//        }
//    }
//
//    @SubscribeEvent
//    public void onArrow(EntityJoinLevelEvent event) {
//
//        if (event.getEntity() instanceof AbstractArrow arrow) {
//            WitherSkeletonEvents.onArrow(arrow);
//        }
//    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof WitherSkeleton))
            return;

        event.setDroppedExperience(
                (int) WitherSkeletonEvents.modifyXP(event.getDroppedExperience())
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof WitherSkeleton skeleton))
            return;

        if (!(skeleton.level() instanceof ServerLevel level))
            return;

        WitherSkeletonEvents.onDrops(level, skeleton);
    }
}