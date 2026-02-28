package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "configurable_stray")
public class StrayConfig implements ConfigData {


    public float health = 20.0F;              // Vanilla: 20
    public float attackDamage = 2.0F;         // Base melee damage
    public double arrowDamageMultiplier = 1.0D;   // 1.0 = normal damage
    public double movementSpeed = 0.25D;      // Vanilla skeleton speed
    public double followRange = 35.0D;

    public boolean enableSlowness = true;
    public int slownessDuration = 200;        // Vanilla: 200 ticks (10s)
    public int slownessAmplifier = 0;         // Vanilla: 0 (Slowness I)

    public int spawnMultiplier = 1;

    public double reinforcementChance = 0.0D;

    public double lootMultiplier = 1.0D;

    public boolean fireImmune = false;
    public boolean glowing = false;
    public boolean burnInDaylight = true;

    public boolean randomArmor = false;
    public double armorChance = 0.15D;

}