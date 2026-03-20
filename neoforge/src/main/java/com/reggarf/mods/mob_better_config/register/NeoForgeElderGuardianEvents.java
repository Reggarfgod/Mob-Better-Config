package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.ElderGuardianEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.ElderGuardian;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

public class NeoForgeElderGuardianEvents {

    @SubscribeEvent
    public void onJoin(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof ElderGuardian elder))
            return;

        if (!(elder.level() instanceof ServerLevel level))
            return;

        ElderGuardianEvents.onJoin(elder, level);
    }


    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof ElderGuardian elder))
            return;

        if (!(elder.level() instanceof ServerLevel level))
            return;

        ElderGuardianEvents.onDrops(level, elder);
    }
}