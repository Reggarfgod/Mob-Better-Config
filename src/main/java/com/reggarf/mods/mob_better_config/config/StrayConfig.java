package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_stray")
public class StrayConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;
    public float health = 20.0F;
    @ConfigEntry.Gui.Tooltip
    public double armor = 4.0D;
    @ConfigEntry.Gui.Tooltip
    public double attackKnockback = 0.0;
    @ConfigEntry.Gui.Tooltip
    public double knockbackResistance = 0.6;
    @ConfigEntry.Gui.Tooltip
    public float attackDamage = 2.0F;         // Base melee damage
    @ConfigEntry.Gui.Tooltip
    public double arrowDamageMultiplier = 1.0D;   // 1.0 = normal damage
    @ConfigEntry.Gui.Tooltip
    public double movementSpeed = 0.25D;      // Vanilla skeleton speed
    @ConfigEntry.Gui.Tooltip
    public double followRange = 35.0D;
    @ConfigEntry.Gui.Tooltip
    public boolean enableSlowness = true;
    @ConfigEntry.Gui.Tooltip
    public int slownessDuration = 200;        // Vanilla: 200 ticks (10s)
    @ConfigEntry.Gui.Tooltip
    public int slownessAmplifier = 0;         // Vanilla: 0 (Slowness I)
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
    public boolean burnInDaylight = true;
    @ConfigEntry.Gui.Tooltip
    public boolean randomArmor = false;
    @ConfigEntry.Gui.Tooltip
    public double armorChance = 0.15D;
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