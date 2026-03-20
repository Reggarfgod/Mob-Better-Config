package com.reggarf.mods.mob_better_config.mixin.ravager;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.RavagerConfig;

import net.minecraft.world.entity.monster.Ravager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Ravager.class)
public class RavagerRoarMixin {

    @ModifyConstant(
        method = "roar",
        constant = @Constant(floatValue = 6.0F)
    )
    private float mobBetterConfig$modifyRoarDamage(float damage) {

        RavagerConfig config = ModConfigs.getRavager();

        if (config.enableRoar) {
            damage *= (float) config.roarDamageMultiplier;
        }

        return damage;
    }
}