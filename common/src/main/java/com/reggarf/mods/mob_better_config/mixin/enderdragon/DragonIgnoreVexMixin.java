package com.reggarf.mods.mob_better_config.mixin.enderdragon;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Vex;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnderDragon.class)
public class DragonIgnoreVexMixin {

    /**
     * Prevent Ender Dragon from damaging Vex
     */
    @Redirect(
            method = {"knockBack", "hurt"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
            )
    )
    private boolean mobBetterConfig$ignoreVexDamage(
            Entity entity,
            DamageSource source,
            float amount
    ) {

        if (entity instanceof Vex) {
            return false;
        }

        return entity.hurt(source, amount);
    }
}