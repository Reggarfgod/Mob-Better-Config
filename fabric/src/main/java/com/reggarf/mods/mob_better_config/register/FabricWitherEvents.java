package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.WitherEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;


public class FabricWitherEvents {

    public static void register() {

        // SPAWN
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (entity instanceof WitherBoss wither && level instanceof ServerLevel serverLevel) {
                WitherEvents.onSpawn(wither, serverLevel);
            }

        });

        // DAMAGE (skull scaling workaround)
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {

            if (source.getDirectEntity() instanceof WitherSkull skull) {

                float newDamage = WitherEvents.modifySkullDamage(skull, amount);

                if (newDamage > amount && entity.level() instanceof ServerLevel level) {
                    entity.hurt(
                            entity.damageSources().magic(),
                            newDamage - amount
                    );
                }
            }

            return true;
        });

        // DEATH
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof WitherBoss wither))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            // Loot
            WitherEvents.onDrops(level, wither);

            // XP
            double multiplier = ModConfigs.getWither().xpMultiplier;

            if (multiplier > 1.0) {

                int baseXP = 50; // Wither boss level XP
                int extraXP = (int)((multiplier - 1.0) * baseXP);

                if (extraXP > 0) {
                    ExperienceOrb.award(level, wither.position(), extraXP);
                }
            }
        });
    }
}