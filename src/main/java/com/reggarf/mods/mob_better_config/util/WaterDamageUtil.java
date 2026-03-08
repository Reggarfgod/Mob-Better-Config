package com.reggarf.mods.mob_better_config.util;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class WaterDamageUtil {

    /**
     * Completely blocks water damage (including animation)
     * when allowWaterDamage is false.
     */
    public static void handleWaterDamage(
            LivingIncomingDamageEvent event,
            LivingEntity entity,
            boolean allowWaterDamage
    ) {

        if (allowWaterDamage)
            return;

        // Block drowning damage type
        if (event.getSource().is(DamageTypeTags.IS_DROWNING)) {
            event.setCanceled(true);
            return;
        }

        // Block wet damage (rain, water)
        if (entity.isInWaterOrRain()) {
            event.setCanceled(true);
        }
    }
}