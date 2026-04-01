package com.reggarf.mods.mob_better_config.platform;

import com.reggarf.mods.mob_better_config.data.MobStats;
import com.reggarf.mods.mob_better_config.data.ModAttachments;
import com.reggarf.mods.mob_better_config.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.Mob;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public MobStats getMobStats(Mob mob) {
        MobStats stats = mob.getAttached(ModAttachments.MOB_STATS);

        if (stats == null) {
            stats = new MobStats();
            mob.setAttached(ModAttachments.MOB_STATS, stats);
        }

        return stats;
    }
}
