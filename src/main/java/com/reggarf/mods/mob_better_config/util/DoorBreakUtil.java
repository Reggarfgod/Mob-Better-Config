package com.reggarf.mods.mob_better_config.util;

import com.reggarf.mods.mob_better_config.ai.CustomBreakDoorGoal;
import com.reggarf.mods.mob_better_config.config.ZombieConfig;

import net.minecraft.world.entity.monster.Zombie;

public class DoorBreakUtil {

    /**
     * Applies door breaking behavior based on config.
     */
    public static void applyDoorBreakMode(Zombie zombie, ZombieConfig config) {

        // Remove existing BreakDoor goals
        zombie.goalSelector.removeAllGoals(goal ->
                goal.getClass().getName().contains("BreakDoor"));

        // Disabled
        if (!config.canBreakDoors || config.doorBreakMode == 0) {
            zombie.setCanBreakDoors(false);
            return;
        }

        zombie.setCanBreakDoors(true);

        int breakTicks = getBreakTicks(config.doorBreakMode);

        zombie.goalSelector.addGoal(
                1,
                new CustomBreakDoorGoal(zombie, breakTicks)
        );
    }

    /**
     * Converts mode into break speed.
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