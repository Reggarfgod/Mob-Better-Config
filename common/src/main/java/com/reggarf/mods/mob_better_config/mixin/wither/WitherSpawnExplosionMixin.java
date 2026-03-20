package com.reggarf.mods.mob_better_config.mixin.wither;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.WitherConfig;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.Level;
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
            target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"
        )
    )
    private net.minecraft.world.level.Explosion mobBetterConfig$modifySpawnExplosion(
            Level level,
            net.minecraft.world.entity.Entity entity,
            double x,
            double y,
            double z,
            float power,
            boolean fire,
            ExplosionInteraction interaction
    ) {

        WitherConfig config = ModConfigs.getWither();

        float newPower = config.spawnExplosionPower;

        return level.explode(entity, x, y, z, newPower, fire, interaction);
    }
}