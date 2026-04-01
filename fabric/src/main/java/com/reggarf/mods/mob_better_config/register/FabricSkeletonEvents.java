package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.SkeletonEvents;

import com.reggarf.mods.mob_better_config.events.ZombieEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;


public class FabricSkeletonEvents {

    public static void register() {

        // Spawn
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {
            if (entity instanceof Skeleton skeleton && level instanceof ServerLevel serverLevel) {
                SkeletonEvents.onSpawn(skeleton, serverLevel);
            }
        });

        ServerTickEvents.END_LEVEL_TICK.register(level -> {

            for (var entity : level.getAllEntities()) {

                if (entity instanceof Skeleton skeleton) {
                    SkeletonEvents.onTick(skeleton);
                }

            }

        });

        // Arrow
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {
            if (entity instanceof AbstractArrow arrow) {
                SkeletonEvents.onArrow(arrow);
            }
        });

        // Death
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Skeleton skeleton))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            SkeletonEvents.onDrops(level, skeleton);

            double multiplier = ModConfigs.getSkeleton().xpMultiplier;

            if (multiplier > 1.0) {
                int baseXP = 5;
                int extraXP = (int) ((multiplier - 1.0) * baseXP);

                if (extraXP > 0) {
                    ExperienceOrb.award(level, skeleton.position(), extraXP);
                }
            }
        });
    }
}