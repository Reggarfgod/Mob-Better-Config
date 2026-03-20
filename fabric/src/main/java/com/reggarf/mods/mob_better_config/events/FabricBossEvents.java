package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.util.BossUtil;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class FabricBossEvents {

    public static void register() {

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {

            if (!(entity.level() instanceof ServerLevel level))
                return;

            if (!BossUtil.isBoss(entity))
                return;

            double lootMultiplier = BossUtil.getLootMultiplier(entity);

            // Fabric requires custom drop logic here
        });

    }

}