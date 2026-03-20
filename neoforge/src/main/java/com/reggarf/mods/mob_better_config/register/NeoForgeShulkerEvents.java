package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.ShulkerEvents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Shulker;

public class NeoForgeShulkerEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Shulker shulker))
            return;

        if (!(shulker.level() instanceof ServerLevel level))
            return;

        ShulkerEvents.onSpawn(shulker, level);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof Shulker))
            return;

        event.setDroppedExperience(
                (int) ShulkerEvents.modifyXP(event.getDroppedExperience())
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Shulker shulker))
            return;

        if (!(shulker.level() instanceof ServerLevel level))
            return;

        ShulkerEvents.onDrops(level, shulker);
    }
}