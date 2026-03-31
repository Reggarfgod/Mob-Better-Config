package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.WardenEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NeoForgeWardenEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Warden warden))
            return;

        if (!(warden.level() instanceof ServerLevel level))
            return;

        WardenEvents.onSpawn(warden, level);
    }

    @SubscribeEvent
    public void onDamage(LivingDamageEvent.Pre event) {

        if (!(event.getSource().getEntity() instanceof Warden warden))
            return;

        float newDamage = WardenEvents.modifyDamage(warden, event.getNewDamage());
        event.setNewDamage(newDamage);
    }

    @SubscribeEvent
    public void onHurt(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Warden warden))
            return;

        if (!(warden.level() instanceof ServerLevel level))
            return;

        WardenEvents.onHurt(warden, level, event.getSource());
    }

    @SubscribeEvent
    public void onProjectile(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Warden warden))
            return;

        if (!(event.getSource().getDirectEntity() instanceof Projectile proj))
            return;

        if (!(proj.getOwner() instanceof net.minecraft.world.entity.LivingEntity attacker))
            return;

        WardenEvents.onProjectile(warden, attacker);
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (event.getEntity() instanceof Warden warden &&
            warden.level() instanceof ServerLevel level) {

            WardenEvents.onTick(warden, level);
        }
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof Warden))
            return;

        event.setDroppedExperience(
                (int) WardenEvents.modifyXP(event.getDroppedExperience())
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Warden warden))
            return;

        if (!(warden.level() instanceof ServerLevel level))
            return;

        WardenEvents.onDrops(level, warden);
    }
}