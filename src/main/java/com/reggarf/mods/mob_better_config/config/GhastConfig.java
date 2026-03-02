package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_ghast")
public class GhastConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean CustomName = true;
    @ConfigEntry.Gui.Tooltip
    public double health = 10.0D; // Vanilla = 10

    @ConfigEntry.Gui.Tooltip
    public double followRange = 100.0D; // Vanilla = 100

    @ConfigEntry.Gui.Tooltip
    public int explosionPower = 1; // Vanilla = 1

    @ConfigEntry.Gui.Tooltip
    public int totalChargeTime = 20; // Vanilla ≈ 20

    @ConfigEntry.Gui.Tooltip
    public double chargeSoundPercent = 0.5D; // 0.5 = halfway through charge

    @ConfigEntry.Gui.Tooltip
    public int cooldownAfterShot = 40; // Vanilla = 40

    @ConfigEntry.Gui.Tooltip
    public double fireballDamageMultiplier = 1.0D;

    @ConfigEntry.Gui.Tooltip
    public double fireballVelocityMultiplier = 1.0D;

    @ConfigEntry.Gui.Tooltip
    public double floatSpeedMultiplier = 1.0D;

    @ConfigEntry.Gui.Tooltip
    public int floatRange = 16; // Vanilla random float distance

    @ConfigEntry.Gui.Tooltip
    public int spawnMultiplier = 1;

    @ConfigEntry.Gui.Tooltip
    public double reinforcementChance = 0.0D;

    @ConfigEntry.Gui.Tooltip
    public double xpMultiplier = 1.0D;

    @ConfigEntry.Gui.Tooltip
    public double lootMultiplier = 1.0D;
    @ConfigEntry.Gui.Tooltip
    public boolean bossMode = false;

    @ConfigEntry.Gui.Tooltip
    public boolean forceAllBoss = false;

    @ConfigEntry.Gui.Tooltip
    public double bossChance = 0.09D;

    @ConfigEntry.Gui.Tooltip
    public double bossHealthMultiplier = 4.0D;

    @ConfigEntry.Gui.Tooltip
    public double bossDamageMultiplier = 3.0D;

    @ConfigEntry.Gui.Tooltip
    public double bossXpMultiplier = 5.0D;
    @ConfigEntry.Gui.Tooltip
    public double bossLootMultiplier = 2.0D;

    @ConfigEntry.Gui.Tooltip
    public boolean bossGlowing = true;

    @ConfigEntry.Gui.Tooltip
    public boolean bossCustomName = true;
}