package com.reggarf.mods.mob_better_config.mixin.enderdragon;

import com.reggarf.mods.mob_better_config.config.EnderDragonConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.data.MobData;
import com.reggarf.mods.mob_better_config.data.MobStats;
import com.reggarf.mods.mob_better_config.util.BossUtil;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragon.class)
public class DragonBossModeMixin {

    @Inject(
            method = "aiStep",
            at = @At("HEAD")
    )
    private void mobBetterConfig$applyBoss(CallbackInfo ci) {

        EnderDragon dragon = (EnderDragon)(Object)this;

        if (!(dragon.level() instanceof ServerLevel))
            return;

        if (!(dragon instanceof Mob mob))
            return;
        MobStats stats = MobData.get(mob);
        if (stats.spawned)
            return;

        stats.spawned = true;

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