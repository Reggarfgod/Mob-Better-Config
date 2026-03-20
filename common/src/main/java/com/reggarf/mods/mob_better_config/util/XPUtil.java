package com.reggarf.mods.mob_better_config.util;

public class XPUtil {

    public static int multiplyXP(int xp, double multiplier) {

        if (multiplier == 1.0)
            return xp;

        return (int) (xp * multiplier);
    }
}