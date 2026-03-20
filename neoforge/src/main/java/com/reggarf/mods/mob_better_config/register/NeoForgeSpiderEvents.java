package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.SkeletonEvents;
import com.reggarf.mods.mob_better_config.events.SpiderEvents;

import net.minecraft.world.entity.monster.Skeleton;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Spider;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NeoForgeSpiderEvents {

    @SubscribeEvent
    public void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Spider spider))
            return;

        if (!(spider.level() instanceof ServerLevel level))
            return;

        SpiderEvents.onSpawn(spider, level);
    }

    @SubscribeEvent
    public void ontick(EntityTickEvent.Post event) {

        if (event.getEntity() instanceof Spider spider) {
            SpiderEvents.onTick(spider);
        }
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof Spider))
            return;

        event.setDroppedExperience(
                (int) SpiderEvents.modifyXP(event.getDroppedExperience())
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Spider spider))
            return;

        if (!(spider.level() instanceof ServerLevel level))
            return;

        SpiderEvents.onDrops(level, spider);
    }
}