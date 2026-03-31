package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.ZombieVillagerEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.zombie.ZombieVillager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;


public class NeoForgeZombieVillagerEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof ZombieVillager zv))
            return;

        if (!(zv.level() instanceof ServerLevel level))
            return;

        ZombieVillagerEvents.onSpawn(zv, level);
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (event.getEntity() instanceof ZombieVillager zv) {
            ZombieVillagerEvents.onTick(zv);
        }
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof ZombieVillager))
            return;

        event.setDroppedExperience(
                (int) ZombieVillagerEvents.modifyXP(event.getDroppedExperience())
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof ZombieVillager zv))
            return;

        if (!(zv.level() instanceof ServerLevel level))
            return;

        ZombieVillagerEvents.onDrops(level, zv);
    }
}