package com.reggarf.mods.mob_better_config.mixin.pillager;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.PillagerConfig;

import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CrossbowItem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {

    @ModifyArg(
            method = "onHitEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurtOrSimulate(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
            ),
            index = 1
    )
    private float mobbetterconfig$modifyArrowDamage(float damage) {

        AbstractArrow arrow = (AbstractArrow)(Object)this;

        if (!(arrow.getOwner() instanceof Pillager pillager))
            return damage;

        if (!(pillager.getMainHandItem().getItem() instanceof CrossbowItem))
            return damage;

        PillagerConfig config = ModConfigs.getPillager();

        return damage * (float) config.crossbowDamageMultiplier;
    }
}