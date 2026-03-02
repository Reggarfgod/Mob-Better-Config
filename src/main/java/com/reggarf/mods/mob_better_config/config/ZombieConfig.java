package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_zombie")
public class ZombieConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;
    public float health = 40.0F;
    public float attackDamage = 6.0F;
    public double movementSpeed = 0.25D;
    public double followRange = 35.0D;
    public double knockbackResistance = 0.2D;
    public double reinforcementChance = 0.2D;
    public boolean canBreakDoors = true;
    @ConfigEntry.BoundedDiscrete(min = 1, max = 5)
    @ConfigEntry.Gui.Tooltip
    public int doorBreakMode = 3;
    public boolean burnInDaylight = true;
    public boolean fireImmune = false;
    public boolean glowing = false;
    public boolean sprintAbility = true;
    public boolean rageMode = true;
    public int spawnMultiplier = 1;
    public double babyChance = 0.1D;
    public boolean randomArmor = true;
    public double armorChance = 0.3D;
    public double lootMultiplier = 1.0D;
}