package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_phantom")
public class PhantomConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;
    public double health = 20.0D;
    @ConfigEntry.Gui.Tooltip
    public float armor= 2.0F;
    @ConfigEntry.Gui.Tooltip
    public double knockbackResistance = 0.6;
    @ConfigEntry.Gui.Tooltip
    public double attackKnockback = 0.0;// Vanilla: 20
    @ConfigEntry.Gui.Tooltip
    public double attackDamage = 6.0D;     // Vanilla: 6
    @ConfigEntry.Gui.Tooltip
    public double movementSpeed = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public double circleSpeedMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public double swoopSpeedMultiplier = 1.5D; // Vanilla approx
    @ConfigEntry.Gui.Tooltip
    public double followRange = 64.0D;     // Vanilla: 64
    @ConfigEntry.Gui.Tooltip
    public boolean canBreakDoors = false;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 5)
    public int doorBreakMode = 3;
    @ConfigEntry.Gui.Tooltip
    public int fixedSize = -1;             // -1 = vanilla scaling
    @ConfigEntry.Gui.Tooltip
    public double sizeMultiplier = 1.0D;
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
    public boolean glowing = false;
    @ConfigEntry.Gui.Tooltip
    public boolean burnInDaylight = true; // Vanilla: true
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