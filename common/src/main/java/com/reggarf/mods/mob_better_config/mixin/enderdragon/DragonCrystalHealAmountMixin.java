package com.reggarf.mods.mob_better_config.mixin.enderdragon;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EnderDragon.class)
public class DragonCrystalHealAmountMixin {

    @ModifyConstant(
            method = "checkCrystals",
            constant = @org.spongepowered.asm.mixin.injection.Constant(floatValue = 1.0F)
    )
    private float mobBetterConfig$modifyHealAmount(float constant) {

        return (float) ModConfigs.getEnderDragon().crystalHealAmount;
    }
}