package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_husk")
public class HuskConfig implements ConfigData {


    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;
    @ConfigEntry.Gui.Tooltip
    public double health = 20;
    @ConfigEntry.Gui.Tooltip
    public float armor= 2.0F;
    @ConfigEntry.Gui.Tooltip
    public double knockbackResistance = 0.6;
    @ConfigEntry.Gui.Tooltip
    public double attackKnockback = 0.0;
    @ConfigEntry.Gui.Tooltip
    public double attackDamage = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public double movementSpeed = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public double followRange = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public double xpMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public boolean enableHunger = true;
    @ConfigEntry.Gui.Tooltip
    public int hungerDuration = 140;        // Vanilla: 140 ticks
    @ConfigEntry.Gui.Tooltip
    public int hungerAmplifier = 0;
    @ConfigEntry.Gui.Tooltip
    public boolean desertBuff = false;
    @ConfigEntry.Gui.Tooltip
    public double desertHealth = 30;
    @ConfigEntry.Gui.Tooltip
    public double desertarmor = 4;
    @ConfigEntry.Gui.Tooltip
    public double desertDamageBonus = 1.5D;
    @ConfigEntry.Gui.Tooltip
    public int spawnMultiplier = 1;
    @ConfigEntry.Gui.Tooltip
    public double reinforcementChance = 0.0D;
    @ConfigEntry.Gui.Tooltip
    public double lootMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public boolean fireImmune = false;
    @ConfigEntry.Gui.Tooltip
    public boolean glowing = false;
    @ConfigEntry.Gui.Tooltip
    public boolean convertInWater = true;
    @ConfigEntry.Gui.Tooltip
    public int waterConversionTime = 300; // Vanilla zombie default ~300 ticks
    @ConfigEntry.Gui.Tooltip
    public boolean bossMode = true;
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