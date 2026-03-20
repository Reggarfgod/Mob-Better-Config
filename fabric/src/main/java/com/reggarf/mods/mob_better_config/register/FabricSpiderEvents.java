package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.events.SpiderEvents;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.Spider;

public class FabricSpiderEvents {

    public static void register() {

        // Spawn
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {
            if (entity instanceof Spider spider && level instanceof ServerLevel serverLevel) {
                SpiderEvents.onSpawn(spider, serverLevel);
            }
        });

        ServerTickEvents.END_WORLD_TICK.register(level -> {

            for (var entity : level.getAllEntities()) {

                if (entity instanceof Spider spider) {
                    SpiderEvents.onTick(spider);
                }

            }

        });


        // Death
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Spider spider))
                return;

            if (!(entity.level() instanceof ServerLevel level))
                return;

            // Loot
            SpiderEvents.onDrops(level, spider);

            // XP
            double multiplier = ModConfigs.getSpider().xpMultiplier;

            if (multiplier > 1.0) {

                int baseXP = 5;
                int extraXP = (int) ((multiplier - 1.0) * baseXP);

                if (extraXP > 0) {
                    ExperienceOrb.award(level, spider.position(), extraXP);
                }
            }
        });
    }
}