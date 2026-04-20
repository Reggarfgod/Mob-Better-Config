package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.StrayEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.skeleton.Stray;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;


public class FabricStrayEvents {

    public static void register() {

        // Spawn
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {
            if (entity instanceof Stray stray && level instanceof ServerLevel serverLevel) {
                StrayEvents.onSpawn(stray, serverLevel);
            }
        });

        // Tick loop
        ServerTickEvents.END_WORLD_TICK.register(level -> {

            for (var entity : level.getAllEntities()) {

                if (entity instanceof Stray stray) {
                    StrayEvents.onTick(stray);
                }

            }

        });

        // Arrow spawn
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {
            if (entity instanceof Arrow arrow) {
                StrayEvents.onArrowSpawn(arrow);
            }
        });

        // Arrow damage

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {

            if (!(source.getDirectEntity() instanceof AbstractArrow arrow))
                return true;

            float newDamage = StrayEvents.modifyArrowDamage(arrow, amount);

            if (newDamage > amount && entity.level() instanceof ServerLevel level) {

                float extra = newDamage - amount;
                entity.hurt(
                        entity.damageSources().generic(),
                        extra
                );
            }

            return true;
        });

        // Death
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Stray stray))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            StrayEvents.onDrops(level, stray);

            double multiplier = ModConfigs.getStray().xpMultiplier;

            if (multiplier > 1.0) {

                int baseXP = 5;
                int extraXP = (int) ((multiplier - 1.0) * baseXP);

                if (extraXP > 0) {
                    ExperienceOrb.award(level, stray.position(), extraXP);
                }
            }
        });
    }
}