package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_witch")
public class WitchConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;
    public float health = 26.0F;
    @ConfigEntry.Gui.Tooltip
    public double armor = 4.0D;
    @ConfigEntry.Gui.Tooltip
    public double attackKnockback = 0.0;
    @ConfigEntry.Gui.Tooltip
    public double knockbackResistance = 0.6;
    @ConfigEntry.Gui.Tooltip
    public float attackDamage = 4.0F;
    @ConfigEntry.Gui.Tooltip
    public double movementSpeed = 0.25D;
    @ConfigEntry.Gui.Tooltip
    public double followRange = 35.0D;
    @ConfigEntry.Gui.Tooltip
    public boolean fireImmune = false;
    @ConfigEntry.Gui.Tooltip
    public boolean glowing = false;
    @ConfigEntry.Gui.Tooltip
    public boolean fasterPotionThrow = false;
    @ConfigEntry.Gui.Tooltip
    public double potionDamageMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public int spawnMultiplier = 1;
    @ConfigEntry.Gui.Tooltip
    public double reinforcementChance = 0.1D;
    @ConfigEntry.Gui.Tooltip
    public double lootMultiplier = 1.0D;
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
    public boolean bossGlowing = false;

    @ConfigEntry.Gui.Tooltip
    public boolean bossCustomName = true;
}