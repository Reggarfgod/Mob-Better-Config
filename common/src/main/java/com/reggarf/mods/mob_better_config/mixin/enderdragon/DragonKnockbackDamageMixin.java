package com.reggarf.mods.mob_better_config.mixin.enderdragon;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EnderDragon.class)
public class DragonKnockbackDamageMixin {

    @ModifyConstant(
            method = "knockBack",
            constant = @Constant(floatValue = 5.0F)
    )
    private float mobBetterConfig$modifyKnockbackDamage(float constant) {

        return (float) ModConfigs.getEnderDragon().knockbackDamage;

    }
}