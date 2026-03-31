package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.HoglinEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.hoglin.Hoglin;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class NeoForgeHoglinEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Hoglin hoglin))
            return;

        if (!(hoglin.level() instanceof ServerLevel level))
            return;

        HoglinEvents.onSpawn(hoglin, level);
    }

    @SubscribeEvent
    public void onDamage(LivingDamageEvent.Pre event) {

        if (!(event.getSource().getEntity() instanceof Hoglin))
            return;

        float newDamage = HoglinEvents.modifyAttackDamage(event.getNewDamage());

        event.setNewDamage(newDamage);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof Hoglin))
            return;

        int xp = event.getDroppedExperience();

        xp = (int) HoglinEvents.modifyXP(xp);

        event.setDroppedExperience(xp);
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Hoglin hoglin))
            return;

        if (!(hoglin.level() instanceof ServerLevel level))
            return;

        HoglinEvents.onDrops(level, hoglin);
    }
}