package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_phantom")
public class PhantomConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;

    public double health = 20.0D;          // Vanilla: 20
    public double attackDamage = 6.0D;     // Vanilla: 6
    public double movementSpeed = 1.0D;
    public double circleSpeedMultiplier = 1.0D;
    public double swoopSpeedMultiplier = 1.5D; // Vanilla approx
    public double followRange = 64.0D;     // Vanilla: 64

    @ConfigEntry.Gui.Tooltip
    public boolean canBreakDoors = false;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 5)
    public int doorBreakMode = 3;

    public int fixedSize = -1;             // -1 = vanilla scaling
    public double sizeMultiplier = 1.0D;

    public int spawnMultiplier = 1;


    public double xpMultiplier = 1.0D;
    public double lootMultiplier = 1.0D;

    public double reinforcementChance = 0.0D;

    public boolean fireImmune = false;
    public boolean glowing = false;
    public boolean burnInDaylight = true; // Vanilla: true


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