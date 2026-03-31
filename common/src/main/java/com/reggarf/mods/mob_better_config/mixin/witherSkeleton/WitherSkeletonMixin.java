package com.reggarf.mods.mob_better_config.mixin.witherSkeleton;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.WitherSkeletonConfig;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WitherSkeleton.class)
public abstract class WitherSkeletonMixin {

    @Inject(
            method = "doHurtTarget",
            at = @At("TAIL")
    )
    private void mbc$modifyWitherEffect(ServerLevel level, Entity target, CallbackInfoReturnable<Boolean> cir) {

        if (!cir.getReturnValue())
            return;

        if (!(target instanceof LivingEntity living))
            return;

        WitherSkeletonConfig config = ModConfigs.getWitherSkeleton();

        living.removeEffect(MobEffects.WITHER);

        if (config.enableWitherEffect) {
            living.addEffect(new MobEffectInstance(
                    MobEffects.WITHER,
                    config.witherDuration,
                    config.witherAmplifier
            ));
        }
    }
}