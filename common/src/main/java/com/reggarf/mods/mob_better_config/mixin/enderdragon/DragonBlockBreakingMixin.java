package com.reggarf.mods.mob_better_config.mixin.enderdragon;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderDragon.class)
public class DragonBlockBreakingMixin {

    @Inject(
            method = "checkWalls",
            at = @At("HEAD"),
            cancellable = true
    )
    private void mobBetterConfig$disableBlockBreaking(
            ServerLevel level,
            AABB box,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (!ModConfigs.getEnderDragon().enableBlockBreaking) {
            cir.setReturnValue(false);
        }
    }
}