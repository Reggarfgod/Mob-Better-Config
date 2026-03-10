package com.reggarf.mods.mob_better_config.config;

import net.minecraft.world.entity.EntityType;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class ModConfigs {

    private static MobBetterConfigRoot ROOT;

    public static void register() {

        AutoConfig.register(
                MobBetterConfigRoot.class,
                GsonConfigSerializer::new
        );

        ROOT = AutoConfig
                .getConfigHolder(MobBetterConfigRoot.class)
                .getConfig();
    }

    public static MobConfig get(EntityType<?> type) {

        if (ROOT == null)
            return new MobConfig();


        if (type == EntityType.ZOMBIE)
            return ROOT.overworld.zombie;

        if (type == EntityType.SKELETON)
            return ROOT.overworld.skeleton;

        if (type == EntityType.CREEPER)
            return ROOT.overworld.creeper;

        if (type == EntityType.SPIDER)
            return ROOT.overworld.spider;

//        if (type == EntityType.CAVE_SPIDER)
//            return ROOT.overworld.caveSpider;

        if (type == EntityType.ENDERMAN)
            return ROOT.overworld.enderman;

        if (type == EntityType.WITCH)
            return ROOT.overworld.witch;

//        if (type == EntityType.DROWNED)
//            return ROOT.overworld.drowned;
//
//        if (type == EntityType.PHANTOM)
//            return ROOT.overworld.phantom;
//
//        if (type == EntityType.PILLAGER)
//            return ROOT.overworld.pillager;
//
//        if (type == EntityType.VINDICATOR)
//            return ROOT.overworld.vindicator;
//
//        if (type == EntityType.RAVAGER)
//            return ROOT.overworld.ravager;

//        if (type == EntityType.SLIME)
//            return ROOT.overworld.slime;

//        if (type == EntityType.HUSK)
//            return ROOT.overworld.husk;

//        if (type == EntityType.STRAY)
//            return ROOT.overworld.stray;

//        if (type == EntityType.SILVERFISH)
//            return ROOT.overworld.silverfish;

//        if (type == EntityType.GUARDIAN)
//            return ROOT.overworld.guardian;


        /*NETHER*/
        if (type == EntityType.BLAZE)
            return ROOT.nether.blaze;

        if (type == EntityType.GHAST)
            return ROOT.nether.ghast;

//        if (type == EntityType.MAGMA_CUBE)
//            return ROOT.nether.magma_cube;
//
//        if (type == EntityType.WITHER_SKELETON)
//            return ROOT.nether.wither_skeleton;
//
//        if (type == EntityType.ZOGLIN)
//            return ROOT.nether.zoglin;
//
//        if (type == EntityType.HOGLIN)
//            return ROOT.nether.hoglin;

//        if (type == EntityType.PIGLIN_BRUTE)
//            return ROOT.nether.piglinbrute;

//        if (type == EntityType.ZOMBIFIED_PIGLIN)
//            return ROOT.nether.piglin;

        /* ===================== */
        /* END                   */
        /* ===================== */

        if (type == EntityType.SHULKER)
            return ROOT.end.shulker;

        /* ===================== */
        /* BOSSES                */
        /* ===================== */

        if (type == EntityType.WARDEN)
            return ROOT.bosses.warden;

//        if (type == EntityType.ELDER_GUARDIAN)
//            return ROOT.bosses.elderguardian;

        if (type == EntityType.WITHER)
            return ROOT.bosses.wither;

//        if (type == EntityType.ENDER_DRAGON)
//            return ROOT.bosses.enderdragon;

        return new MobConfig();
    }
}