package com.reggarf.mods.mob_better_config.util.helper;

import net.minecraft.world.entity.Entity;

import java.lang.reflect.Method;

public class VersionCompatUtil {

    private static Method snapToMethod;
    private static Method moveToMethod;

    static {

        try {
            snapToMethod = Entity.class.getMethod(
                    "snapTo",
                    double.class,
                    double.class,
                    double.class
            );
        } catch (Throwable ignored) {}

        try {
            moveToMethod = Entity.class.getMethod(
                    "moveTo",
                    double.class,
                    double.class,
                    double.class
            );
        } catch (Throwable ignored) {}
    }

    public static void setEntityPos(Entity entity, double x, double y, double z) {

        try {

            if (snapToMethod != null) {
                snapToMethod.invoke(entity, x, y, z);
                return;
            }

            if (moveToMethod != null) {
                moveToMethod.invoke(entity, x, y, z);
            }

        } catch (Throwable ignored) {}
    }

    public static boolean isClientSide(Entity entity) {

        try {

            Method m = entity.level().getClass().getMethod("isClientSide");
            return (boolean) m.invoke(entity.level());

        } catch (Throwable ignored) {}

        try {

            return (boolean) entity.level()
                    .getClass()
                    .getField("isClientSide")
                    .get(entity.level());

        } catch (Throwable ignored) {}

        return false;
    }
}