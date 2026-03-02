package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_evoker")
public class EvokerConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;
    @ConfigEntry.Gui.Tooltip
    public double health = 24.0D;
    @ConfigEntry.Gui.Tooltip
    public double movementSpeed = 0.5D;
    @ConfigEntry.Gui.Tooltip
    public double followRange = 12.0D;
    @ConfigEntry.Gui.Tooltip
    public boolean enableFangsSpell = true;
    @ConfigEntry.Gui.Tooltip
    public double fangsDamageMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public boolean enableVexSummon = true;
    @ConfigEntry.Gui.Tooltip
    public int summonVexCount = 3; // Vanilla: 3
    @ConfigEntry.Gui.Tooltip
    public int vexLifeTicks = 20 * 60;
    @ConfigEntry.Gui.Tooltip
    public boolean allowWololo = true;
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