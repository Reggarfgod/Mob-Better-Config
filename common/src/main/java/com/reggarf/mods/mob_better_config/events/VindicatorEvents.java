package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.VindicatorConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.network.chat.Component;

public class VindicatorEvents {

    public static void onSpawn(Vindicator vindicator, ServerLevel level) {
        if (CommonMobHandler.isInitialized(vindicator))
            return;

        CommonMobHandler.markInitialized(vindicator);

        VindicatorConfig config = ModConfigs.getVindicator();

        applyAll(vindicator, config);

        // Boss
        BossUtil.tryApplyBoss(
                vindicator,
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

        // Johnny Mode
        if (config.enableJohnnyMode) {
            vindicator.setCustomName(Component.literal("Johnny"));
            vindicator.setPersistenceRequired();
        }

        // Spawn multiplier
        for (int i = 1; i < config.spawnMultiplier; i++) {

            Vindicator extra = new Vindicator(EntityType.VINDICATOR, level);

            extra.moveTo(
                    vindicator.getX(),
                    vindicator.getY(),
                    vindicator.getZ(),
                    vindicator.getYRot(),
                    vindicator.getXRot()
            );

            applyAll(extra, config);

            if (config.enableJohnnyMode) {
                extra.setCustomName(Component.literal("Johnny"));
                extra.setPersistenceRequired();
            }

            level.addFreshEntity(extra);
        }
    }

    private static void applyAll(Vindicator vindicator, VindicatorConfig config) {

        CommonMobHandler.applyCommonAttributes(
                vindicator,
                config.health,
                config.armor,
                0.0,
                config.attackDamage,
                0.0,
                config.movementSpeed,
                config.followRange,
                config.knockbackResistance,
                config.attackKnockback,
                0.6,
                0.08,
                config.glowing,
                config.CustomName,
                false,
                0,
                config.reinforcementChance
        );

        vindicator.setHealth((float) config.health);

        if (config.fireImmune)
            vindicator.setRemainingFireTicks(0);
    }

    public static float modifyXP(float xp) {
        return xp * (float) ModConfigs.getVindicator().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, Vindicator vindicator) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                vindicator,
                ModConfigs.getVindicator().lootMultiplier
        );
    }
}