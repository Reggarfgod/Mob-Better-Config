package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.SkeletonEvents;

import com.reggarf.mods.mob_better_config.events.ZombieEvents;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NeoForgeSkeletonEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Skeleton skeleton))
            return;

        if (!(skeleton.level() instanceof ServerLevel level))
            return;

        SkeletonEvents.onSpawn(skeleton, level);
    }

    @SubscribeEvent
    public void tick(EntityTickEvent.Post event) {

        if (event.getEntity() instanceof Skeleton skeleton) {
            SkeletonEvents.onTick(skeleton);
        }
    }
    @SubscribeEvent
    public void onArrow(EntityJoinLevelEvent event) {

        if (event.getEntity() instanceof AbstractArrow arrow) {
            SkeletonEvents.onArrow(arrow);
        }
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof Skeleton))
            return;

        event.setDroppedExperience(
                (int) SkeletonEvents.modifyXP(event.getDroppedExperience())
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Skeleton skeleton))
            return;

        if (!(skeleton.level() instanceof ServerLevel level))
            return;

        SkeletonEvents.onDrops(level, skeleton);
    }
}