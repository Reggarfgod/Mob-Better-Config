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
        return ROOT.overworld.zombie;
    }

    public static SkeletonConfig getSkeleton() {
        return ROOT.overworld.skeleton;
    }

    public static ZombieVillagerConfig getZombieVillager() {
        return ROOT.overworld.zombievillager;
    }

    public static WitchConfig getWitch() {
        return ROOT.overworld.witch;
    }

    public static SpiderConfig getSpider() {
        return ROOT.overworld.spider;
    }

    public static CaveSpiderConfig getCaveSpider() {
        return ROOT.overworld.caveSpider;
    }

    public static CreeperConfig getCreeper() {
        return ROOT.overworld.creeper;
    }

    public static VindicatorConfig getVindicator() {
        return ROOT.overworld.vindicator;
    }

    public static StrayConfig getStray() {
        return ROOT.overworld.stray;
    }

    public static SlimeConfig getSlime() {
        return ROOT.overworld.slime;
    }

    public static HuskConfig getHusk() {
        return ROOT.overworld.husk;
    }

    public static PillagerConfig getPillager() {
        return ROOT.overworld.pillager;
    }

    public static PhantomConfig getPhantom() {
        return ROOT.overworld.phantom;
    }

    public static EndermanConfig getEnderman() {
        return ROOT.overworld.enderman;
    }

    public static DrownedConfig getDrowned() {
        return ROOT.overworld.drowned;
    }

    public static EvokerConfig getEvoker() {
        return ROOT.overworld.evoker;
    }

    public static RavagerConfig getRavager() {
        return ROOT.overworld.ravager;
    }

    public static SilverfishConfig getSilverfish() {
        return ROOT.overworld.silverfish;
    }

    public static GuardianConfig getGuardian() {
        return ROOT.overworld.guardian;
    }

    public static WitherSkeletonConfig getWitherSkeleton() {
        return ROOT.nether.wither_skeleton;
    }

    public static MagmaCubeConfig getMagmaCube() {
        return ROOT.nether.magma_cube;
    }

    public static BlazeConfig getBlaze() {
        return ROOT.nether.blaze;
    }

    public static GhastConfig getGhast() {
        return ROOT.nether.ghast;
    }

    public static HoglinConfig getHoglin() {
        return ROOT.nether.hoglin;
    }

    public static ZoglinConfig getZoglin() {
        return ROOT.nether.zoglin;
    }

    public static PiglinBruteConfig getPiglinBrute() {
        return ROOT.nether.piglinbrute;
    }

    public static ZombifiedPiglinConfig getZombifiedPiglin() {
        return ROOT.nether.piglin;
    }

    public static ShulkerConfig getShulker() {
        return ROOT.end.shulker;
    }

    public static WardenConfig getWarden() {
        return ROOT.bosses.warden;
    }

    public static ElderGuardianConfig getElderGuardian() {
        return ROOT.bosses.elderguardian;
    }

    public static WitherConfig getWither() {
        return ROOT.bosses.wither;
    }

    public static EnderDragonConfig getEnderDragon() {
        return ROOT.bosses.enderdragon;
    }
}