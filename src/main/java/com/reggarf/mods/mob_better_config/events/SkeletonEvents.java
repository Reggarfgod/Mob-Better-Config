package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.SkeletonConfig;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class SkeletonEvents {

    @SubscribeEvent
    public void onSkeletonJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Skeleton skeleton))
            return;

        applyConfig(skeleton);

        SkeletonConfig config = ModConfigs.getSkeleton();
        BossUtil.tryApplyBoss(
                skeleton,
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
        if (skeleton.level() instanceof ServerLevel level) {

            for (int i = 1; i < config.spawnMultiplier; i++) {
                Skeleton extra = new Skeleton(EntityType.SKELETON, level);

                extra.snapTo(
                        skeleton.getX(),
                        skeleton.getY(),
                        skeleton.getZ(),
                        skeleton.getYRot(),
                        skeleton.getXRot()
                );

                level.addFreshEntity(extra);
            }
        }
    }

    private void applyConfig(Skeleton skeleton) {

        SkeletonConfig config = ModConfigs.getSkeleton();
        RandomSource random = skeleton.level().getRandom();
        if (config.CustomName) {
            MobNameUtil.applyRandomName(skeleton);
        }
        // ===== Attributes =====
        if (skeleton.getAttribute(Attributes.MAX_HEALTH) != null)
            skeleton.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.health);

        if (skeleton.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            skeleton.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(config.attackDamage);

        if (skeleton.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            skeleton.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(config.movementSpeed);

        if (skeleton.getAttribute(Attributes.FOLLOW_RANGE) != null)
            skeleton.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(config.followRange);

        if (skeleton.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            skeleton.getAttribute(Attributes.KNOCKBACK_RESISTANCE)
                    .setBaseValue(config.knockbackResistance);

        if (skeleton.getAttribute(Attributes.ARMOR) != null)
            skeleton.getAttribute(Attributes.ARMOR).setBaseValue(config.armor);

        if (skeleton.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            skeleton.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(config.attackKnockback);
        if (skeleton.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            skeleton.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(config.reinforcementChance);

        skeleton.setHealth(config.health);

        if (!config.burnInDaylight)
            skeleton.clearFire();


        if (config.fireImmune)
            skeleton.setRemainingFireTicks(0);


        if (config.glowing)
            skeleton.setGlowingTag(true);

        if (random.nextDouble() < config.randomArmorChance) {
            ArmorUtil.equipRandomArmor(skeleton, random, 0.5f);
        }
    }
    @SubscribeEvent
    public void onSkeletonTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Skeleton skeleton))
            return;

        SkeletonConfig config = ModConfigs.getSkeleton();

        // Prevent daylight burn
        if (!config.burnInDaylight && skeleton.isOnFire())
            skeleton.clearFire();

        // Fire immunity persistent
        if (config.fireImmune && skeleton.isOnFire())
            skeleton.clearFire();

        // Rapid fire toggle (basic aggressive mode)
        if (config.rapidFire && skeleton.getTarget() != null) {
            skeleton.setAggressive(true);
        }
    }

    @SubscribeEvent
    public void onArrowSpawn(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof AbstractArrow arrow))
            return;

        if (!(arrow.getOwner() instanceof Skeleton))
            return;

        SkeletonConfig config = ModConfigs.getSkeleton();

        if (config.bowPowerMultiplier != 1.0D) {

            double baseDamage = 2.0D;
            arrow.setBaseDamage(baseDamage * config.bowPowerMultiplier);
        }
    }
    @SubscribeEvent
    public void onSkeletonDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Skeleton skeleton))
            return;

        if (!(skeleton.level() instanceof ServerLevel level))
            return;

        double multiplier = ModConfigs.getSkeleton().lootMultiplier;

        LootUtil.applyLootMultiplier(event, level, skeleton, multiplier);
    }
}