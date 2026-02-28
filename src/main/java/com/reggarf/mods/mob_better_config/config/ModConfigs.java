package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class ModConfigs {

    private static MobBetterConfigRoot ROOT;

    public static void register() {
        AutoConfig.register(MobBetterConfigRoot.class, GsonConfigSerializer::new);
        ROOT = AutoConfig.getConfigHolder(MobBetterConfigRoot.class).getConfig();
    }

    public static ZombieConfig getZombie() {
        return ROOT.zombie;
    }

    public static SkeletonConfig getSkeleton() {
        return ROOT.skeleton;
    }
    public static ZombieVillagerConfig getZombieVillager() {
        return ROOT.zombieVillager;
    }
    public static WitchConfig getWitch() {
        return ROOT.witch;
    }
    public static SpiderConfig getSpider() {
        return ROOT.spider;
    }

    public static CaveSpiderConfig getCaveSpider() {
        return ROOT.caveSpider;
    }
    public static CreeperConfig getCreeper() {
        return ROOT.creeper;
    }
}