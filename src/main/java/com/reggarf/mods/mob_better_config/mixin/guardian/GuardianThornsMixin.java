//package com.reggarf.mods.mob_better_config.mixin.guardian;
//
//import com.reggarf.mods.mob_better_config.config.ModConfigs;
//import com.reggarf.mods.mob_better_config.config.GuardianConfig;
//
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.world.entity.monster.Guardian;
//
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Redirect;
//
//@Mixin(Guardian.class)
//public abstract class GuardianThornsMixin {
//
//    @Redirect(
//            method = "hurt",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
//            )
//    )
//    private boolean mobBetterConfig$modifyThornsDamage(
//            net.minecraft.world.entity.LivingEntity attacker,
//            DamageSource source,
//            float amount
//    ) {
//
//        GuardianConfig config = ModConfigs.getGuardian();
//
//        if (!config.enableThorns) {
//            return false; // cancel thorns completely
//        }
//
//        return attacker.hurt(source, (float) config.thornsDamage);
//    }
//}