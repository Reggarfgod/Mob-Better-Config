package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.WitherEvents;

import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.wither.WitherBoss;

public class NeoForgeWitherEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof WitherBoss wither))
            return;

        if (!(wither.level() instanceof ServerLevel level))
            return;

        WitherEvents.onSpawn(wither, level);
    }

    @SubscribeEvent
    public void onDamage(LivingIncomingDamageEvent event) {

        if (!(event.getSource().getDirectEntity() instanceof WitherSkull skull))
            return;

        float newDamage = WitherEvents.modifySkullDamage(
                skull,
                event.getAmount()
        );

        event.setAmount(newDamage);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof WitherBoss))
            return;

        event.setDroppedExperience(
                (int) WitherEvents.modifyXP(event.getDroppedExperience())
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof WitherBoss wither))
            return;

        if (!(wither.level() instanceof ServerLevel level))
            return;

        WitherEvents.onDrops(level, wither);
    }
}