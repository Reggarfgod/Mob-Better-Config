package com.reggarf.mods.mob_better_config.util;

import com.reggarf.mods.mob_better_config.ai.CustomBreakDoorGoal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;

import java.lang.reflect.Field;

public class DoorBreakUtil {

    private DoorBreakUtil() {}

    /**
     * Applies configurable door breaking behavior.
     * Works for any mob using GroundPathNavigation.
     */
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

        try {

            Field goalSelectorField = Mob.class.getDeclaredField("goalSelector");
            goalSelectorField.setAccessible(true);

            GoalSelector goalSelector = (GoalSelector) goalSelectorField.get(mob);

            goalSelector.addGoal(
                    1,
                    new CustomBreakDoorGoal(mob, breakTicks)
            );

        } catch (Exception ignored) {}

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