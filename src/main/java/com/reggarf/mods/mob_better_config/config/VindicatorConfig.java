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


    /* =========================
       Special
       ========================= */

    @ConfigEntry.Category("special")
    @ConfigEntry.Gui.Tooltip
    public boolean enableJohnnyMode = false;


    /* =========================
       Spawning
       ========================= */

    @ConfigEntry.Category("spawning")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int spawnMultiplier = 1;

    @ConfigEntry.Category("spawning")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
    public double reinforcementChance = 0.0D;


    /* =========================
       Loot
       ========================= */

    @ConfigEntry.Category("loot")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 10)
    public double lootMultiplier = 1.0D;


    /* =========================
       Behavior
       ========================= */

    @ConfigEntry.Category("behavior")
    @ConfigEntry.Gui.Tooltip
    public boolean fireImmune = false;

    @ConfigEntry.Category("behavior")
    @ConfigEntry.Gui.Tooltip
    public boolean glowing = false;
}