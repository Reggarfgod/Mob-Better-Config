package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class MobConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean enabled = true;
    @ConfigEntry.Gui.Tooltip
    public boolean customName = false;
    @ConfigEntry.Gui.Tooltip
    public double health = 20;
    @ConfigEntry.Gui.Tooltip
    public double armor = 0;
    @ConfigEntry.Gui.Tooltip
    public double armorToughness = 0;
    @ConfigEntry.Gui.Tooltip
    public double attackDamage = 4;
    @ConfigEntry.Gui.Tooltip
    public double attackSpeed = 1;
    @ConfigEntry.Gui.Tooltip
    public double movementSpeed = 0.25;
    @ConfigEntry.Gui.Tooltip
    public double followRange = 32;
    @ConfigEntry.Gui.Tooltip
    public double knockbackResistance = 0;
    @ConfigEntry.Gui.Tooltip
    public double attackKnockback = 0;
    @ConfigEntry.Gui.Tooltip
    public double stepHeight = 0.6;
    @ConfigEntry.Gui.Tooltip
    public double gravity = 0.08;
    @ConfigEntry.Gui.Tooltip
    public boolean glowing = false;
    @ConfigEntry.Gui.Tooltip
    public boolean canBreakDoors = false;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 5)
    public int doorBreakMode = 3;
    @ConfigEntry.Gui.Tooltip
    public boolean burnInDaylight = true;
    @ConfigEntry.Gui.Tooltip
    public boolean fireImmune = false;
    @ConfigEntry.Gui.Tooltip
    public boolean sprintAbility = false;
    @ConfigEntry.Gui.Tooltip
    public boolean rageMode = false;
    @ConfigEntry.Gui.Tooltip
    public boolean jumpBoost = false;
    @ConfigEntry.Gui.Tooltip
    public boolean nightBuff = false;
    @ConfigEntry.Gui.Tooltip
    public int spawnMultiplier = 1;
    @ConfigEntry.Gui.Tooltip
    public double lootMultiplier = 1.0;
    @ConfigEntry.Gui.Tooltip
    public boolean bossMode = false;
    @ConfigEntry.Gui.Tooltip
    public boolean forceAllBoss = false;
    @ConfigEntry.Gui.Tooltip
    public double bossChance = 0.01;
    @ConfigEntry.Gui.Tooltip
    public double bossHealthMultiplier = 3;
    @ConfigEntry.Gui.Tooltip
    public double bossDamageMultiplier = 2;
    @ConfigEntry.Gui.Tooltip
    public double bossXpMultiplier = 5;
    @ConfigEntry.Gui.Tooltip
    public double bossLootMultiplier = 2;
    @ConfigEntry.Gui.Tooltip
    public boolean bossGlowing = false;
    @ConfigEntry.Gui.Tooltip
    public boolean bossCustomName = true;
}