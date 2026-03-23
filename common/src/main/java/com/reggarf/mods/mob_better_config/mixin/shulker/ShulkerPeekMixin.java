package com.reggarf.mods.mob_better_config.mixin.shulker;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.mixin.MobAccessor;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.monster.Shulker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Shulker.class)
public class ShulkerPeekMixin {

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void mbc$removeGoals(CallbackInfo ci) {

        Shulker self = (Shulker) (Object) this;

        if (ModConfigs.getShulker().allowPeek)
            return;

        GoalSelector selector =
                ((MobAccessor) self).getGoalSelector();

        selector.getAvailableGoals().removeIf(goal -> {
            String name = goal.getGoal().getClass().getSimpleName();
            return name.equals("ShulkerAttackGoal") ||
                    name.equals("ShulkerPeekGoal");
        });
    }
}