package com.reggarf.mods.mob_better_config.mixin.enderdragon;

import com.reggarf.mods.mob_better_config.config.EnderDragonConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.util.VexReinforcementUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragon.class)
public class DragonVexReinforcementMixin {

    @Inject(
            method = "aiStep",
            at = @At("TAIL")
    )
    private void mobBetterConfig$vexReinforcement(CallbackInfo ci) {

        EnderDragon dragon = (EnderDragon)(Object) this;

        if (!(dragon.level() instanceof ServerLevel level))
            return;

        EnderDragonConfig config = ModConfigs.getEnderDragon();

        VexReinforcementUtil.trySpawnVexReinforcement(
                dragon,
                level,
                config.enableVexReinforcements,
                config.vexRequireBelowHalfHealth,
                config.vexSpawnChance,
                config.vexCount,
                config.vexSpawnRadius,
                config.vexLifeTimeSeconds,
                config.vexMaxNearby,
                config.vexHealth,
                config.vexSpeed,
                config.vexAttackDamage,
                config.vexTargetPlayerDistance
        );
    }
}