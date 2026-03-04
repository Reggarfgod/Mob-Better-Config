package com.reggarf.mods.mob_better_config.mixin.enderdragon;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.EnderDragonConfig;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragon.class)
public class DragonCrystalHealingMixin {

    @Inject(
            method = "checkCrystals",
            at = @At("HEAD"),
            cancellable = true
    )
    private void mobBetterConfig$disableCrystalHealing(CallbackInfo ci) {

        EnderDragonConfig config = ModConfigs.getEnderDragon();

        if (!config.enableCrystalHealing)
            ci.cancel();
    }
}