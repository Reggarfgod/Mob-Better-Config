package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.StrayConfig;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.skeleton.Stray;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class StrayEvents {

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Stray stray))
            return;

        if (!(stray.level() instanceof ServerLevel level))
            return;

        StrayConfig config = ModConfigs.getStray();

        if (NbtUtil.getBooleanSafe(stray.getPersistentData(),"mob_better_config_spawned"))
            return;

        applyConfig(stray);
        BossUtil.tryApplyBoss(
                stray,
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

            Stray extra = new Stray(EntityType.STRAY, level);

            extra.snapTo(
                    stray.getX(),
                    stray.getY(),
                    stray.getZ(),
                    stray.getYRot(),
                    stray.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);
            level.addFreshEntity(extra);
        }
    }
    @SubscribeEvent
    public void onArrowDamage(net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre event) {

        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow))
            return;

        if (!(arrow.getOwner() instanceof Stray))
            return;

        StrayConfig config = ModConfigs.getStray();

        if (config.arrowDamageMultiplier == 1.0D)
            return;

        float newDamage = (float)(event.getNewDamage() * config.arrowDamageMultiplier);

        event.setNewDamage(newDamage);
    }
    private void applyConfig(Stray stray) {

        StrayConfig config = ModConfigs.getStray();
        if (config.CustomName) {
            MobNameUtil.applyRandomName(stray);
        }
        if (stray.getAttribute(Attributes.MAX_HEALTH) != null)
            stray.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.health);

        if (stray.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            stray.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(config.attackDamage);

        if (stray.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            stray.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(config.movementSpeed);

        if (stray.getAttribute(Attributes.FOLLOW_RANGE) != null)
            stray.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(config.followRange);

        if (stray.getAttribute(Attributes.ARMOR) != null)
            stray.getAttribute(Attributes.ARMOR).setBaseValue(config.armor);

        if (stray.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            stray.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(config.knockbackResistance);

        if (stray.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            stray.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(config.attackKnockback);

        if (stray.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            stray.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(config.reinforcementChance);

        stray.setHealth(config.health);

        if (config.fireImmune)
            stray.setRemainingFireTicks(0);

        if (config.glowing)
            stray.setGlowingTag(true);

        // Random Armor
        if (config.randomArmor &&
                stray.getRandom().nextDouble() < config.armorChance) {

            ArmorUtil.equipRandomArmor(stray, stray.getRandom(), 0.4F);
        }
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Stray stray))
            return;

        StrayConfig config = ModConfigs.getStray();

        if (!config.burnInDaylight && stray.isOnFire()) {
            stray.clearFire();
        }
    }

    @SubscribeEvent
    public void onArrowSpawn(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof Arrow arrow))
            return;

        if (!(arrow.getOwner() instanceof Stray))
            return;

        StrayConfig config = ModConfigs.getStray();

        // Clear vanilla potion
        arrow.getPickupItemStackOrigin()
                .set(net.minecraft.core.component.DataComponents.POTION_CONTENTS,
                        PotionContents.EMPTY);

        if (!config.enableSlowness)
            return;

        arrow.addEffect(new MobEffectInstance(
                MobEffects.SLOWNESS,
                config.slownessDuration,
                config.slownessAmplifier
        ));
    }



    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Stray stray))
            return;

        if (!(stray.level() instanceof ServerLevel level))
            return;

        StrayConfig config = ModConfigs.getStray();

        LootUtil.applyLootMultiplier(
                event,
                level,
                stray,
                config.lootMultiplier
        );
    }
}