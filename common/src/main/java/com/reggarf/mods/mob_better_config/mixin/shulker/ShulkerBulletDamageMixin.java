package com.reggarf.mods.mob_better_config.mixin.shulker;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ShulkerBullet.class)
public class ShulkerBulletDamageMixin {

    @ModifyArg(
            method = "onHitEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurtOrSimulate(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
            ),
            index = 1,
            require = 0
    )
    private float mbc$modifyBulletDamage_new(float originalDamage) {
        return applyMultiplier(originalDamage);
    }

    private float applyMultiplier(float originalDamage) {

        ShulkerBullet bullet = (ShulkerBullet)(Object)this;
        Entity owner = bullet.getOwner();

        if (!(owner instanceof Shulker))
            return originalDamage;

        float multiplier = (float) ModConfigs.getShulker().bulletDamageMultiplier;

        return originalDamage * multiplier;
    }
}