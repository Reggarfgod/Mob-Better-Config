package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.EvokerEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

import net.minecraft.world.entity.animal.sheep.Sheep;

import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.illager.Evoker;
import net.minecraft.world.entity.projectile.EvokerFangs;

public class FabricEvokerEvents {

    public static void register() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {

            if (!(world instanceof ServerLevel level))
                return;

            if (entity instanceof Evoker evoker)
                EvokerEvents.onJoin(evoker, level);

            if (entity instanceof EvokerFangs fangs) {

                if (!EvokerEvents.allowFangs(fangs))
                    fangs.discard();
            }

            if (entity instanceof Vex vex) {

                if (!EvokerEvents.handleVexSpawn(vex, level))
                    vex.discard();
            }
        });
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {

            if (!(source.getDirectEntity() instanceof EvokerFangs fangs))
                return true;

            float newDamage = EvokerEvents.onFangDamage(fangs, amount);

            if (newDamage != amount) {
                entity.hurt(source, newDamage);
                return false;
            }

            return true;
        });

        ServerTickEvents.END_WORLD_TICK.register(level -> {

            for (Entity entity : level.getAllEntities()) {

                if (entity instanceof Sheep sheep)
                    EvokerEvents.onSheepTick(sheep);
            }
        });


        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Evoker evoker))
                return;

            if (!(evoker.level() instanceof ServerLevel level))
                return;

            EvokerEvents.onDrops(level, evoker);
        });
    }
}