package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_drowned")
public class DrownedConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;
    @ConfigEntry.Gui.Tooltip
    public double health = 20.0D;
    @ConfigEntry.Gui.Tooltip
    public double randomArmorChance = 0.2D;// Vanilla: 0.20
    @ConfigEntry.Gui.Tooltip
    public float armor= 2.0F;
    @ConfigEntry.Gui.Tooltip
    public float attackDamage = 2.0F;
    @ConfigEntry.Gui.Tooltip
    public double knockbackResistance = 0.6;
    @ConfigEntry.Gui.Tooltip
    public double attackKnockback = 0.0;
    @ConfigEntry.Gui.Tooltip
    public double movementSpeed = 0.23D;
    @ConfigEntry.Gui.Tooltip
    public double followRange = 35.0D;
    @ConfigEntry.Gui.Tooltip
    public boolean canBreakDoors = false;    // Vanilla: false
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 5)  // FIXED: allow 0
    public int doorBreakMode = 2;            // Vanilla: 0 (disabled)

    @ConfigEntry.Gui.Tooltip
    public boolean fireImmune = false;       // Vanilla: false
    @ConfigEntry.Gui.Tooltip
    public boolean burnInDaylight = false;    // Vanilla: false

    @ConfigEntry.Gui.Tooltip
    public int burnSeconds = 8;


    @ConfigEntry.Gui.Tooltip
    public double tridentChance = 0.10D; // Vanilla ~10% of 0.9 roll

    @ConfigEntry.Gui.Tooltip
    public double fishingRodChance = 0.06D;

    @ConfigEntry.Gui.Tooltip
    public double nautilusShellChance = 0.03D;

    @ConfigEntry.Gui.Tooltip
    public boolean requireDeepWater = true;

    @ConfigEntry.Gui.Tooltip
    public int deepWaterOffset = 5; // seaLevel - 5

    @ConfigEntry.Gui.Tooltip
    public boolean allowDaySpawn = false;

    @ConfigEntry.Gui.Tooltip
    public double tridentVelocity = 1.6D;

    @ConfigEntry.Gui.Tooltip
    public int tridentInaccuracy = 14;

    @ConfigEntry.Gui.Tooltip
    public double tridentDamageMultiplier = 1.0D;

    @ConfigEntry.Gui.Tooltip
    public boolean aggressiveSwimming = true;

    @ConfigEntry.Gui.Tooltip
    public double swimUpBoost = 0.002D;


    @ConfigEntry.Gui.Tooltip
    public int spawnMultiplier = 1;
    @ConfigEntry.Gui.Tooltip
    public double reinforcementChance = 0.0D;

    @ConfigEntry.Gui.Tooltip
    public double xpMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public double lootMultiplier = 1.0D;

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