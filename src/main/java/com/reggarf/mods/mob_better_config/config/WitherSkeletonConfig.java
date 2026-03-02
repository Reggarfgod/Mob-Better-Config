package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_wither_skeleton")
public class WitherSkeletonConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;
    @ConfigEntry.Gui.Tooltip
    public double health = 20.0D;
    @ConfigEntry.Gui.Tooltip
    public double randomArmorChance = 0.2D;// Vanilla: 0.20
    @ConfigEntry.Gui.Tooltip
    public double attackDamage = 4.0D;     // Vanilla: 4
    @ConfigEntry.Gui.Tooltip
    public double movementSpeed = 0.25D;   // Vanilla: 0.25
    @ConfigEntry.Gui.Tooltip
    public double followRange = 16.0D;     // Vanilla: 16
    @ConfigEntry.Gui.Tooltip
    public boolean enableWitherEffect = true;
    @ConfigEntry.Gui.Tooltip
    public int witherDuration = 200; // Vanilla: 200 ticks
    @ConfigEntry.Gui.Tooltip
    public int witherAmplifier = 0;
    @ConfigEntry.Gui.Tooltip
    public boolean flamingArrows = true;
    @ConfigEntry.Gui.Tooltip
    public double arrowDamage = 3.0D; // Ranged
    @ConfigEntry.Gui.Tooltip
    public int arrowFireSeconds = 100; // Vanilla: 100
    @ConfigEntry.Gui.Tooltip
    public int spawnMultiplier = 1;
    @ConfigEntry.Gui.Tooltip
    public double reinforcementChance = 0.0D;
    @ConfigEntry.Gui.Tooltip
    public double xpMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public double lootMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public boolean bossMode = false;
    @ConfigEntry.Gui.Tooltip
    public boolean forceAllBoss = false;
    @ConfigEntry.Gui.Tooltip
    public double bossChance = 0.05D;
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