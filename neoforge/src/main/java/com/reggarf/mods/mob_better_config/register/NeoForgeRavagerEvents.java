package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.RavagerEvents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Ravager;

public class NeoForgeRavagerEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Ravager ravager))
            return;

        if (!(ravager.level() instanceof ServerLevel level))
            return;

        RavagerEvents.onSpawn(ravager, level);
    }


    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof Ravager))
            return;

        event.setDroppedExperience(
                (int) RavagerEvents.modifyXP(event.getDroppedExperience())
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Ravager ravager))
            return;

        if (!(ravager.level() instanceof ServerLevel level))
            return;

        RavagerEvents.onDrops(level, ravager);
    }

}