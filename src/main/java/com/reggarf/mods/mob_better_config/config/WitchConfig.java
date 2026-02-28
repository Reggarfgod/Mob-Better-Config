package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "configurable_witch")
public class WitchConfig implements ConfigData {


    // Attributes
    public float health = 26.0F;
    public float attackDamage = 4.0F;
    public double movementSpeed = 0.25D;
    public double followRange = 35.0D;

    // Behavior
    public boolean fireImmune = false;
    public boolean glowing = false;

    // Potion Tweaks
    public boolean fasterPotionThrow = false;
    public double potionDamageMultiplier = 1.0D;

    // Spawn
    public int spawnMultiplier = 1;
    public double randomArmorChance = 0.1D;

    // Reinforcement
    public double reinforcementChance = 0.1D;

    // Loot
    public double lootMultiplier = 1.0D; // 1.0 = normal drops
}