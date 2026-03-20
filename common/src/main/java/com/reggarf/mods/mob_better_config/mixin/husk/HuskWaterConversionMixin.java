package com.reggarf.mods.mob_better_config.mixin.husk;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import net.minecraft.world.entity.monster.Husk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Husk.class)
public abstract class HuskWaterConversionMixin {

    @Unique
    private int mbc_waterTimer = 0;

    /**
     * Enable / disable water conversion
     */
    @Inject(method = "convertsInWater", at = @At("HEAD"), cancellable = true)
    private void mobBetterConfig$toggleConversion(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(ModConfigs.getHusk().convertInWater);
    }

    /**
     * Custom conversion timer
     */
    @Inject(method = "doUnderWaterConversion", at = @At("HEAD"), cancellable = true)
    private void mobBetterConfig$delayConversion(CallbackInfo ci) {

        int requiredTime = ModConfigs.getHusk().waterConversionTime;

        if (mbc_waterTimer < requiredTime) {
            mbc_waterTimer++;
            ci.cancel();
            return;
        }

        mbc_waterTimer = 0;
    }
}