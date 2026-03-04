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
    public static WardenConfig getWarden() {
        return ROOT.warden;
    }
    public static VindicatorConfig getVindicator() {
        return ROOT.vindicator;
    }
    public static StrayConfig getStray() {
        return ROOT.stray;
    }
    public static SlimeConfig getSlime() {
        return ROOT.slime;
    }
    public static HuskConfig getHusk() {
        return ROOT.husk;
    }
    public static PillagerConfig getPillager() {
        return ROOT.pillager;
    }
    public static PhantomConfig getPhantom() {
        return ROOT.phantom;
    }
    public static EndermanConfig getEnderman() {
        return ROOT.enderman;
    }
    public static WitherSkeletonConfig getWitherSkeleton() {
        return ROOT.wither_skeleton;
    }
    public static DrownedConfig getDrowned() {
        return ROOT.drowned;
    }
    public static MagmaCubeConfig getMagmaCube() {
        return ROOT.magma_cube;
    }
    public static BlazeConfig getBlaze() {
        return ROOT.blaze;
    }
    public static GhastConfig getGhast() {
        return ROOT.ghast;
    }
    public static EvokerConfig getEvoker() {
        return ROOT.evoker;
    }
//  public static VexConfig getVex() { return ROOT.vex; }
    public static RavagerConfig getRavager() {
    return ROOT.ravager;
    }
    public static ShulkerConfig getShulker() {
        return ROOT.shulker;
    }
    public static SilverfishConfig getSilverfish() {
        return ROOT.silverfish;
    }
    public static GuardianConfig getGuardian() {
        return ROOT.guardian;
    }
    public static ElderGuardianConfig getElderGuardian() {
        return ROOT.elderguardian;
    }
    public static WitherConfig getWither() {
        return ROOT.wither;
    }
    public static EnderDragonConfig getEnderDragon() {
        return ROOT.enderdragon;
    }
    public static HoglinConfig getHoglin() {
        return ROOT.hoglin;
    }

    public static ZoglinConfig getZoglin() {
        return ROOT.zoglin;
    }
}