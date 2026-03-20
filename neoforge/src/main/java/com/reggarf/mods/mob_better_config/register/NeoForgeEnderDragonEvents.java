package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.EnderDragonEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

public class NeoForgeEnderDragonEvents {

    @SubscribeEvent
    public void onJoin(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof EnderDragon dragon))
            return;

        if (!(dragon.level() instanceof ServerLevel level))
            return;

        EnderDragonEvents.onJoin(dragon, level);
    }

    @SubscribeEvent
    public void onDragonBreathDamage(LivingIncomingDamageEvent event) {

        float newDamage = EnderDragonEvents.modifyDragonBreathDamage(
                event.getSource(),
                event.getAmount()
        );

        event.setAmount(newDamage);
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof EnderDragon dragon))
            return;

        if (!(dragon.level() instanceof ServerLevel level))
            return;

        EnderDragonEvents.onDrops(level, dragon);
    }
}