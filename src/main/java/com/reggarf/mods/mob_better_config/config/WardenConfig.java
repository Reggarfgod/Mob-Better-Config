package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "configurable_warden")
public class WardenConfig implements ConfigData {


    // Core Attributes (Vanilla Based)
    public float health = 500.0F;                  // Vanilla: 500
    public double movementSpeed = 0.3D;            // Vanilla: 0.3
    public double knockbackResistance = 1.0D;      // Vanilla: 1.0
    public double attackKnockback = 1.5D;          // Vanilla: 1.5
    public float attackDamage = 30.0F;             // Vanilla: 30
    public double followRange = 50.0D;


    // Sonic Boom
    public double sonicBoomDamageMultiplier = 1.0D;
    public int sonicBoomCooldown = 200;            // Vanilla: 200 ticks
    public int sonicBoomInitialDelay = 40;         // Vanilla: 40 ticks after melee

    // Darkness Ability
    public boolean enableDarkness = true;
    public int darknessRadius = 20;                // Vanilla: 20
    public int darknessDuration = 260;             // Vanilla: 260 ticks
    public int darknessInterval = 120;             // Vanilla: every 120 ticks


    // Anger System
    public int defaultAnger = 35;                  // Vanilla default anger
    public int projectileAnger = 10;               // Anger from projectile
    public int onHurtAngerBoost = 20;              // Extra anger on hurt

    // Spawn
    public int spawnMultiplier = 1;


    // Reinforcement
    public double reinforcementChance = 0.0D;
    // Loot
    public double lootMultiplier = 1.0D;
    // Behavior
    public boolean fireImmune = true;
    public boolean glowing = false;
}