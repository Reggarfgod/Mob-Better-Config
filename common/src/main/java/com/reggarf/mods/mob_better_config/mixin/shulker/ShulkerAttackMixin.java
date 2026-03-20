package com.reggarf.mods.mob_better_config.mixin.shulker;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.entity.monster.Shulker$ShulkerAttackGoal")
public class ShulkerAttackMixin {

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
            ),
            cancellable = true
    )
    private void mbc$cancelBulletSpawn(CallbackInfo ci) {
        if (!ModConfigs.getShulker().enableBulletAttack) {
            ci.cancel(); // stops bullet spawn
        }
    }
}