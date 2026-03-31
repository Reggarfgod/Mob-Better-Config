package com.reggarf.mods.mob_better_config.data;

import com.reggarf.mods.mob_better_config.platform.Services;
import net.minecraft.world.entity.Mob;

public class MobData {

    public static MobStats get(Mob mob) {
        return Services.PLATFORM.getMobStats(mob);
    }
}