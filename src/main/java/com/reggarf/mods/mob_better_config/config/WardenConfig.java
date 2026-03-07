package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_warden")
public class WardenConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;
    @ConfigEntry.Gui.Tooltip
    public float health = 500.0F;
    @ConfigEntry.Gui.Tooltip
    public double armor = 4.0D;
    @ConfigEntry.Gui.Tooltip
    public double movementSpeed = 0.3D;
    @ConfigEntry.Gui.Tooltip
    public double knockbackResistance = 1.0D;      // Vanilla: 1.0
    @ConfigEntry.Gui.Tooltip
    public double attackKnockback = 1.5D;          // Vanilla: 1.5
    @ConfigEntry.Gui.Tooltip
    public float attackDamage = 30.0F;             // Vanilla: 30
    @ConfigEntry.Gui.Tooltip
    public double followRange = 50.0D;

    @ConfigEntry.Gui.Tooltip
    public double sonicBoomDamageMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public int sonicBoomCooldown = 200;            // Vanilla: 200 ticks
    @ConfigEntry.Gui.Tooltip
    public int sonicBoomInitialDelay = 40;         // Vanilla: 40 ticks after melee

    // Darkness Ability
    @ConfigEntry.Gui.Tooltip
    public boolean enableDarkness = true;
    @ConfigEntry.Gui.Tooltip
    public int darknessRadius = 20;                // Vanilla: 20
    @ConfigEntry.Gui.Tooltip
    public int darknessDuration = 260;             // Vanilla: 260 ticks
    @ConfigEntry.Gui.Tooltip
    public int darknessInterval = 120;             // Vanilla: every 120 ticks


    // Anger System
    @ConfigEntry.Gui.Tooltip
    public int defaultAnger = 35;                  // Vanilla default anger
    @ConfigEntry.Gui.Tooltip
    public int projectileAnger = 10;               // Anger from projectile
    @ConfigEntry.Gui.Tooltip
    public int onHurtAngerBoost = 20;              // Extra anger on hurt

    // Spawn
    @ConfigEntry.Gui.Tooltip
    public int spawnMultiplier = 1;


    // Loot
    @ConfigEntry.Gui.Tooltip
    public double lootMultiplier = 1.0D;
    // Behavior
    @ConfigEntry.Gui.Tooltip
    public boolean fireImmune = false;
    @ConfigEntry.Gui.Tooltip
    public boolean glowing = false;

    @ConfigEntry.Gui.Tooltip
    public boolean bossMode = true;

    @ConfigEntry.Gui.Tooltip
    public boolean forceAllBoss = false;

    @ConfigEntry.Gui.Tooltip
    public double bossChance = 0.01D;

    @ConfigEntry.Gui.Tooltip
    public double bossHealthMultiplier = 3.0D;

    @ConfigEntry.Gui.Tooltip
    public double bossDamageMultiplier = 2.0D;

    @ConfigEntry.Gui.Tooltip
    public double bossXpMultiplier = 5.0D;
    @ConfigEntry.Gui.Tooltip
    public double bossLootMultiplier = 2.0D;

    @ConfigEntry.Gui.Tooltip
    public boolean bossGlowing = false;

    @ConfigEntry.Gui.Tooltip
    public boolean bossCustomName = true;
}