package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.WitherSkeletonConfig;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class WitherSkeletonEvents {

    private static final String WITHER_TAG = "mob_better_config_wither_flag";

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof WitherSkeleton skeleton))
            return;

        if (!(skeleton.level() instanceof ServerLevel level))
            return;

        WitherSkeletonConfig config = ModConfigs.getWitherSkeleton();

        if (skeleton.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(skeleton, config);

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

        // Spawn multiplier
        for (int i = 1; i < config.spawnMultiplier; i++) {

            WitherSkeleton extra =
                    new WitherSkeleton(EntityType.WITHER_SKELETON, level);

            extra.moveTo(
                    skeleton.getX(),
                    skeleton.getY(),
                    skeleton.getZ(),
                    skeleton.getYRot(),
                    skeleton.getXRot()
            );

            extra.getPersistentData()
                    .putBoolean("mob_better_config_spawned", true);

            applyConfig(extra, config);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(WitherSkeleton skeleton,
                             WitherSkeletonConfig config) {
        RandomSource random = skeleton.level().getRandom();
        if (config.CustomName) {
            MobNameUtil.applyRandomName(skeleton);
        }

        if (skeleton.getAttribute(Attributes.MAX_HEALTH) != null)
            skeleton.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (skeleton.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            skeleton.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage);

        if (skeleton.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            skeleton.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);

        if (skeleton.getAttribute(Attributes.FOLLOW_RANGE) != null)
            skeleton.getAttribute(Attributes.FOLLOW_RANGE)
                    .setBaseValue(config.followRange);

        skeleton.setHealth((float) config.health);
        if (random.nextDouble() < config.randomArmorChance) {
            ArmorUtil.equipRandomArmor(skeleton, random, 0.5f);
        }
    }

    @SubscribeEvent
    public void onMeleeHit(LivingDamageEvent.Post event) {

        if (event.getSource().getEntity() instanceof WitherSkeleton &&
                event.getEntity() instanceof LivingEntity target) {

            // Mark target for next tick processing
            target.getPersistentData().putBoolean(WITHER_TAG, true);
        }
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof LivingEntity target))
            return;

        if (!target.getPersistentData().getBoolean(WITHER_TAG))
            return;

        target.getPersistentData().remove(WITHER_TAG);

        WitherSkeletonConfig config = ModConfigs.getWitherSkeleton();

        // Remove vanilla Wither effect FIRST
        if (target.hasEffect(MobEffects.WITHER))
            target.removeEffect(MobEffects.WITHER);

        if (!config.enableWitherEffect)
            return;

        target.addEffect(new MobEffectInstance(
                MobEffects.WITHER,
                config.witherDuration,
                config.witherAmplifier
        ));
    }

    @SubscribeEvent
    public void onArrowSpawn(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof AbstractArrow arrow))
            return;

        if (!(arrow.getOwner() instanceof WitherSkeleton))
            return;

        WitherSkeletonConfig config = ModConfigs.getWitherSkeleton();

        if (!config.flamingArrows) {
            arrow.clearFire();
            arrow.setRemainingFireTicks(0);
        } else {
            arrow.setRemainingFireTicks(config.arrowFireSeconds);
        }

        arrow.setBaseDamage(config.arrowDamage);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        XPUtil.applyXpIfInstance(
                event,
                WitherSkeleton.class,
                ModConfigs.getWitherSkeleton().xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof WitherSkeleton skeleton))
            return;

        if (!(skeleton.level() instanceof ServerLevel level))
            return;

        LootUtil.applyLootMultiplier(
                event,
                level,
                skeleton,
                ModConfigs.getWitherSkeleton().lootMultiplier
        );
    }

    @SubscribeEvent
    public void onDamaged(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof WitherSkeleton skeleton))
            return;

        if (!(skeleton.level() instanceof ServerLevel level))
            return;

        WitherSkeletonConfig config = ModConfigs.getWitherSkeleton();

        ReinforcementUtil.trySpawnReinforcement(
                skeleton,
                level,
                config.reinforcementChance,
                4
        );
    }
}