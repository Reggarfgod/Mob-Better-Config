package com.reggarf.mods.mob_better_config.mixin.shulker;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import net.minecraft.world.entity.monster.Shulker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Shulker.class)
public class ShulkerCloneMixin {

    @Inject(method = "hitByShulkerBullet", at = @At("HEAD"), cancellable = true)
    private void mbc$cloneControl(CallbackInfo ci) {

        if (!ModConfigs.getShulker().enableClone) {
            ci.cancel();
        }
    }
}