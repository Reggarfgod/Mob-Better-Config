package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.HuskEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.zombie.Husk;


public class FabricHuskEvents {

    public static void register() {

        /* Spawn */

        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (!(entity instanceof Husk husk))
                return;

            if (!(level instanceof ServerLevel serverLevel))
                return;

            HuskEvents.onSpawn(husk, serverLevel);
        });


        /* Damage */

        ServerLivingEntityEvents.AFTER_DAMAGE.register(
                (entity, source, baseDamage, damageTaken, blocked) -> {

                    if (!(entity instanceof LivingEntity target))
                        return;

                    if (!(source.getEntity() instanceof LivingEntity attacker))
                        return;

                    HuskEvents.onDamage(target, attacker);
                });


        /* Tick */

        ServerTickEvents.END_LEVEL_TICK.register(level -> {

            for (Entity entity : level.getAllEntities()) {

//                if (entity instanceof Husk husk)
//                    HuskEvents.onTick(husk);

                if (entity instanceof LivingEntity living)
                    HuskEvents.onTargetTick(living);
            }
        });


        /* Death */

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Husk husk))
                return;

            if (!(husk.level() instanceof ServerLevel level))
                return;

            HuskEvents.onDrops(level, husk);
        });
    }
}