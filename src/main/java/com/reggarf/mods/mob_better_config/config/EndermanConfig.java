package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "configurable_enderman")
public class EndermanConfig implements ConfigData {

        @ConfigEntry.Gui.Tooltip
        public boolean CustomName = true;
        @ConfigEntry.Gui.Tooltip
        public double health = 40.0D;
        @ConfigEntry.Gui.Tooltip
        public float armor= 2.0F;
        @ConfigEntry.Gui.Tooltip
        public double knockbackResistance = 0.6;
        @ConfigEntry.Gui.Tooltip
        public double attackKnockback = 0.0;// Vanilla: 40
        @ConfigEntry.Gui.Tooltip
        public double attackDamage = 7.0D;       // Vanilla: 7
        @ConfigEntry.Gui.Tooltip
        public double movementSpeed = 0.3D;      // Vanilla: 0.3
        @ConfigEntry.Gui.Tooltip
        public double followRange = 64.0D;       // Vanilla: 64
        @ConfigEntry.Gui.Tooltip
        public boolean canBreakDoors = false;    // Vanilla: false
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 5)  // FIXED: allow 0
        public int doorBreakMode = 0;            // Vanilla: 0 (disabled)
        @ConfigEntry.Gui.Tooltip
        public boolean enableTeleport = true;    // Vanilla: true
        @ConfigEntry.Gui.Tooltip
        public double teleportChance = 1.0D;     // Vanilla behavior multiplier
        @ConfigEntry.Gui.Tooltip
        public double teleportRangeMultiplier = 1.0D; // Vanilla range
        @ConfigEntry.Gui.Tooltip
        public boolean alwaysAggressive = false; // Vanilla: false
        @ConfigEntry.Gui.Tooltip
        public double rageSpeedMultiplier = 1.0D; // Vanilla has no rage multiplier
        @ConfigEntry.Gui.Tooltip
        public boolean takeWaterDamage = true;   // Vanilla: TRUE (isSensitiveToWater = true)
        @ConfigEntry.Gui.Tooltip
        public boolean canPickupBlocks = true;   // Vanilla: TRUE
        @ConfigEntry.Gui.Tooltip
        public int spawnMultiplier = 1;          // Vanilla: 1
        @ConfigEntry.Gui.Tooltip
        public double reinforcementChance = 0.0D; // Vanilla: no reinforcement
        @ConfigEntry.Gui.Tooltip
        public double xpMultiplier = 1.0D;       // Vanilla: normal XP
        @ConfigEntry.Gui.Tooltip
        public double lootMultiplier = 1.0D;     // Vanilla: normal loot
        @ConfigEntry.Gui.Tooltip
        public boolean fireImmune = false;       // Vanilla: false
        @ConfigEntry.Gui.Tooltip
        public boolean burnInDaylight = false;    // Vanilla: false
        @ConfigEntry.Gui.Tooltip
        public int burnSeconds = 8;
        @ConfigEntry.Gui.Tooltip
        public boolean glowing = false;          // Vanilla: false
        @ConfigEntry.Gui.Tooltip
        public boolean bossMode = false;         // Vanilla: false
        @ConfigEntry.Gui.Tooltip
        public boolean forceAllBoss = false;     // Vanilla: false
        @ConfigEntry.Gui.Tooltip
        public double bossChance = 0.05D;        // Custom system default
        @ConfigEntry.Gui.Tooltip
        public double bossHealthMultiplier = 3.0D;
        @ConfigEntry.Gui.Tooltip
        public double bossDamageMultiplier = 2.0D;
        @ConfigEntry.Gui.Tooltip
        public double bossXpMultiplier = 5.0D;
        @ConfigEntry.Gui.Tooltip
        public double bossLootMultiplier = 2.0D;
        @ConfigEntry.Gui.Tooltip
        public boolean bossGlowing = true;
        @ConfigEntry.Gui.Tooltip
        public boolean bossCustomName = true;
    }