//package com.reggarf.mods.mob_better_config.mixin.hoglin;
//
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.monster.Zoglin;
//import net.minecraft.world.entity.monster.hoglin.Hoglin;
//
//import net.minecraft.world.entity.ai.attributes.Attributes;
//
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(Hoglin.class)
//public abstract class HoglinConversionMixin {
//
//    @Inject(method = "finishConversion", at = @At("HEAD"))
//    private void mobBetterConfig$copyBossState(ServerLevel level, CallbackInfo ci) {
//
//        Hoglin hoglin = (Hoglin)(Object)this;
//
//        boolean boss = hoglin.getPersistentData()
//                .getBoolean("mob_better_config_boss");
//
//        if (!boss)
//            return;
//
//        Zoglin zoglin = hoglin.convertTo(EntityType.ZOGLIN, true);
//
//        if (zoglin == null)
//            return;
//
//        // Copy boss flag
//        zoglin.getPersistentData().putBoolean("mob_better_config_boss", true);
//
//        // Copy health
//        double hp = hoglin.getAttribute(Attributes.MAX_HEALTH).getBaseValue();
//
//        if (zoglin.getAttribute(Attributes.MAX_HEALTH) != null)
//            zoglin.getAttribute(Attributes.MAX_HEALTH).setBaseValue(hp);
//
//        zoglin.setHealth((float)hp);
//
//        // Copy damage
//        double dmg = hoglin.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
//
//        if (zoglin.getAttribute(Attributes.ATTACK_DAMAGE) != null)
//            zoglin.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(dmg);
//
//        // Copy glowing
//        if (hoglin.isCurrentlyGlowing())
//            zoglin.setGlowingTag(true);
//
//        // Copy name
//        zoglin.setCustomName(hoglin.getCustomName());
//    }
//}