package com.reggarf.mods.mob_better_config.platform.services;

import net.minecraft.world.entity.Mob;
import com.reggarf.mods.mob_better_config.data.MobStats;

public interface IPlatformHelper {

    String getPlatformName();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }

    MobStats getMobStats(Mob mob);
}