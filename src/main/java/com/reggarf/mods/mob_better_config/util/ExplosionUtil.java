package com.reggarf.mods.mob_better_config.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.neoforged.neoforge.event.level.ExplosionEvent;

import java.lang.reflect.Field;

public class ExplosionUtil {

    /**
     * Scales explosion radius safely using reflection.
     *
     * @param event ExplosionEvent.Start
     * @param sourceEntity entity causing explosion
     * @param multiplier radius multiplier (1.0 = vanilla)
     */
    public static void applyExplosionMultiplier(
            ExplosionEvent.Start event,
            Entity sourceEntity,
            double multiplier
    ) {

        if (multiplier == 1.0D || multiplier <= 0.0D)
            return;

        Explosion explosion = event.getExplosion();

        if (explosion.getIndirectSourceEntity() != sourceEntity)
            return;

        try {
            Field radiusField = Explosion.class.getDeclaredField("radius");
            radiusField.setAccessible(true);

            float currentRadius = (float) radiusField.get(explosion);
            float newRadius = (float) (currentRadius * multiplier);

            radiusField.set(explosion, newRadius);

        } catch (Exception ignored) {}
    }
}