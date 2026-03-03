package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
@Config(name = "configurable_elderguardian")
public class ElderGuardianConfig {


    @ConfigEntry.Gui.Tooltip
    public boolean customName = true;

    @ConfigEntry.Gui.Tooltip
    public double health = 80.0D; // Vanilla: 80

    @ConfigEntry.Gui.Tooltip
    public double attackDamage = 8.0D; // Vanilla: 8

    @ConfigEntry.Gui.Tooltip
    public double movementSpeed = 0.3D; // Vanilla: 0.3

    @ConfigEntry.Gui.Tooltip
    public boolean glowing = false;



    @ConfigEntry.Gui.Tooltip
    public boolean enableLaser = true;

    @ConfigEntry.Gui.Tooltip
    public double laserDamageMultiplier = 1.0D;


    @ConfigEntry.Gui.Tooltip
    public boolean enableThorns = true;

    @ConfigEntry.Gui.Tooltip
    public double thornsDamage = 4.0D;


    @ConfigEntry.Gui.Tooltip
    public boolean enableMiningFatigue = true;

    @ConfigEntry.Gui.Tooltip
    public int miningFatigueInterval = 1200;

    @ConfigEntry.Gui.Tooltip
    public int miningFatigueRadius = 50;

    @ConfigEntry.Gui.Tooltip
    public int miningFatigueDuration = 6000;

    @ConfigEntry.Gui.Tooltip
    public int miningFatigueAmplifier = 2;


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
    public double bossHealthMultiplier = 2.0D;

    @ConfigEntry.Gui.Tooltip
    public double bossDamageMultiplier = 2.0D;

    @ConfigEntry.Gui.Tooltip
    public boolean bossGlowing = true;

    @ConfigEntry.Gui.Tooltip
    public boolean bossCustomName = true;

    @ConfigEntry.Gui.Tooltip
    public double bossXpMultiplier = 2.0D;

    @ConfigEntry.Gui.Tooltip
    public double bossLootMultiplier = 2.0D;
}