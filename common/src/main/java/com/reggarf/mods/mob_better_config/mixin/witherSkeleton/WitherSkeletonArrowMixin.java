package com.reggarf.mods.mob_better_config.mixin.witherSkeleton;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.WitherSkeletonConfig;

import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(WitherSkeleton.class)
public abstract class WitherSkeletonArrowMixin {

    @Inject(
            method = "getArrow",
            at = @At("RETURN")
    )
    private void mbc$modifyArrow(
            ItemStack weapon,
            float velocity,
            ItemStack ammo,
            CallbackInfoReturnable<AbstractArrow> cir
    ) {

        AbstractArrow arrow = cir.getReturnValue();

        if (arrow == null)
            return;

        WitherSkeletonConfig config = ModConfigs.getWitherSkeleton();

        if (!config.flamingArrows) {
            arrow.clearFire();
            arrow.setRemainingFireTicks(0);
        } else {
            arrow.setRemainingFireTicks(config.arrowFireSeconds);
        }

        arrow.setBaseDamage(config.arrowDamage);
    }
}