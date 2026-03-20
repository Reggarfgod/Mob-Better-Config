package com.reggarf.mods.mob_better_config.mixin.enderdragon;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EnderDragon.class)
public class DragonCrystalHealIntervalMixin {

    @ModifyConstant(
            method = "checkCrystals",
            constant = @Constant(intValue = 10)
    )
    private int mobBetterConfig$modifyHealInterval(int constant) {

        return ModConfigs.getEnderDragon().crystalHealInterval;

    }
}