package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.ZombifiedPiglinEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;


public class NeoForgeZombifiedPiglinEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof ZombifiedPiglin piglin))
            return;

        if (!(piglin.level() instanceof ServerLevel level))
            return;

        ZombifiedPiglinEvents.onSpawn(piglin, level);
    }


    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof ZombifiedPiglin))
            return;

        event.setDroppedExperience(
                (int) ZombifiedPiglinEvents.modifyXP(event.getDroppedExperience())
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof ZombifiedPiglin piglin))
            return;

        if (!(piglin.level() instanceof ServerLevel level))
            return;

        ZombifiedPiglinEvents.onDrops(level, piglin);
    }
}