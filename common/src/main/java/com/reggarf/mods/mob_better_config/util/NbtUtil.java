package com.reggarf.mods.mob_better_config.util;

import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Method;

public class NbtUtil {

    private NbtUtil() {}

    public static boolean getBooleanSafe(CompoundTag tag, String key) {
        try {
            Method m = CompoundTag.class.getMethod("getBoolean", String.class);
            return (boolean) m.invoke(tag, key);
        } catch (NoSuchMethodException e) {
            try {
                Method m = CompoundTag.class.getMethod("getBooleanTag", String.class);
                return (boolean) m.invoke(tag, key);
            } catch (Throwable ignored) {}
        } catch (Throwable ignored) {}

        return false;
    }

    public static void putBooleanSafe(CompoundTag tag, String key, boolean value) {
        try {
            Method m = CompoundTag.class.getMethod("putBoolean", String.class, boolean.class);
            m.invoke(tag, key, value);
        } catch (Throwable ignored) {}
    }

    public static int getIntSafe(CompoundTag tag, String key) {
        try {
            Method m = CompoundTag.class.getMethod("getInt", String.class);
            return (int) m.invoke(tag, key);
        } catch (NoSuchMethodException e) {
            try {
                Method m = CompoundTag.class.getMethod("getIntTag", String.class);
                return (int) m.invoke(tag, key);
            } catch (Throwable ignored) {}
        } catch (Throwable ignored) {}

        return 0;
    }

    public static void putIntSafe(CompoundTag tag, String key, int value) {
        try {
            Method m = CompoundTag.class.getMethod("putInt", String.class, int.class);
            m.invoke(tag, key, value);
        } catch (Throwable ignored) {}
    }

    public static double getDoubleSafe(CompoundTag tag, String key) {
        try {
            Method m = CompoundTag.class.getMethod("getDouble", String.class);
            return (double) m.invoke(tag, key);
        } catch (NoSuchMethodException e) {
            try {
                Method m = CompoundTag.class.getMethod("getDoubleTag", String.class);
                return (double) m.invoke(tag, key);
            } catch (Throwable ignored) {}
        } catch (Throwable ignored) {}
        return 0.0;
    }

    public static void putDoubleSafe(CompoundTag tag, String key, double value) {
        try {
            Method m = CompoundTag.class.getMethod("putDouble", String.class, double.class);
            m.invoke(tag, key, value);
        } catch (Throwable ignored) {}
    }
}