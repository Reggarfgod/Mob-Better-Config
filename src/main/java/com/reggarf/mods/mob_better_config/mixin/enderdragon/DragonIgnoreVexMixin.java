package com.reggarf.mods.mob_better_config.mixin.enderdragon;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

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
                    target = "Lnet/minecraft/world/entity/Entity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z"
            )
    )
    private boolean mobBetterConfig$ignoreVexDamage(
            Entity entity,
            ServerLevel level,
            DamageSource source,
            float amount
    ) {

        if (entity instanceof Vex) {
            return false;
        }

        return entity.hurtServer(level, source, amount);
    }
}