package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.WitchEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;


public class NeoForgeWitchEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Witch witch))
            return;

        if (!(witch.level() instanceof ServerLevel level))
            return;

        WitchEvents.onSpawn(witch, level);
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (event.getEntity() instanceof Witch witch) {
            WitchEvents.onTick(witch);
        }
    }

    @SubscribeEvent
    public void onPotionImpact(ProjectileImpactEvent event) {

        if (!(event.getProjectile() instanceof AbstractThrownPotion potion))
            return;

        if (!(potion.level() instanceof ServerLevel level))
            return;

        WitchEvents.onPotionImpact(potion, level);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof Witch))
            return;

        event.setDroppedExperience(
                (int) WitchEvents.modifyXP(event.getDroppedExperience())
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Witch witch))
            return;

        if (!(witch.level() instanceof ServerLevel level))
            return;

        WitchEvents.onDrops(level, witch);
    }
}