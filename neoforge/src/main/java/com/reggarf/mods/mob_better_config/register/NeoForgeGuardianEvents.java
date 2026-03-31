package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.GuardianEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Guardian;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NeoForgeGuardianEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Guardian guardian))
            return;

        if (!(guardian.level() instanceof ServerLevel level))
            return;

        GuardianEvents.onSpawn(guardian, level);
    }
    @SubscribeEvent
    public void onPreTick(EntityTickEvent.Pre event) {

        if (!(event.getEntity() instanceof Guardian guardian))
            return;

        GuardianEvents.onPreTick(guardian);
    }

    @SubscribeEvent
    public void onPostTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Guardian guardian))
            return;

        GuardianEvents.onPostTick(guardian);
    }

    @SubscribeEvent
    public void onDamage(LivingIncomingDamageEvent event) {

        if (!(event.getEntity() instanceof Guardian guardian))
            return;

        if (!(event.getSource().getDirectEntity() instanceof LivingEntity attacker))
            return;

        GuardianEvents.handleThorns(guardian, attacker);
    }

    /* ---------------- XP ---------------- */

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof Guardian))
            return;

        int xp = event.getDroppedExperience();

        xp = (int) GuardianEvents.onXP(xp);

        event.setDroppedExperience(xp);
    }

    /* ---------------- Drops ---------------- */

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Guardian guardian))
            return;

        if (!(guardian.level() instanceof ServerLevel level))
            return;

        GuardianEvents.onDrops(level, guardian);
    }
}