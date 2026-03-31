package com.reggarf.mods.mob_better_config.platform;

import com.reggarf.mods.mob_better_config.data.MobStats;
import com.reggarf.mods.mob_better_config.data.ModAttachments;
import com.reggarf.mods.mob_better_config.platform.services.IPlatformHelper;
import net.minecraft.world.entity.Mob;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.getCurrent().isProduction();
    }
    @Override
    public MobStats getMobStats(Mob mob) {
        return mob.getData(ModAttachments.MOB_STATS.get());
    }
}
