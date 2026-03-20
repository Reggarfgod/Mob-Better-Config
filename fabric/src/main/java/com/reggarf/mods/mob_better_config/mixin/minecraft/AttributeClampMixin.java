package com.reggarf.mods.mob_better_config.mixin.minecraft;


import com.reggarf.mods.mob_better_config.util.AttributeClampUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = {
        "net.minecraft.world.entity.ai.attributes.RangedAttribute"
})
public abstract class AttributeClampMixin {

    /**
     * Remove vanilla attribute min/max clamping
     */
    @Dynamic
    @Inject(method = "sanitizeValue", at = @At("HEAD"), cancellable = true)
    private void mbc$removeClamp(double value, CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(AttributeClampUtil.sanitize(value));
    }
}