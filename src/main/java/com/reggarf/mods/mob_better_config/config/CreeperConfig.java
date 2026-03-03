package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_creeper")
public class CreeperConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;
    public float health = 20.0F;
    public double movementSpeed = 0.25D;
    public int explosionRadius = 3;
    public double explosionDamageMultiplier = 1.0D;
    public int fuseTime = 30; // default 30 ticks
    public boolean powered = false;
    public boolean fireImmune = false;
    public boolean glowing = false;
    public int spawnMultiplier = 1;
    public double reinforcementChance = 0.0D;
    public double lootMultiplier = 1.0D;
}