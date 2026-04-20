package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.WardenEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.projectile.Projectile;

public class FabricWardenEvents {

    public static void register() {

        // SPAWN
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (entity instanceof Warden warden && level instanceof ServerLevel serverLevel) {
                WardenEvents.onSpawn(warden, serverLevel);
            }

        });

        // TICK
        ServerTickEvents.END_WORLD_TICK.register(level -> {

            for (var entity : level.getAllEntities()) {

                if (entity instanceof Warden warden && level instanceof ServerLevel serverLevel) {
                    WardenEvents.onTick(warden, serverLevel);
                }

            }

        });

        // DAMAGE (Blaze-style return control)
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {

            // Warden attacking → modify damage
            if (source.getEntity() instanceof Warden warden) {

                float newDamage = WardenEvents.modifyDamage(warden, amount);

                if (newDamage > amount && entity.level() instanceof ServerLevel level) {
                    entity.hurt(
                            entity.damageSources().generic(),
                            newDamage - amount
                    );
                }
            }

            // Warden getting hurt → anger system
            if (entity instanceof Warden warden && entity.level() instanceof ServerLevel level) {
                WardenEvents.onHurt(warden, level, source);
            }

            // Projectile anger
            if (entity instanceof Warden warden &&
                    source.getDirectEntity() instanceof Projectile proj &&
                    proj.getOwner() instanceof net.minecraft.world.entity.LivingEntity attacker) {

                WardenEvents.onProjectile(warden, attacker);
            }

            return true;
        });

        // DEATH
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Warden warden))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            // Loot
            WardenEvents.onDrops(level, warden);

            // XP
            double multiplier = ModConfigs.getWarden().xpMultiplier;

            if (multiplier > 1.0) {

                int baseXP = 5; // Warden normally drops no XP, adjust if needed
                int extraXP = (int)((multiplier - 1.0) * baseXP);

                if (extraXP > 0) {
                    ExperienceOrb.award(level, warden.position(), extraXP);
                }
            }

        });

    }
}