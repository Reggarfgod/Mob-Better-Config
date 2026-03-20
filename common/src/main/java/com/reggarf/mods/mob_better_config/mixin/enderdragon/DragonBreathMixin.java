package com.reggarf.mods.mob_better_config.mixin.enderdragon;

import com.reggarf.mods.mob_better_config.config.EnderDragonConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.AreaEffectCloud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AreaEffectCloud.class)
public class DragonBreathMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void mobBetterConfig$disableDragonBreath(CallbackInfo ci) {

        AreaEffectCloud cloud = (AreaEffectCloud)(Object)this;

        EnderDragonConfig config = ModConfigs.getEnderDragon();

        if (!config.enableDragonBreath) {
            if (cloud.getParticle() == ParticleTypes.DRAGON_BREATH) {
                cloud.discard();
                ci.cancel();

            }
        }
    }
}