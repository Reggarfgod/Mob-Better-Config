package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.CreeperConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Explosion;

import java.lang.reflect.Field;

public class CreeperEvents {

    public static void onSpawn(Creeper creeper, ServerLevel level) {
        if (CommonMobHandler.isInitialized(creeper))
            return;
        CommonMobHandler.markInitialized(creeper);

        CreeperConfig config = ModConfigs.getCreeper();
        CommonMobHandler.applyCommonAttributes(
                creeper,
                config.health,
                config.armor,
                config.armorToughness,
                config.attackDamage,
                config.attackSpeed,
                config.movementSpeed,
                config.followRange,
                config.knockbackResistance,
                config.attackKnockback,
                config.stepHeight,
                config.gravity,
                config.glowing,
                config.CustomName,
                config.canBreakDoors,
                config.doorBreakMode,
                config.reinforcementChance
        );

        BossUtil.tryApplyBoss(
                creeper,
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

        CommonMobHandler.spawnMultiplier(
                creeper,
                level,
                config.spawnMultiplier
        );

        applySpecialConfig(creeper, level);
    }

    private static void applySpecialConfig(Creeper creeper, ServerLevel level) {

        CreeperConfig config = ModConfigs.getCreeper();

        try {

            Field radiusField = Creeper.class.getDeclaredField("explosionRadius");
            radiusField.setAccessible(true);
            radiusField.setInt(creeper, config.explosionRadius);

            Field swellField = Creeper.class.getDeclaredField("maxSwell");
            swellField.setAccessible(true);
            swellField.setInt(creeper, config.fuseTime);

        } catch (Exception ignored) {}

        if (config.powered) {

            LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);

            if (lightning != null) {
                lightning.moveTo(creeper.getX(), creeper.getY(), creeper.getZ());
                creeper.thunderHit(level, lightning);
            }
        }
    }

    public static void onTick(Creeper creeper) {

        CreeperConfig config = ModConfigs.getCreeper();

        CommonMobHandler.applyCommonTickBehaviors(
                creeper,
                true,
                config.fireImmune,
                false,
                false,
                false,
                false
        );
    }

    public static void onExplosion(Explosion explosion) {

        if (!(explosion.getIndirectSourceEntity() instanceof Creeper))
            return;

        CreeperConfig config = ModConfigs.getCreeper();

        if (config.explosionDamageMultiplier == 1.0D)
            return;

        try {

            Field radiusField = Explosion.class.getDeclaredField("radius");
            radiusField.setAccessible(true);

            float current = (float) radiusField.get(explosion);
            radiusField.set(explosion, current * (float) config.explosionDamageMultiplier);

        } catch (Exception ignored) {}
    }

    public static void onDrops(ServerLevel level, Creeper creeper) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                creeper,
                ModConfigs.getCreeper().lootMultiplier
        );
    }
}