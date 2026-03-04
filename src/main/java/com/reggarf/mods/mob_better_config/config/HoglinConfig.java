package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_hoglin")
public class HoglinConfig {

    // ===============================
    // General
    // ===============================

    @ConfigEntry.Gui.Tooltip
    public boolean customName = true;

    @ConfigEntry.Gui.Tooltip
    public boolean glowing = false;

    @ConfigEntry.Gui.Tooltip
    public boolean fireImmune = false;

    @ConfigEntry.Gui.Tooltip
    public int spawnMultiplier = 1;

    // Stats (Vanilla: 40 HP, 6 Damage)
    @ConfigEntry.Gui.Tooltip
    public double health = 40.0;
    @ConfigEntry.Gui.Tooltip
    public double armor = 1.0;

    @ConfigEntry.Gui.Tooltip
    public double attackDamage = 6.0;

    @ConfigEntry.Gui.Tooltip
    public double movementSpeed = 0.3;

    @ConfigEntry.Gui.Tooltip
    public double knockbackResistance = 0.6;

    @ConfigEntry.Gui.Tooltip
    public double attackKnockback = 1.0;

    @ConfigEntry.Gui.Tooltip
    public double attackDamageMultiplier = 1.0;

    // ===============================
    // Hoglin Special Mechanics
    // ===============================

    @ConfigEntry.Gui.Tooltip
    public boolean disableZombification = false;

    @ConfigEntry.Gui.Tooltip
    public double convertToZoglinChance = 0.0;

    // ===============================
    // Reinforcements
    // ===============================

    @ConfigEntry.Gui.Tooltip
    public double reinforcementChance = 0.0;

    // ===============================
    // Loot / XP
    // ===============================

    @ConfigEntry.Gui.Tooltip
    public double xpMultiplier = 1.0;

    @ConfigEntry.Gui.Tooltip
    public double lootMultiplier = 1.0;

    // ===============================
    // Boss System
    // ===============================

    @ConfigEntry.Gui.Tooltip
    public boolean bossMode = false;

    @ConfigEntry.Gui.Tooltip
    public boolean forceAllBoss = false;

    @ConfigEntry.Gui.Tooltip
    public double bossChance = 0.05;

    @ConfigEntry.Gui.Tooltip
    public double bossHealthMultiplier = 3.0;

    @ConfigEntry.Gui.Tooltip
    public double bossDamageMultiplier = 2.0;

    @ConfigEntry.Gui.Tooltip
    public boolean bossGlowing = true;

    @ConfigEntry.Gui.Tooltip
    public boolean bossCustomName = true;

    @ConfigEntry.Gui.Tooltip
    public double bossXpMultiplier = 3.0;

    @ConfigEntry.Gui.Tooltip
    public double bossLootMultiplier = 2.0;

}