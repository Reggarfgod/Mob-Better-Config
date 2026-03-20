package com.reggarf.mods.mob_better_config.mixin.wither;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.WitherConfig;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WitherBoss.class)
public abstract class WitherBossRegenMixin {

    @Redirect(
            method = "customServerAiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/boss/wither/WitherBoss;heal(F)V"
            )
    )
    private void mobBetterConfig$controlRegen(WitherBoss wither, float vanillaAmount) {

        WitherConfig config = ModConfigs.getWither();
        if (!config.enableRegeneration)
            return;
        if (wither.tickCount % config.regenerationInterval != 0)
            return;
        float newAmount = (float)(vanillaAmount * config.regenerationAmount);

        wither.heal(newAmount);
    }
}