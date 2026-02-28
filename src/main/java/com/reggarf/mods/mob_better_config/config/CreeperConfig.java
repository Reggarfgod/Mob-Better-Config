package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "configurable_creeper")
public class CreeperConfig implements ConfigData {

    // Attributes
    public float health = 20.0F;
    public double movementSpeed = 0.25D;

    // Explosion
    public int explosionRadius = 3;
    public double explosionDamageMultiplier = 1.0D;
    public int fuseTime = 30; // default 30 ticks
    public boolean powered = false;

    // Behavior
    public boolean fireImmune = false;
    public boolean glowing = false;

    // Spawn
    public int spawnMultiplier = 1;

    // Reinforcement
    public double reinforcementChance = 0.0D;

    // Loot
    public double lootMultiplier = 1.0D;
}