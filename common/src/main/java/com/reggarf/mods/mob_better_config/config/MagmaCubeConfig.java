package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_magma_cube")
public class MagmaCubeConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;

    @ConfigEntry.Gui.Tooltip
    public double healthMultiplier = 1.0D;

    @ConfigEntry.Gui.Tooltip
    public double attackDamageMultiplier = 1.0D;

    @ConfigEntry.Gui.Tooltip
    public double xpMultiplier = 1.0D;

    @ConfigEntry.Gui.Tooltip
    public double lootMultiplier = 1.0D;



    @ConfigEntry.Gui.Tooltip
    public int fixedSize = -1; // -1 = vanilla size

    @ConfigEntry.Gui.Tooltip
    public double sizeMultiplier = 1.0D;

    @ConfigEntry.Gui.Tooltip
    public int minSize = 1;

    @ConfigEntry.Gui.Tooltip
    public int maxSize = 127;

    @ConfigEntry.Gui.Tooltip
    public boolean allowSplit = true;

    @ConfigEntry.Gui.Tooltip
    public boolean preventSplitWhenBoss = true;

    @ConfigEntry.Gui.Tooltip
    public double splitSizeMultiplier = 0.5D; // Vanilla: size / 2

    @ConfigEntry.Gui.Tooltip
    public int minSplitCount = 2; // Vanilla: 2

    @ConfigEntry.Gui.Tooltip
    public int maxSplitCount = 4; // Vanilla: 4



    @ConfigEntry.Gui.Tooltip
    public double baseMovementSpeed = 0.2D; // close to vanilla

    @ConfigEntry.Gui.Tooltip
    public double speedPerSize = 0.1D;

    @ConfigEntry.Gui.Tooltip
    public double knockbackStrength = 0.0D;


    @ConfigEntry.Gui.Tooltip
    public boolean damageScalesWithSize = true;

    @ConfigEntry.Gui.Tooltip
    public boolean despawnInPeaceful = true;

    @ConfigEntry.Gui.Tooltip
    public int spawnMultiplier = 1;
//
//    @ConfigEntry.Gui.Tooltip
//    public double reinforcementChance = 0.0D;


    @ConfigEntry.Gui.Tooltip
    public boolean glowing = false;


    @ConfigEntry.Gui.Tooltip
    public boolean bossMode = true;

    @ConfigEntry.Gui.Tooltip
    public boolean forceAllBoss = false;

    @ConfigEntry.Gui.Tooltip
    public double bossChance = 0.01D;

    @ConfigEntry.Gui.Tooltip
    public double bossHealthMultiplier = 3.0D;

    @ConfigEntry.Gui.Tooltip
    public double bossDamageMultiplier = 2.0D;

    @ConfigEntry.Gui.Tooltip
    public double bossXpMultiplier = 5.0D;
    @ConfigEntry.Gui.Tooltip
    public double bossLootMultiplier = 2.0D;

    @ConfigEntry.Gui.Tooltip
    public boolean bossGlowing = true;

    @ConfigEntry.Gui.Tooltip
    public boolean bossCustomName = true;
}