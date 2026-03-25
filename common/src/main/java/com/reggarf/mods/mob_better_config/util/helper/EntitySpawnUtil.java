package com.reggarf.mods.mob_better_config.util.helper;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;

public class EntitySpawnUtil {

    public static Vex createVex(ServerLevel level) {
        try {
            Class<?> entityTypeClass = EntityType.VEX.getClass();

            try {
                // NEW versions (1.20.5+ / 1.21+)
                Class<?> spawnReasonClass = Class.forName("net.minecraft.world.entity.EntitySpawnReason");
                Object reason = spawnReasonClass.getField("EVENT").get(null);

                return (Vex) entityTypeClass
                        .getMethod("create", net.minecraft.world.level.Level.class, spawnReasonClass)
                        .invoke(EntityType.VEX, level, reason);

            } catch (ClassNotFoundException | NoSuchMethodException ignored) {
                // OLD versions (1.20.1)
                return (Vex) entityTypeClass
                        .getMethod("create", net.minecraft.world.level.Level.class)
                        .invoke(EntityType.VEX, level);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getSummonReason() {
        try {
            // NEW
            Class<?> clazz = Class.forName("net.minecraft.world.entity.EntitySpawnReason");
            return clazz.getField("MOB_SUMMONED").get(null);

        } catch (Exception ignored) {
            try {
                // OLD
                Class<?> clazz = Class.forName("net.minecraft.world.entity.MobSpawnType");
                return clazz.getField("MOB_SUMMONED").get(null);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static void finalizeSpawn(Vex vex, ServerLevel level) {
        try {
            Object reason = getSummonReason();

            vex.getClass()
                    .getMethod(
                            "finalizeSpawn",
                            ServerLevelAccessor.class,
                            DifficultyInstance.class,
                            reason.getClass(),
                            SpawnGroupData.class
                    )
                    .invoke(
                            vex,
                            level,
                            level.getCurrentDifficultyAt(vex.blockPosition()),
                            reason,
                            null
                    );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T extends net.minecraft.world.entity.Mob> T createMob(
            net.minecraft.world.entity.EntityType<T> type,
            net.minecraft.server.level.ServerLevel level
    ) {
        try {
            Class<?> entityTypeClass = type.getClass();

            try {
                // NEW versions
                Class<?> spawnReasonClass = Class.forName("net.minecraft.world.entity.EntitySpawnReason");
                Object reason = spawnReasonClass.getField("EVENT").get(null);

                return (T) entityTypeClass
                        .getMethod("create", net.minecraft.world.level.Level.class, spawnReasonClass)
                        .invoke(type, level, reason);

            } catch (ClassNotFoundException | NoSuchMethodException ignored) {
                // OLD versions
                return (T) entityTypeClass
                        .getMethod("create", net.minecraft.world.level.Level.class)
                        .invoke(type, level);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}