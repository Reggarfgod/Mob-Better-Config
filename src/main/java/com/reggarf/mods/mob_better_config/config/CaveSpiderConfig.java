package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_cave_spider")
public class CaveSpiderConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;
    @ConfigEntry.Gui.Tooltip
    public float health = 12.0F;
    @ConfigEntry.Gui.Tooltip
    public float armor= 2.0F;
    @ConfigEntry.Gui.Tooltip
    public float attackDamage = 2.0F;
    @ConfigEntry.Gui.Tooltip
    public double knockbackResistance = 0.6;
    @ConfigEntry.Gui.Tooltip
    public double attackKnockback = 0.0;
    @ConfigEntry.Gui.Tooltip
    public double movementSpeed = 0.35D;
    @ConfigEntry.Gui.Tooltip
    public double followRange = 25.0D;
    @ConfigEntry.Gui.Tooltip
    public boolean fireImmune = false;
    @ConfigEntry.Gui.Tooltip
    public boolean glowing = false;
    @ConfigEntry.Gui.Tooltip
    public int spawnMultiplier = 1;
    @ConfigEntry.Gui.Tooltip
    public double reinforcementChance = 0.0D;
    @ConfigEntry.Gui.Tooltip
    public double lootMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public boolean enablePoison = true;
    @ConfigEntry.Gui.Tooltip
    public double poisonDurationMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public double poisonAmplifierMultiplier = 1.0D;
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