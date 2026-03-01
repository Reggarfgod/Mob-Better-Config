package com.reggarf.mods.mob_better_config.util;

import com.reggarf.mods.mob_better_config.ai.CustomBreakDoorGoal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;

public class DoorBreakUtil {

    private DoorBreakUtil() {}

    /**
     * Applies configurable door breaking behavior.
     * Works for Zombies and any Mob that supports door breaking.
     */
    public static void handleDoorBreaking(
            Mob mob,
            boolean canBreakDoors,
            int doorBreakMode
    ) {

        // Only mobs with door-breaking capability (Zombie-based mobs)
        if (!(mob instanceof Zombie zombie))
            return;

        // Remove existing break door goals (vanilla + custom)
        zombie.goalSelector.removeAllGoals(goal ->
                goal.getClass().getName().contains("BreakDoor"));

        // Disabled
        if (!canBreakDoors || doorBreakMode <= 0) {
            zombie.setCanBreakDoors(false);
            return;
        }

        zombie.setCanBreakDoors(true);

        int breakTicks = getBreakTicks(doorBreakMode);

        zombie.goalSelector.addGoal(
                1,
                new CustomBreakDoorGoal(zombie, breakTicks)
        );
    }

    /**
     * Converts config mode into break duration.
     */
    private static int getBreakTicks(int mode) {
        return switch (mode) {
            case 1 -> 400; // very slow
            case 2 -> 300;
            case 3 -> 240; // vanilla-like
            case 4 -> 120;
            case 5 -> 60;  // very fast
            default -> 240;
        };
    }
}