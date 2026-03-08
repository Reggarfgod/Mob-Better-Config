package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.CaveSpiderConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.CaveSpider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class CaveSpiderEvents {

    private static final String POISON_TAG = "mob_better_config_poison_flag";


    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof CaveSpider spider))
            return;

        if (!(spider.level() instanceof ServerLevel level))
            return;

        CaveSpiderConfig config = ModConfigs.getCaveSpider();

        if (NbtUtil.getBooleanSafe(spider.getPersistentData(), "mob_better_config_spawned"))
            return;

        applyConfig(spider);
        BossUtil.tryApplyBoss(
                spider,
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

            CaveSpider extra = new CaveSpider(EntityType.CAVE_SPIDER, level);

            extra.snapTo(
                    spider.getX(),
                    spider.getY(),
                    spider.getZ(),
                    spider.getYRot(),
                    spider.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(CaveSpider spider) {

        CaveSpiderConfig config = ModConfigs.getCaveSpider();
        if (config.CustomName) {
            MobNameUtil.applyRandomName(spider);
        }
        if (spider.getAttribute(Attributes.MAX_HEALTH) != null)
            spider.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.health);

        if (spider.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            spider.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(config.attackDamage);

        if (spider.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            spider.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(config.movementSpeed);

        if (spider.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            spider.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(config.reinforcementChance);

        if (spider.getAttribute(Attributes.ARMOR) != null)
            spider.getAttribute(Attributes.ARMOR).setBaseValue(config.armor);

        if (spider.getAttribute(Attributes.FOLLOW_RANGE) != null)
            spider.getAttribute(Attributes.FOLLOW_RANGE)
                    .setBaseValue(config.followRange);

        if (spider.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            spider.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(config.knockbackResistance);

        if (spider.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            spider.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(config.attackKnockback);

        spider.setHealth(config.health);

        if (config.fireImmune)
            spider.setRemainingFireTicks(0);

        if (config.glowing)
            spider.setGlowingTag(true);
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof LivingEntity target))
            return;

        if (!NbtUtil.getBooleanSafe(target.getPersistentData(), POISON_TAG))
            return;


        target.getPersistentData().remove(POISON_TAG);

        CaveSpiderConfig config = ModConfigs.getCaveSpider();

        // Remove vanilla poison first
        if (target.hasEffect(MobEffects.POISON))
            target.removeEffect(MobEffects.POISON);

        if (!config.enablePoison)
            return;

        int duration = (int)(140 * config.poisonDurationMultiplier); // default ~7 sec
        int amplifier = (int)Math.floor(config.poisonAmplifierMultiplier - 1);
        amplifier = Math.max(0, amplifier);

        target.addEffect(new MobEffectInstance(
                MobEffects.POISON,
                duration,
                amplifier
        ));
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof CaveSpider spider))
            return;

        if (!(spider.level() instanceof ServerLevel level))
            return;

        CaveSpiderConfig config = ModConfigs.getCaveSpider();

        LootUtil.applyLootMultiplier(
                event,
                level,
                spider,
                config.lootMultiplier
        );
    }
}