package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "wither")
public class WitherConfig {

    @ConfigEntry.Gui.Tooltip
    public boolean customName = true;

    @ConfigEntry.Gui.Tooltip
    public boolean glowing = false;

    @ConfigEntry.Gui.Tooltip
    public double health = 300.0D;

    @ConfigEntry.Gui.Tooltip
    public double movementSpeed = 0.6D;

    @ConfigEntry.Gui.Tooltip
    public double flyingSpeed = 0.6D;

    @ConfigEntry.Gui.Tooltip
    public double followRange = 40.0D;

    @ConfigEntry.Gui.Tooltip
    public double armor = 4.0D;

    @ConfigEntry.Gui.Tooltip
    public boolean enableBlockBreaking = true;

    @ConfigEntry.Gui.Tooltip
    public boolean enableRegeneration = true;

    @ConfigEntry.Gui.Tooltip
    public double regenerationAmount = 1.0D;

    @ConfigEntry.Gui.Tooltip
    public int regenerationInterval = 20;

    @ConfigEntry.Gui.Tooltip
    public int invulnerableTicks = 220;

    @ConfigEntry.Gui.Tooltip
    public float spawnExplosionPower = 7.0F;

    @ConfigEntry.Gui.Tooltip
    public double skullDamageMultiplier = 1.0D;

    @ConfigEntry.Gui.Tooltip
    public double xpMultiplier = 1.0D;

    @ConfigEntry.Gui.Tooltip
    public double lootMultiplier = 1.0D;

    // =========================
    // Boss Mode
    // =========================

    public boolean bossMode = false;
    public boolean forceAllBoss = false;
    public double bossChance = 0.2D;
    public double bossHealthMultiplier = 2.0D;
    public double bossDamageMultiplier = 2.0D;
    public boolean bossGlowing = true;
    public boolean bossCustomName = true;
    public double bossXpMultiplier = 2.0D;
    public double bossLootMultiplier = 2.0D;
}