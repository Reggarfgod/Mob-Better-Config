package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.HoglinEvents;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.damagesource.DamageSource;

public class FabricHoglinEvents {

    public static void register() {

        /* Spawn */

        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (!(entity instanceof Hoglin hoglin))
                return;

            if (!(level instanceof ServerLevel serverLevel))
                return;

            HoglinEvents.onSpawn(hoglin, serverLevel);
        });


        /* Damage modifier */

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {

            if (source.getEntity() instanceof Hoglin) {

                float newDamage = HoglinEvents.modifyAttackDamage(amount);

                entity.hurt(source, newDamage);
                return false;
            }

            return true;
        });


        /* Death (loot + xp) */

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Hoglin hoglin))
                return;

            if (!(hoglin.level() instanceof ServerLevel level))
                return;

            HoglinEvents.onDrops(level, hoglin);
        });
    }
}