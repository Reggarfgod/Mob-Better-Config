package com.reggarf.mods.mob_better_config.mixin.enderman;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderMan.class)
public class EndermanWaterDamageMixin {

    @Inject(
            method = "isSensitiveToWater",
            at = @At("HEAD"),
            cancellable = true
    )
    private void mobBetterConfig$waterDamage(CallbackInfoReturnable<Boolean> cir) {

        // config example: takeWaterDamage
        cir.setReturnValue(ModConfigs.getEnderman().takeWaterDamage);
    }
}