package com.reggarf.mods.mob_better_config.mixin.creeper;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.CreeperConfig;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public abstract class CreeperMixin {

    // 🔥 Replace reflection with direct access
    @Shadow @Mutable private int explosionRadius;
    @Shadow @Mutable private int maxSwell;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void applyConfig(EntityType<? extends Creeper> type, Level level, CallbackInfo ci) {

        CreeperConfig config = ModConfigs.getCreeper();

        this.explosionRadius = config.explosionRadius;
        this.maxSwell = config.fuseTime;
    }
}