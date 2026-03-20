package com.reggarf.mods.mob_better_config.mixin.brute;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AbstractPiglin.class)
public abstract class AbstractPiglinMixin {

    /**
     * Disable zombification if config says so
     */
    @Inject(method = "isConverting", at = @At("HEAD"), cancellable = true)
    private void mobbetterconfig$disableZombification(CallbackInfoReturnable<Boolean> cir) {

        if (ModConfigs.getPiglinBrute().disableZombification) {
            cir.setReturnValue(false);
        }
    }

    /**
     * Copy Piglin Brute stats to the converted Zombified Piglin
     */
    @Inject(method = "finishConversion", at = @At("TAIL"))
    private void mobbetterconfig$copyBruteStats(ServerLevel level, CallbackInfo ci) {

        AbstractPiglin piglin = (AbstractPiglin)(Object)this;

        if (!(piglin instanceof PiglinBrute brute))
            return;

        // find the zombified piglin that replaced this brute
        ZombifiedPiglin zombified = level.getEntitiesOfClass(
                ZombifiedPiglin.class,
                brute.getBoundingBox().inflate(2.0D)
        ).stream().findFirst().orElse(null);

        if (zombified == null)
            return;

        copyAttribute(brute, zombified, Attributes.MAX_HEALTH);
        copyAttribute(brute, zombified, Attributes.ATTACK_DAMAGE);
        copyAttribute(brute, zombified, Attributes.MOVEMENT_SPEED);
        copyAttribute(brute, zombified, Attributes.ARMOR);
        copyAttribute(brute, zombified, Attributes.KNOCKBACK_RESISTANCE);

        zombified.setHealth(brute.getHealth());
    }
    private static void copyAttribute(
        LivingEntity from,
        LivingEntity to,
        net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute
    ) {

        var fromAttr = from.getAttribute(attribute);
        var toAttr = to.getAttribute(attribute);

        if (fromAttr == null || toAttr == null)
            return;

        toAttr.setBaseValue(fromAttr.getBaseValue());
    }
}