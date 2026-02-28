package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "configurable_cave_spider")
public class CaveSpiderConfig implements ConfigData {

    public float health = 12.0F;
    public float attackDamage = 2.0F;
    public double movementSpeed = 0.35D;
    public double followRange = 25.0D;

    public boolean fireImmune = false;
    public boolean glowing = false;

    public int spawnMultiplier = 1;
    public double reinforcementChance = 0.0D;

    public double lootMultiplier = 1.0D;
    // Poison Control
    public boolean enablePoison = true;
    public double poisonDurationMultiplier = 1.0D;
    public double poisonAmplifierMultiplier = 1.0D;
}