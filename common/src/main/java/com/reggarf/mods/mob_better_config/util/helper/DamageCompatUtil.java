package com.reggarf.mods.mob_better_config.util.helper;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class DamageCompatUtil {

    public static boolean hurt(Entity entity, ServerLevel level, DamageSource source, float damage) {
        try {
            // 1.21+ REAL call
            return (boolean) entity.getClass()
                    .getMethod("hurtServer", ServerLevel.class, DamageSource.class, float.class)
                    .invoke(entity, level, source, damage);

        } catch (Exception ignored) {
            try {
                return (boolean) entity.getClass()
                        .getMethod("hurt", DamageSource.class, float.class)
                        .invoke(entity, source, damage);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}