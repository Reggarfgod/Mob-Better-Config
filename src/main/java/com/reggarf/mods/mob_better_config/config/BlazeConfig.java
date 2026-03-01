package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_blaze")
public class BlazeConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public double health = 20.0D;
//    @ConfigEntry.Gui.Tooltip
//    public double attackDamage = 6.0D;
//    @ConfigEntry.Gui.Tooltip
//    public double movementSpeed = 0.23D;
    @ConfigEntry.Gui.Tooltip
    public double followRange = 48.0D;
    @ConfigEntry.Gui.Tooltip
    public double fireballDamageMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public int fireballCount = 1; // vanilla = 1 per burst
    @ConfigEntry.Gui.Tooltip
    public int burstCooldown = 100; // vanilla 100 after 4 shots
    @ConfigEntry.Gui.Tooltip
    public int chargedTime = 60; // vanilla 60
    @ConfigEntry.Gui.Tooltip
    public double verticalBoostMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public double fallSlowMultiplier = 0.6D; // vanilla 0.6
    @ConfigEntry.Gui.Tooltip
    public boolean takeWaterDamage = true;
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
    public boolean bossGlowing = true;
    @ConfigEntry.Gui.Tooltip
    public boolean bossCustomName = true;
}