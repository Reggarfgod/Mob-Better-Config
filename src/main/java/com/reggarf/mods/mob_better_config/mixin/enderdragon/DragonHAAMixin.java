package com.reggarf.mods.mob_better_config.mixin.enderdragon;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.EnderDragonConfig;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragon.class)
public class DragonHAAMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void mobBetterConfig$applyDragonHealth(CallbackInfo ci) {

        EnderDragon dragon = (EnderDragon)(Object)this;
        EnderDragonConfig config = ModConfigs.getEnderDragon();

        if (dragon.getAttribute(Attributes.MAX_HEALTH) != null) {
            dragon.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.health);
        }

        if (dragon.getAttribute(Attributes.ARMOR) != null) {
            dragon.getAttribute(Attributes.ARMOR).setBaseValue(config.armor);
        }

        if (dragon.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
            dragon.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(config.attackDamage);
        }

        dragon.setHealth((float) config.health);
    }
}