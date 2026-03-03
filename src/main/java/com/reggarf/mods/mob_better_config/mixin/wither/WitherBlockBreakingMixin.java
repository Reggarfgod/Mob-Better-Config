package com.reggarf.mods.mob_better_config.mixin.wither;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.WitherConfig;

import net.minecraft.world.entity.boss.wither.WitherBoss;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherBoss.class)
public abstract class WitherBlockBreakingMixin {

    @Shadow
    private int destroyBlocksTick;

    @Inject(method = "customServerAiStep", at = @At("HEAD"))
    private void mobBetterConfig$disableBlockBreaking(CallbackInfo ci) {

        WitherConfig config = ModConfigs.getWither();

        if (!config.enableBlockBreaking) {
            this.destroyBlocksTick = 0;
        }
    }
}