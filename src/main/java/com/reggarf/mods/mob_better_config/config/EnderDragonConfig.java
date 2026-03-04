package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "ender_dragon")
public class EnderDragonConfig {

    @ConfigEntry.Gui.Tooltip
    public boolean customName = true;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean glowing = false;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public double health = 200.0D;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public double flyingSpeed = 1.0D;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public double armor = 10.0D;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public double attackDamage = 10.0D;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean enableDragonBreath = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public double dragonBreathDamageMultiplier = 1.0D;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean enableCrystalHealing = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public double crystalHealAmount = 1.0D;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public int crystalHealInterval = 10;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean enableBlockBreaking = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public double knockbackDamage = 5.0D;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public double headDamage = 10.0D;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public double xpMultiplier = 1.0D;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public double lootMultiplier = 1.0D;

// Vex Reinforcements
@ConfigEntry.Gui.RequiresRestart
    public boolean enableVexReinforcements = true;
    @ConfigEntry.Gui.RequiresRestart
    public boolean vexRequireBelowHalfHealth = true;
    @ConfigEntry.Gui.RequiresRestart
    public double vexSpawnChance = 0.01D;
    @ConfigEntry.Gui.RequiresRestart
    public int vexCount = 6;
    @ConfigEntry.Gui.RequiresRestart
    public int vexSpawnRadius = 6;
    @ConfigEntry.Gui.RequiresRestart
    public int vexLifeTimeSeconds = 20;
    @ConfigEntry.Gui.RequiresRestart
    public int vexMaxNearby = 10;
    @ConfigEntry.Gui.RequiresRestart
    public double vexHealth = 20.0D;
    public double vexSpeed = 0.35D;
    @ConfigEntry.Gui.RequiresRestart
    public double vexAttackDamage = 4.0D;
    @ConfigEntry.Gui.RequiresRestart
    public double vexTargetPlayerDistance = 32.0D;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean bossMode = true;

    @ConfigEntry.Gui.Tooltip
    public boolean forceAllBoss = true;

    @ConfigEntry.Gui.Tooltip
    public double bossChance = 0.2D;

    @ConfigEntry.Gui.Tooltip
    public double bossHealthMultiplier = 2.0D;

    @ConfigEntry.Gui.Tooltip
    public double bossDamageMultiplier = 2.0D;

    @ConfigEntry.Gui.Tooltip
    public boolean bossGlowing = true;

    @ConfigEntry.Gui.Tooltip
    public boolean bossCustomName = true;

    @ConfigEntry.Gui.Tooltip
    public double bossXpMultiplier = 2.0D;

    @ConfigEntry.Gui.Tooltip
    public double bossLootMultiplier = 2.0D;
}