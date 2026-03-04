package com.reggarf.mods.mob_better_config.mixin.enderdragon;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.EnderDragonConfig;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.phys.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnderDragon.class)
public class DragonXpMixin {

    @Redirect(
            method = "tickDeath",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"
            )
    )
    private void mobBetterConfig$applyXpMultiplier(
            ServerLevel level,
            Vec3 pos,
            int xp
    ) {

        EnderDragonConfig config = ModConfigs.getEnderDragon();

        int modifiedXp = Mth.floor(xp * config.xpMultiplier);

        ExperienceOrb.award(level, pos, modifiedXp);
    }
}