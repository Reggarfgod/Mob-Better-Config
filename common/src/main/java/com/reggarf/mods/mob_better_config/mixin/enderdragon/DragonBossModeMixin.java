package com.reggarf.mods.mob_better_config.mixin.enderdragon;

import com.reggarf.mods.mob_better_config.config.EnderDragonConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.util.BossUtil;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragon.class)
public class DragonBossModeMixin {

    @Inject(
            method = "aiStep",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;dragonFight:Lnet/minecraft/world/level/dimension/end/EndDragonFight;",
                    shift = At.Shift.AFTER
            )
    )
    private void mobBetterConfig$applyBoss(CallbackInfo ci) {

        EnderDragon dragon = (EnderDragon)(Object)this;

        if (!(dragon.level() instanceof ServerLevel))
            return;

        // Multiloader-safe persistence check
        if (dragon.getTags().contains("mob_better_config_spawned"))
            return;

        dragon.addTag("mob_better_config_spawned");

        EnderDragonConfig config = ModConfigs.getEnderDragon();

        BossUtil.tryApplyBoss(
                dragon,
                config.bossMode,
                config.forceAllBoss,
                config.bossChance,
                config.bossHealthMultiplier,
                config.bossDamageMultiplier,
                config.bossGlowing,
                config.bossCustomName,
                config.bossXpMultiplier,
                config.bossLootMultiplier
        );
    }
}