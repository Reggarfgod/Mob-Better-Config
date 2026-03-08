package com.reggarf.mods.mob_better_config.ai;

import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;

public class CustomBreakDoorGoal extends BreakDoorGoal {

    private final int customBreakTime;

    public CustomBreakDoorGoal(Zombie zombie, int breakTime) {
        super(zombie, difficulty -> true);
        this.customBreakTime = breakTime;
    }

    @Override
    protected int getDoorBreakTime() {
        return customBreakTime;
    }
}