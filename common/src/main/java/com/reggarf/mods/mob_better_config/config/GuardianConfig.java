package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_guardian")
public class GuardianConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;



    @ConfigEntry.Gui.Tooltip
    public double health = 30.0D;
    @ConfigEntry.Gui.Tooltip
    public float armor= 2.0F;
    @ConfigEntry.Gui.Tooltip
    public double armorToughness = 0.0D;
    @ConfigEntry.Gui.Tooltip
    public double knockbackResistance = 0.6;
    @ConfigEntry.Gui.Tooltip
    public double attackKnockback = 0.0;
    @ConfigEntry.Gui.Tooltip
    public double attackDamage = 6.0D;
    @ConfigEntry.Gui.Tooltip
    public double attackSpeed = 4.0D;
    @ConfigEntry.Gui.Tooltip
    public double stepHeight = 0.6D;

    @ConfigEntry.Gui.Tooltip
    public double gravity = 0.08D;

    @ConfigEntry.Gui.Tooltip
    public double movementSpeed = 0.5D;

    @ConfigEntry.Gui.Tooltip
    public double followRange = 16.0D;

    @ConfigEntry.Gui.Tooltip
    public double reinforcementChance = 0.0D;

    @ConfigEntry.Gui.Tooltip
    public boolean enableLaser = true;

    @ConfigEntry.Gui.Tooltip
    public double laserDamageMultiplier = 1.0D;

    @ConfigEntry.Gui.Tooltip
    public int attackDuration = 80;


    @ConfigEntry.Gui.Tooltip
    public boolean enableThorns = true;

    @ConfigEntry.Gui.Tooltip
    public double thornsDamage = 2.0D;

    @ConfigEntry.Gui.Tooltip
    public boolean targetPlayers = true;

    @ConfigEntry.Gui.Tooltip
    public boolean targetSquid = true;

    @ConfigEntry.Gui.Tooltip
    public boolean targetAxolotl = true;

    @ConfigEntry.Gui.Tooltip
    public double xpMultiplier = 1.0D;

    @ConfigEntry.Gui.Tooltip
    public double lootMultiplier = 1.0D;

    @ConfigEntry.Gui.Tooltip
    public boolean fireImmune = false;

    @ConfigEntry.Gui.Tooltip
    public boolean glowing = false;

    @ConfigEntry.Gui.Tooltip
    public boolean canBreakDoors = false;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 5)
    public int doorBreakMode = 3;

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
    public boolean bossGlowing = true;

    @ConfigEntry.Gui.Tooltip
    public boolean bossCustomName = true;

    @ConfigEntry.Gui.Tooltip
    public double bossXpMultiplier = 2.0D;

    @ConfigEntry.Gui.Tooltip
    public double bossLootMultiplier = 2.0D;
}