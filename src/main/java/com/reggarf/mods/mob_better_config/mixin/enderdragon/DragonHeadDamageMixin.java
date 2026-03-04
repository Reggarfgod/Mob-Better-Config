package com.reggarf.mods.mob_better_config.mixin.enderdragon;

import com.reggarf.mods.mob_better_config.config.ModConfigs;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Constant;

@Mixin(EnderDragon.class)
public class DragonHeadDamageMixin {

    @ModifyConstant(
            method = "hurt",
            constant = @Constant(floatValue = 10.0F)
    )
    private float mobBetterConfig$modifyHeadDamage(float constant) {

        return (float) ModConfigs.getEnderDragon().headDamage;

    }
}