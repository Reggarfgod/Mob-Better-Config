package com.reggarf.mods.mob_better_config.util;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;

import java.lang.reflect.Field;

public class GoalUtil {

    private static Field GOAL_SELECTOR_FIELD;

    static {
        try {
            GOAL_SELECTOR_FIELD = Mob.class.getDeclaredField("goalSelector");
            GOAL_SELECTOR_FIELD.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private GoalUtil() {}

    public static void addGoal(Mob mob, int priority, Goal goal) {
        try {

            GoalSelector selector =
                    (GoalSelector) GOAL_SELECTOR_FIELD.get(mob);

            selector.addGoal(priority, goal);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}