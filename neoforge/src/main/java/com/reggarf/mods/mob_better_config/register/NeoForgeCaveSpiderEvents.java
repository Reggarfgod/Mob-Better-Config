package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.CaveSpiderEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.spider.CaveSpider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NeoForgeCaveSpiderEvents {

    @SubscribeEvent
    public static void onSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof CaveSpider spider))
            return;

        if (!(spider.level() instanceof ServerLevel level))
            return;

        CaveSpiderEvents.onSpawn(spider, level);
    }

    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof CaveSpider spider))
            return;

        if (!(spider.level() instanceof ServerLevel level))
            return;

        CaveSpiderEvents.onDrops(level, spider);
    }
}