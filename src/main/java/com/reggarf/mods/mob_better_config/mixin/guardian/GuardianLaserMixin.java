//package com.reggarf.mods.mob_better_config.mixin.guardian;
//
//import com.reggarf.mods.mob_better_config.config.ModConfigs;
//import com.reggarf.mods.mob_better_config.config.GuardianConfig;
//
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(targets = "net.minecraft.world.entity.monster.Guardian$GuardianAttackGoal")
//public abstract class GuardianLaserMixin {
//
//    @Inject(
//            method = "canUse",
//            at = @At("HEAD"),
//            cancellable = true
//    )
//    private void mobBetterConfig$disableLaser(CallbackInfoReturnable<Boolean> cir) {
//
//        GuardianConfig config = ModConfigs.getGuardian();
//
//        if (!config.enableLaser) {
//            cir.setReturnValue(false);
//        }
//    }
//}