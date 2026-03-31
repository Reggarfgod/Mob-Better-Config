package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.ZombieEvents;

import net.minecraft.server.level.ServerLevel;


import net.minecraft.world.entity.monster.zombie.Zombie;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NeoForgeZombieEvents {

    @SubscribeEvent
    public void spawn(FinalizeSpawnEvent event) {

        if (event.getEntity() instanceof Zombie zombie &&
                zombie.level() instanceof ServerLevel level) {

            ZombieEvents.onSpawn(zombie, level);
        }
    }

    @SubscribeEvent
    public void tick(EntityTickEvent.Post event) {

        if (event.getEntity() instanceof Zombie zombie) {

            ZombieEvents.onTick(zombie);
        }
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (event.getEntity() instanceof Zombie zombie) {

            float xp = event.getDroppedExperience();
            xp = ZombieEvents.modifyXP(xp);
            event.setDroppedExperience((int) xp);
        }
    }

    @SubscribeEvent
    public void drops(LivingDropsEvent event) {

        if (event.getEntity() instanceof Zombie zombie &&
                zombie.level() instanceof ServerLevel level) {

            ZombieEvents.onDrops(level, zombie);
        }
    }
}