package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.HuskConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class HuskEvents {

    private static final String HUNGER_TAG = "mob_better_config_hunger_flag";


    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Husk husk))
            return;

        if (!(husk.level() instanceof ServerLevel level))
            return;

        HuskConfig config = ModConfigs.getHusk();

        if (husk.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(husk, config);

        BossUtil.tryApplyBoss(
                husk,
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

            Husk extra = new Husk(EntityType.HUSK, level);

            extra.moveTo(
                    husk.getX(),
                    husk.getY(),
                    husk.getZ(),
                    husk.getYRot(),
                    husk.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);
            applyConfig(extra, config);
            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(Husk husk, HuskConfig config) {
        if (config.CustomName) {
            MobNameUtil.applyRandomName(husk);
        }
        if (husk.getAttribute(Attributes.MAX_HEALTH) != null)
            husk.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.health);

        if (husk.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            husk.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(config.attackDamage);

        if (husk.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            husk.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(config.movementSpeed);

        if (husk.getAttribute(Attributes.FOLLOW_RANGE) != null)
            husk.getAttribute(Attributes.FOLLOW_RANGE)
                    .setBaseValue(config.followRange);

        if (husk.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            husk.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(config.reinforcementChance);

        if (husk.getAttribute(Attributes.ARMOR) != null)
            husk.getAttribute(Attributes.ARMOR).setBaseValue(config.armor);

        if (husk.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            husk.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(config.knockbackResistance);

        if (husk.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            husk.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(config.attackKnockback);

        husk.setHealth(husk.getMaxHealth());

        if (config.fireImmune)
            husk.setRemainingFireTicks(0);

        if (config.glowing)
            husk.setGlowingTag(true);

        // Desert Buff
        if (config.desertBuff &&
                husk.level().getBiome(husk.blockPosition())
                        .is(BiomeTags.HAS_DESERT_PYRAMID)) {

            if (husk.getAttribute(Attributes.MAX_HEALTH) != null)
                husk.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.desertHealth);
            if (husk.getAttribute(Attributes.ARMOR) != null)
                husk.getAttribute(Attributes.ARMOR).setBaseValue(config.desertarmor);

            if (husk.getAttribute(Attributes.ATTACK_DAMAGE) != null)
                husk.getAttribute(Attributes.ATTACK_DAMAGE)
                        .setBaseValue(
                                husk.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue()
                                        * config.desertDamageBonus
                        );

            husk.setHealth(husk.getMaxHealth());
        }
    }

    @SubscribeEvent
    public void onDamage(LivingDamageEvent.Post event) {

//        // Reinforcement logic (existing)
//        if (event.getEntity() instanceof Husk husk &&
//                husk.level() instanceof ServerLevel level) {
//
//            HuskConfig config = ModConfigs.getHusk();
//
//            ReinforcementUtil.trySpawnReinforcement(
//                    husk,
//                    level,
//                    config.reinforcementChance,
//                    4
//            );
//        }

        // Mark target for hunger fix
        if (event.getSource().getEntity() instanceof Husk &&
                event.getEntity() instanceof LivingEntity target) {

            target.getPersistentData().putBoolean(HUNGER_TAG, true);
        }
    }

    @SubscribeEvent
    public void onEntityTick(EntityTickEvent.Post event) {

        if (event.getEntity() instanceof LivingEntity target &&
                target.getPersistentData().getBoolean(HUNGER_TAG)) {

            target.getPersistentData().remove(HUNGER_TAG);

            HuskConfig config = ModConfigs.getHusk();

            if (target.hasEffect(MobEffects.HUNGER))
                target.removeEffect(MobEffects.HUNGER);

            if (config.enableHunger) {

                float difficulty = target.level()
                        .getCurrentDifficultyAt(target.blockPosition())
                        .getEffectiveDifficulty();

                int duration = (int) (config.hungerDuration * difficulty);

                target.addEffect(new MobEffectInstance(
                        MobEffects.HUNGER,
                        duration,
                        config.hungerAmplifier
                ));
            }
        }

        // Husk Water Conversion
        if (!(event.getEntity() instanceof Husk husk))
            return;

        if (!(husk.level() instanceof ServerLevel level))
            return;

        HuskConfig config = ModConfigs.getHusk();

        if (!config.convertInWater)
            return;

        var data = husk.getPersistentData();

        if (husk.isInWaterRainOrBubble()) {

            int timer = data.getInt("mbc_water_timer");
            timer++;

            if (timer >= config.waterConversionTime) {

                Zombie zombie = EntityType.ZOMBIE.create(level, EntitySpawnReason.CONVERSION);

                if (zombie != null) {

                    zombie.moveTo(
                            husk.getX(),
                            husk.getY(),
                            husk.getZ(),
                            husk.getYRot(),
                            husk.getXRot()
                    );

                    zombie.finalizeSpawn(
                            level,
                            level.getCurrentDifficultyAt(zombie.blockPosition()),
                            EntitySpawnReason.CONVERSION,
                            null
                    );

                    level.addFreshEntity(zombie);

                    husk.discard();

                    if (!husk.isSilent()) {
                        level.levelEvent(null, 1041, husk.blockPosition(), 0);
                    }
                }

                data.putInt("mbc_water_timer", 0);
                return;
            }

            data.putInt("mbc_water_timer", timer);

        } else {
            data.putInt("mbc_water_timer", 0);
        }
    }
    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        HuskConfig config = ModConfigs.getHusk();

        XPUtil.applyXpIfInstance(
                event,
                Husk.class,
                config.xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Husk husk))
            return;

        if (!(husk.level() instanceof ServerLevel level))
            return;

        HuskConfig config = ModConfigs.getHusk();

        LootUtil.applyLootMultiplier(
                event,
                level,
                husk,
                config.lootMultiplier
        );
    }
}