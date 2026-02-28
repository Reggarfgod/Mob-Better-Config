package com.reggarf.mods.mob_better_config.ai;

import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.level.GameRules;

public class CustomBreakDoorGoal extends BreakDoorGoal {

    private final Zombie zombie;
    private final int breakTime;
    private int progress;

    public CustomBreakDoorGoal(Zombie zombie, int breakTime) {
        super(zombie, difficulty -> true);
        this.zombie = zombie;
        this.breakTime = breakTime;
    }

    @Override
    public void tick() {
        super.tick();
        progress++;

        if (progress >= breakTime) {
            if (zombie.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                zombie.level().destroyBlock(this.doorPos, true, zombie);
            }
            progress = 0;
        }
    }
}