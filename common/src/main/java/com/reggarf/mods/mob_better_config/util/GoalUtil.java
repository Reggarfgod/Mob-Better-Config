package com.reggarf.mods.mob_better_config.util;

import com.reggarf.mods.mob_better_config.mixin.MobAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;

public class GoalUtil {

    private GoalUtil() {}

    public static void addGoal(Mob mob, int priority, Goal goal) {

        GoalSelector selector =
                ((MobAccessor) mob).getGoalSelector();

        selector.addGoal(priority, goal);
    }
}