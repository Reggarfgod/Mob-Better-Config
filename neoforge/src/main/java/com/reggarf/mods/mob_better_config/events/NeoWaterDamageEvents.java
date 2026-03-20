package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.util.WaterDamageUtil;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class NeoWaterDamageEvents {

    private static final boolean allowWaterDamage = false;

    @SubscribeEvent
    public static void onDamage(LivingIncomingDamageEvent event) {

        LivingEntity entity = event.getEntity();

        if (WaterDamageUtil.shouldBlockWaterDamage(
                entity,
                event.getSource(),
                allowWaterDamage
        )) {
            event.setCanceled(true);
        }
    }
}