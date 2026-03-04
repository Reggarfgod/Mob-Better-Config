package com.reggarf.mods.mob_better_config.mixin.wither;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.WitherConfig;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WitherBoss.class)
public abstract class WitherSkullMixin {

    @Redirect(
        method = "performRangedAttack(IDDDZ)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
        )
    )
    private boolean mobBetterConfig$controlWitherSkull(
            Level level,
            Entity entity
    ) {

        WitherConfig config = ModConfigs.getWither();

        if (!config.enableWitherSkull) {
            return false;
        }

        return level.addFreshEntity(entity);
    }
}