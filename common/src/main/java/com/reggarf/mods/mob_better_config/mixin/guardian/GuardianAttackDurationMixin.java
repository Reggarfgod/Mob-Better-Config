package com.reggarf.mods.mob_better_config.mixin.guardian;

import com.reggarf.mods.mob_better_config.config.GuardianConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import net.minecraft.world.entity.monster.Guardian;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Guardian.class)
public abstract class GuardianAttackDurationMixin {

    @Inject(
            method = "getAttackDuration",
            at = @At("HEAD"),
            cancellable = true
    )
    private void mobBetterConfig$modifyAttackDuration(CallbackInfoReturnable<Integer> cir) {

        GuardianConfig config = ModConfigs.getGuardian();

        if (!config.enableLaser) {
            cir.setReturnValue(0);
            return;
        }

        cir.setReturnValue(config.attackDuration);
    }
}