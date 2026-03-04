package com.reggarf.mods.mob_better_config.mixin.enderdragon;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.EnderDragonConfig;
import com.reggarf.mods.mob_better_config.util.MobNameUtil;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragon.class)
public class DragonCustomNameMixin {

    @Inject(
            method = "aiStep",
            at = @At("TAIL")
    )
    private void mobBetterConfig$applyCustomName(CallbackInfo ci) {

        EnderDragon dragon = (EnderDragon)(Object) this;

        if (!(dragon.level() instanceof ServerLevel))
            return;

        EnderDragonConfig config = ModConfigs.getEnderDragon();

        if (config.customName)
            MobNameUtil.applyRandomName(dragon);
    }
}