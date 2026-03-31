package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.StrayEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.skeleton.Stray;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NeoForgeStrayEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Stray stray))
            return;

        if (!(stray.level() instanceof ServerLevel level))
            return;

        StrayEvents.onSpawn(stray, level);
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof Stray stray) {
            StrayEvents.onTick(stray);
        }
    }

    @SubscribeEvent
    public void onArrowSpawn(EntityJoinLevelEvent event) {

        if (event.getEntity() instanceof Arrow arrow) {
            StrayEvents.onArrowSpawn(arrow);
        }
    }

    @SubscribeEvent
    public void onArrowDamage(LivingDamageEvent.Pre event) {

        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow))
            return;

        float newDamage = StrayEvents.modifyArrowDamage(
                arrow,
                event.getNewDamage()
        );

        event.setNewDamage(newDamage);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof Stray))
            return;

        event.setDroppedExperience(
                (int) StrayEvents.modifyXP(event.getDroppedExperience())
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Stray stray))
            return;

        if (!(stray.level() instanceof ServerLevel level))
            return;

        StrayEvents.onDrops(level, stray);
    }
}