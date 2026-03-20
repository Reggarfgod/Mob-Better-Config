package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.EvokerEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.projectile.EvokerFangs;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NeoForgeEvokerEvents {

    @SubscribeEvent
    public void onJoin(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof Evoker evoker))
            return;

        if (!(evoker.level() instanceof ServerLevel level))
            return;

        EvokerEvents.onJoin(evoker, level);
    }

    @SubscribeEvent
    public void onFangDamage(LivingIncomingDamageEvent event) {

        if (!(event.getSource().getDirectEntity() instanceof EvokerFangs fangs))
            return;

        float newDamage = EvokerEvents.onFangDamage(fangs, event.getAmount());

        event.setAmount(newDamage);
    }
    @SubscribeEvent
    public void onFangs(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof EvokerFangs fangs))
            return;

        if (!EvokerEvents.allowFangs(fangs))
            event.setCanceled(true);
    }


    @SubscribeEvent
    public void onVex(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof Vex vex))
            return;

        if (!(vex.level() instanceof ServerLevel level))
            return;

        if (!EvokerEvents.handleVexSpawn(vex, level))
            event.setCanceled(true);
    }


    @SubscribeEvent
    public void onSheepTick(EntityTickEvent.Post event) {

        if (event.getEntity() instanceof Sheep sheep)
            EvokerEvents.onSheepTick(sheep);
    }


    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Evoker evoker))
            return;

        if (!(evoker.level() instanceof ServerLevel level))
            return;

        EvokerEvents.onDrops(level, evoker);
    }
}