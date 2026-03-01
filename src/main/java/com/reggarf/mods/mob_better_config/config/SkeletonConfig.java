package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_skeleton")
public class SkeletonConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;

    // Attributes
    public float health = 20.0F;
    public float attackDamage = 4.0F;
    public double movementSpeed = 0.25D;
    public double followRange = 35.0D;
    public double knockbackResistance = 0.2D;
    public double reinforcementChance = 0.2D;
    // Behavior
    public boolean fireImmune = false;
    public boolean burnInDaylight = true;
    public boolean glowing = false;

    // Combat
    public double bowPowerMultiplier = 1.0D;
    public boolean rapidFire = false;

    // Spawn
    public int spawnMultiplier = 1;
    public double randomArmorChance = 0.2D;

    public double lootMultiplier = 1.0D;
}