package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "configurable_spider")
public class SpiderConfig implements ConfigData {

    // Attributes
    public float health = 16.0F;
    public float attackDamage = 2.0F;
    public double movementSpeed = 0.3D;
    public double followRange = 30.0D;

    // Behavior
    public boolean fireImmune = false;
    public boolean glowing = false;

    // Spawn
    public int spawnMultiplier = 1;
    public double randomArmorChance = 0.0D;

    // Reinforcement
    public double reinforcementChance = 0.0D;

    // Loot
    public double lootMultiplier = 1.0D;
}