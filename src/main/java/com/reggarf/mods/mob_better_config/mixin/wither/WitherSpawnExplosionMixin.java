package com.reggarf.mods.mob_better_config.mixin.wither;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.WitherConfig;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.Level.ExplosionInteraction;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WitherBoss.class)
public abstract class WitherSpawnExplosionMixin {

    @Redirect(
            method = "customServerAiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;explode(Lnet/minecraft/world/entity/Entity;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)V"
            )
    )
    private void mobBetterConfig$modifySpawnExplosion(
            ServerLevel level,
            Entity entity,
            double x,
            double y,
            double z,
            float power,
            boolean fire,
            ExplosionInteraction interaction
    ) {

        WitherConfig config = ModConfigs.getWither();

        float newPower = config.spawnExplosionPower;

        level.explode(entity, x, y, z, newPower, fire, interaction);
    }
}