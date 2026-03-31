package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.HuskEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;


import net.minecraft.world.entity.monster.zombie.Husk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NeoForgeHuskEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Husk husk))
            return;

        if (!(husk.level() instanceof ServerLevel level))
            return;

        HuskEvents.onSpawn(husk, level);
    }

    @SubscribeEvent
    public void onDamage(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof LivingEntity target))
            return;

        if (!(event.getSource().getEntity() instanceof LivingEntity attacker))
            return;

        HuskEvents.onDamage(target, attacker);
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

//        if (event.getEntity() instanceof Husk husk)
//            HuskEvents.onTick(husk);

        if (event.getEntity() instanceof LivingEntity living)
            HuskEvents.onTargetTick(living);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof Husk))
            return;

        float xp = event.getDroppedExperience();

        event.setDroppedExperience((int) HuskEvents.modifyXP(xp));
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Husk husk))
            return;

        if (!(husk.level() instanceof ServerLevel level))
            return;

        HuskEvents.onDrops(level, husk);
    }
}