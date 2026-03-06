package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_pillager")
public class PillagerConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;
    @ConfigEntry.Gui.Tooltip
    public double health = 24.0D;

    @ConfigEntry.Gui.Tooltip
    public double armor = 4.0D;
    @ConfigEntry.Gui.Tooltip
    public double attackDamage = 5.0D;     // Vanilla: 5
    @ConfigEntry.Gui.Tooltip
    public double knockbackResistance = 0.6;
    @ConfigEntry.Gui.Tooltip
    public double attackKnockback = 0.0;// Vanilla: 20
    @ConfigEntry.Gui.Tooltip
    public double movementSpeed = 0.35D;   // Vanilla: 0.35
    @ConfigEntry.Gui.Tooltip
    public double followRange = 32.0D;     // Vanilla: 32

    @ConfigEntry.Gui.Tooltip
    public double crossbowDamageMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public boolean strongerInRaid = true;
    @ConfigEntry.Gui.Tooltip
    public double raidDamageBonus = 1.3D;
    @ConfigEntry.Gui.Tooltip
    public double raidHealthBonus = 1.3D;
    @ConfigEntry.Gui.Tooltip
    public int spawnMultiplier = 1;
    @ConfigEntry.Gui.Tooltip
    public double xpMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public double lootMultiplier = 1.0D;
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