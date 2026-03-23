package com.reggarf.mods.mob_better_config.util;

import com.reggarf.mods.mob_better_config.ai.CustomBreakDoorGoal;
import com.reggarf.mods.mob_better_config.mixin.MobAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;

public class DoorBreakUtil {

    private DoorBreakUtil() {}

    public static void handleDoorBreaking(
            Mob mob,
            boolean canBreakDoors,
            int doorBreakMode
    ) {

        if (!(mob.getNavigation() instanceof GroundPathNavigation navigation))
            return;

        mob.removeAllGoals(goal ->
                goal.getClass().getName().contains("BreakDoor")
        );

        if (!canBreakDoors || doorBreakMode <= 0) {
            navigation.setCanOpenDoors(false);
            return;
        }

        navigation.setCanOpenDoors(true);

        int breakTicks = getBreakTicks(doorBreakMode);

        GoalSelector goalSelector =
                ((MobAccessor) mob).getGoalSelector();

        goalSelector.addGoal(
                1,
                new CustomBreakDoorGoal(mob, breakTicks)
        );
    }

    private static int getBreakTicks(int mode) {
        return switch (mode) {
            case 1 -> 400;
            case 2 -> 300;
            case 3 -> 240;
            case 4 -> 120;
            case 5 -> 60;
            default -> 240;
        };
    }
}