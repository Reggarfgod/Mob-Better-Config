package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_vindicator")
public class VindicatorConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;

    @ConfigEntry.Category("attributes")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 1000)
    public float health = 24.0F;
    @ConfigEntry.Gui.Tooltip
    public double armor = 4.0D;
    @ConfigEntry.Gui.Tooltip
    public double attackKnockback = 0.0;
    @ConfigEntry.Gui.Tooltip
    public double knockbackResistance = 0.6;
    @ConfigEntry.Category("attributes")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
    public float attackDamage = 5.0F;

    @ConfigEntry.Category("attributes")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
    public double movementSpeed = 0.35D;

    @ConfigEntry.Category("attributes")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 128)
    public double followRange = 35.0D;

    @ConfigEntry.Category("special")
    @ConfigEntry.Gui.Tooltip
    public boolean enableJohnnyMode = false;

    @ConfigEntry.Category("spawning")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int spawnMultiplier = 1;

    @ConfigEntry.Category("spawning")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
    public double reinforcementChance = 0.0D;

    @ConfigEntry.Category("loot")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 10)
    public double lootMultiplier = 1.0D;
    @ConfigEntry.Category("behavior")
    @ConfigEntry.Gui.Tooltip
    public boolean fireImmune = false;

    @ConfigEntry.Category("behavior")
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