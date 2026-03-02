package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_ravager")
public class RavagerConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;
    @ConfigEntry.Gui.Tooltip
    public double health = 100.0D;
    @ConfigEntry.Gui.Tooltip
    public double attackDamage = 12.0D;
    @ConfigEntry.Gui.Tooltip
    public double movementSpeed = 0.3D;
    @ConfigEntry.Gui.Tooltip
    public double followRange = 32.0D;
    @ConfigEntry.Gui.Tooltip
    public double knockbackResistance = 0.75D;
    @ConfigEntry.Gui.Tooltip
    public boolean enableRoar = true;
    @ConfigEntry.Gui.Tooltip
    public double roarDamageMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public boolean strongerInRaid = true;
    @ConfigEntry.Gui.Tooltip
    public double raidHealthBonus = 1.3D;
    @ConfigEntry.Gui.Tooltip
    public double raidDamageBonus = 1.3D;
    @ConfigEntry.Gui.Tooltip
    public int spawnMultiplier = 1;
    @ConfigEntry.Gui.Tooltip
    public double xpMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public double lootMultiplier = 1.0D;

    // ===============================
    // Reinforcement
    // ===============================
    @ConfigEntry.Gui.Tooltip
    public double reinforcementChance = 0.0D;

    @ConfigEntry.Gui.Tooltip
    public boolean fireImmune = false;
    @ConfigEntry.Gui.Tooltip
    public boolean glowing = false;

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