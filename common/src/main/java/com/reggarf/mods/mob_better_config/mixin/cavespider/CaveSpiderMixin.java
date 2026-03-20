package com.reggarf.mods.mob_better_config.mixin.cavespider;

import com.reggarf.mods.mob_better_config.config.CaveSpiderConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CaveSpider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CaveSpider.class)
public class CaveSpiderMixin {

    @Redirect(
            method = "doHurtTarget",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z"
            )
    )
    private boolean mbc$modifyPoison(LivingEntity target, MobEffectInstance effect, net.minecraft.world.entity.Entity source) {

        CaveSpiderConfig config = ModConfigs.getCaveSpider();

        // Disable poison completely
        if (!config.enablePoison) {
            return false;
        }

        int duration = (int) (140 * config.poisonDurationMultiplier);
        int amplifier = Math.max(0, (int) Math.floor(config.poisonAmplifierMultiplier - 1));

        return target.addEffect(new MobEffectInstance(
                MobEffects.POISON,
                duration,
                amplifier
        ), source);
    }
}