package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.WitherSkeletonEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class FabricWitherSkeletonEvents {

    public static void register() {

        // SPAWN
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {
            if (entity instanceof WitherSkeleton skeleton && level instanceof ServerLevel serverLevel) {
                WitherSkeletonEvents.onSpawn(skeleton, serverLevel);
            }
        });

//        // TICK LOOP
//        ServerTickEvents.END_WORLD_TICK.register(level -> {
//
//            for (var entity : level.getAllEntities()) {
//
//                if (entity instanceof LivingEntity living) {
//                    WitherSkeletonEvents.onTick(living);
//                }
//
//            }
//
//        });
//
//        // MELEE HIT
//        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
//
//            if (source.getEntity() instanceof WitherSkeleton && entity instanceof LivingEntity target) {
//                WitherSkeletonEvents.onMeleeHit(target);
//            }
//
//            return true;
//        });
//
//        // ARROW
//        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {
//            if (entity instanceof AbstractArrow arrow) {
//                WitherSkeletonEvents.onArrow(arrow);
//            }
//        });

        // DEATH
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof WitherSkeleton skeleton))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            WitherSkeletonEvents.onDrops(level, skeleton);

            double multiplier = ModConfigs.getWitherSkeleton().xpMultiplier;

            if (multiplier > 1.0) {
                int baseXP = 5;
                int extraXP = (int)((multiplier - 1.0) * baseXP);

                if (extraXP > 0) {
                    ExperienceOrb.award(level, skeleton.position(), extraXP);
                }
            }
        });
    }
}