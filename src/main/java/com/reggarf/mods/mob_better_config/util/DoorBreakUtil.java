package com.reggarf.mods.mob_better_config.util;

import com.reggarf.mods.mob_better_config.ai.CustomBreakDoorGoal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;

public class DoorBreakUtil {

    private DoorBreakUtil() {}

    /**
     * Applies configurable door breaking behavior.
     */
    public static void handleDoorBreaking(
            Mob mob,
            boolean canBreakDoors,
            int doorBreakMode
    ) {

        // Only zombies support door breaking
        if (!(mob instanceof Zombie zombie))
            return;

        // Remove any existing door break goals
        zombie.goalSelector.removeAllGoals(goal ->
                goal.getClass().getName().contains("BreakDoor"));

        // Disable door breaking
        if (!canBreakDoors || doorBreakMode == 0) {
            zombie.setCanBreakDoors(false);
            return;
        }

        zombie.setCanBreakDoors(true);

        int breakTicks;

        switch (doorBreakMode) {
            case 1 -> breakTicks = 400;
            case 2 -> breakTicks = 300;
            case 3 -> breakTicks = 240;
            case 4 -> breakTicks = 120;
            case 5 -> breakTicks = 60;
            default -> breakTicks = 240;
        }

        zombie.goalSelector.addGoal(
                1,
                new CustomBreakDoorGoal(zombie, breakTicks)
        );
    }
}