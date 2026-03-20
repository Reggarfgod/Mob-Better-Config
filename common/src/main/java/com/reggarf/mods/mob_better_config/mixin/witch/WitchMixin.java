package com.reggarf.mods.mob_better_config.mixin.witch;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Witch.class)
public abstract class WitchMixin {

    @Redirect(
        method = "registerGoals",
        at = @At(
            value = "NEW",
            target = "net/minecraft/world/entity/ai/goal/RangedAttackGoal"
        )
    )
    private RangedAttackGoal mbc$modifyAttackGoal(
            RangedAttackMob mob,
            double speed,
            int interval,
            float range
    ) {

        var config = ModConfigs.getWitch();

        int newInterval = config.fasterPotionThrow
                ? Math.max(1, config.potionThrowInterval)
                : interval;

        return new RangedAttackGoal(
                mob,
                speed,
                newInterval,
                range
        );
    }
}