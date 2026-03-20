package com.reggarf.mods.mob_better_config.util;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class WaterDamageUtil {

    /**
     * Returns true if water damage should be blocked.
     */
    public static boolean shouldBlockWaterDamage(LivingEntity entity, DamageSource source, boolean allowWaterDamage) {

        // If config allows water damage → do not block
        if (allowWaterDamage) {
            return false;
        }

        // Only block drowning damage
        if (source != null && source.is(DamageTypeTags.IS_DROWNING)) {
            return true;
        }

        return false;
    }
}