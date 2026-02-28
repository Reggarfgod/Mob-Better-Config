package com.reggarf.mods.mob_better_config.util;

import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

public class XPUtil {

    /**
     * Applies XP multiplier to any LivingExperienceDropEvent
     */
    public static void applyXpMultiplier(LivingExperienceDropEvent event, double multiplier) {

        if (multiplier == 1.0D)
            return;

        int originalXP = event.getOriginalExperience();

        int newXP = (int) Math.max(0, originalXP * multiplier);

        event.setDroppedExperience(newXP);
    }

    /**
     * Applies XP multiplier only if entity matches class type
     */
    public static <T extends Entity> void applyXpIfInstance(
            LivingExperienceDropEvent event,
            Class<T> entityClass,
            double multiplier
    ) {

        if (!entityClass.isInstance(event.getEntity()))
            return;

        applyXpMultiplier(event, multiplier);
    }
}