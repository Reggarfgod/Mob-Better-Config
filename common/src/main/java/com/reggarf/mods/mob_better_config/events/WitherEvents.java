package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.WitherConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.WitherSkull;

public class WitherEvents {

    public static void onSpawn(WitherBoss wither, ServerLevel level) {
        if (CommonMobHandler.isInitialized(wither))
            return;
        CommonMobHandler.markInitialized(wither);
        WitherConfig config = ModConfigs.getWither();

        applyAll(wither, config);

        // Invulnerability phase
        wither.setInvulnerableTicks(config.invulnerableTicks);

        // Boss system
        BossUtil.tryApplyBoss(
                wither,
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

    private static void applyAll(WitherBoss wither, WitherConfig config) {

        if (config.customName)
            MobNameUtil.applyRandomName(wither);

        CommonMobHandler.applyCommonAttributes(
                wither,
                config.health,
                config.armor,
                0.0,
                0.0,
                0.0,
                config.movementSpeed,
                config.followRange,
                0.0,
                0.0,
                1.0,
                0.08,
                config.glowing,
                false,
                false,
                0,
                0.0
        );

        if (wither.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.FLYING_SPEED) != null) {
            wither.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.FLYING_SPEED)
                    .setBaseValue(config.flyingSpeed);
        }

        wither.setHealth((float) config.health);
    }

    // Skull damage
    public static float modifySkullDamage(WitherSkull skull, float damage) {

        if (!(skull.getOwner() instanceof WitherBoss))
            return damage;

        WitherConfig config = ModConfigs.getWither();

        return damage * (float) config.skullDamageMultiplier;
    }

    public static float modifyXP(float xp) {
        return xp * (float) ModConfigs.getWither().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, WitherBoss wither) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                wither,
                ModConfigs.getWither().lootMultiplier
        );
    }
}