package com.reggarf.mods.mob_better_config.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.level.GameRules;

public class CustomBreakDoorGoal extends BreakDoorGoal {

    private final Mob mob;
    private final int breakTime;
    private int progress;

    public CustomBreakDoorGoal(Mob mob, int breakTime) {
        super(mob, difficulty -> true);
        this.mob = mob;
        this.breakTime = breakTime;
    }

    @Override
    public void start() {
        super.start();
        this.progress = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.progress = 0;
    }

    @Override
    public void tick() {
        super.tick();

        progress++;

        if (progress >= breakTime) {

            if (mob.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {

                mob.level().destroyBlock(this.doorPos, true, mob);

            }

            progress = 0;
        }
    }
}