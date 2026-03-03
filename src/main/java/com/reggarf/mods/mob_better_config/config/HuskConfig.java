package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_husk")
public class HuskConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;

    public double health = 20;
    public double attackDamageMultiplier = 1.0D;
    public double movementSpeedMultiplier = 1.0D;
    public double followRangeMultiplier = 1.0D;
    public double xpMultiplier = 1.0D;
    public boolean enableHunger = true;
    public int hungerDuration = 140;        // Vanilla: 140 ticks
    public int hungerAmplifier = 0;
    public boolean desertBuff = false;
    public double desertHealth = 30;
    public double desertDamageBonus = 1.5D;
    public int spawnMultiplier = 1;
    public double reinforcementChance = 0.0D;
    public double lootMultiplier = 1.0D;
    public boolean fireImmune = false;
    public boolean glowing = false;
    public boolean convertInWater = true;
    public int waterConversionTime = 300; // Vanilla zombie default ~300 ticks
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