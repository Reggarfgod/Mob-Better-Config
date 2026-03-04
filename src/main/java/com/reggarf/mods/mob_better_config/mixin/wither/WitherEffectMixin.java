package com.reggarf.mods.mob_better_config.mixin.wither;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.WitherConfig;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WitherSkull.class)
public abstract class WitherEffectMixin {

    @Redirect(
        method = "onHitEntity",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z"
        )
    )
    private boolean mobBetterConfig$controlWitherEffect(
            LivingEntity target,
            MobEffectInstance effect,
            Entity source
    ) {

        WitherConfig config = ModConfigs.getWither();

        // Disable WITHER effect on players
        if (!config.enableWitherEffect && target instanceof Player) {
            return false;
        }

        return target.addEffect(effect, source);
    }
}