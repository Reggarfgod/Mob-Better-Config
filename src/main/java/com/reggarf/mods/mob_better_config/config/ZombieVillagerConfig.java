package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "configurable_zombie_villager")
public class ZombieVillagerConfig implements ConfigData {


    // Attributes
    public float health = 20.0F;
    public float attackDamage = 5.0F;
    public double movementSpeed = 0.23D;
    public double followRange = 35.0D;

    // Behavior
    public boolean burnInDaylight = true;
    public boolean fireImmune = false;
    public boolean glowing = false;


    // Spawn
    public int spawnMultiplier = 1;
    public double randomArmorChance = 0.15D;


    // Reinforcement
    public double reinforcementChance = 0.01D;
    public double cureSpeedMultiplier = 1.0D;

    public double lootMultiplier = 1.0D;
}