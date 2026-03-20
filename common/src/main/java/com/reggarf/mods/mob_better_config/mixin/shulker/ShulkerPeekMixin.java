package com.reggarf.mods.mob_better_config.mixin.shulker;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.monster.Shulker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(Shulker.class)
public class ShulkerPeekMixin {

    private static Field GOAL_SELECTOR_FIELD;

    static {
        try {
            GOAL_SELECTOR_FIELD = Mob.class.getDeclaredField("goalSelector");
            GOAL_SELECTOR_FIELD.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void mbc$removeGoals(CallbackInfo ci) {

        Shulker self = (Shulker) (Object) this;

        if (ModConfigs.getShulker().allowPeek)
            return;

        try {
            GoalSelector selector = (GoalSelector) GOAL_SELECTOR_FIELD.get(self);

            selector.getAvailableGoals().removeIf(goal -> {
                String name = goal.getGoal().getClass().getSimpleName();
                return name.equals("ShulkerAttackGoal") ||
                        name.equals("ShulkerPeekGoal");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}