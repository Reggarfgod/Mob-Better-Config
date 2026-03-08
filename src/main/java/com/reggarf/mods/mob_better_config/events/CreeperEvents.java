package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.CreeperConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Explosion;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.lang.reflect.Field;

public class CreeperEvents {

    private static final String SPAWN_TAG = "mob_better_config_spawned";

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (event.getLevel().isClientSide())
            return;

        if (!(event.getEntity() instanceof Creeper creeper))
            return;

        if (!(creeper.level() instanceof ServerLevel level))
            return;

        CreeperConfig config = ModConfigs.getCreeper();

        if (!NbtUtil.getBooleanSafe(creeper.getPersistentData(), SPAWN_TAG))
            return;

        applyConfig(creeper);
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
        for (int i = 1; i < config.spawnMultiplier; i++) {

            Creeper extra = new Creeper(EntityType.CREEPER, level);

            extra.snapTo(
                    creeper.getX(),
                    creeper.getY(),
                    creeper.getZ(),
                    creeper.getYRot(),
                    creeper.getXRot()
            );

            extra.getPersistentData().putBoolean(SPAWN_TAG, true);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(Creeper creeper) {

        CreeperConfig config = ModConfigs.getCreeper();
        if (config.CustomName) {
            MobNameUtil.applyRandomName(creeper);
        }
        if (creeper.getAttribute(Attributes.MAX_HEALTH) != null)
            creeper.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.health);

        if (creeper.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            creeper.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(config.movementSpeed);

        if (creeper.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            creeper.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(config.reinforcementChance);

        if (creeper.getAttribute(Attributes.ARMOR) != null)
            creeper.getAttribute(Attributes.ARMOR).setBaseValue(config.armor);

        if (creeper.getAttribute(Attributes.FOLLOW_RANGE) != null)
            creeper.getAttribute(Attributes.FOLLOW_RANGE)
                    .setBaseValue(config.followRange);

        if (creeper.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            creeper.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(config.knockbackResistance);


        creeper.setHealth(config.health);

        try {
            Field radiusField = Creeper.class.getDeclaredField("explosionRadius");
            radiusField.setAccessible(true);
            radiusField.setInt(creeper, config.explosionRadius);

            Field swellField = Creeper.class.getDeclaredField("maxSwell");
            swellField.setAccessible(true);
            swellField.setInt(creeper, config.fuseTime);

        } catch (Exception ignored) {}

        if (config.powered && creeper.level() instanceof ServerLevel level) {

            LightningBolt lightning =
                    EntityType.LIGHTNING_BOLT.create(level, EntitySpawnReason.NATURAL);

            if (lightning != null) {
                lightning.snapTo(creeper.getX(), creeper.getY(), creeper.getZ());
                creeper.thunderHit(level, lightning);
            }
        }

        // Fire Immunity
        if (config.fireImmune) {
            creeper.clearFire();
        }

        // Glowing
        if (config.glowing) {
            creeper.setGlowingTag(true);
        }
    }


    // REINFORCEMENT
    @SubscribeEvent
    public void onDamaged(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Creeper creeper))
            return;

        if (!(creeper.level() instanceof ServerLevel level))
            return;

        CreeperConfig config = ModConfigs.getCreeper();

        ReinforcementUtil.trySpawnReinforcement(
                creeper,
                level,
                config.reinforcementChance,
                4
        );
    }


    // LOOT

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Creeper creeper))
            return;

        if (!(creeper.level() instanceof ServerLevel level))
            return;

        CreeperConfig config = ModConfigs.getCreeper();

        LootUtil.applyLootMultiplier(
                event,
                level,
                creeper,
                config.lootMultiplier
        );
    }


    // FIRE IMMUNITY (Persistent)
    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Creeper creeper))
            return;

        CreeperConfig config = ModConfigs.getCreeper();

        if (config.fireImmune && creeper.isOnFire()) {
            creeper.clearFire();
        }
    }


    // EXPLOSION SCALING
    @SubscribeEvent
    public void onExplosion(ExplosionEvent.Start event) {

        Explosion explosion = event.getExplosion();

        if (!(explosion.getIndirectSourceEntity() instanceof Creeper))
            return;

        CreeperConfig config = ModConfigs.getCreeper();

        if (config.explosionDamageMultiplier == 1.0D)
            return;

        try {
            Field radiusField = Explosion.class.getDeclaredField("radius");
            radiusField.setAccessible(true);

            float currentRadius = (float) radiusField.get(explosion);
            float newRadius = (float) (currentRadius * config.explosionDamageMultiplier);

            radiusField.set(explosion, newRadius);

        } catch (Exception ignored) {}
    }
}