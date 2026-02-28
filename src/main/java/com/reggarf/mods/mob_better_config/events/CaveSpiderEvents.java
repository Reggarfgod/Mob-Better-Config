package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.CaveSpiderConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.util.LootUtil;
import com.reggarf.mods.mob_better_config.util.ReinforcementUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.CaveSpider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class CaveSpiderEvents {

    private static final String POISON_TAG = "mob_better_config_poison_flag";

    // =========================
    // SPAWN LOGIC
    // =========================
    @SubscribeEvent
    public void onJoin(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof CaveSpider spider))
            return;

        if (!(spider.level() instanceof ServerLevel level))
            return;

        CaveSpiderConfig config = ModConfigs.getCaveSpider();

        if (spider.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(spider);

        for (int i = 1; i < config.spawnMultiplier; i++) {

            CaveSpider extra = new CaveSpider(EntityType.CAVE_SPIDER, level);

            extra.moveTo(
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

    // =========================
    // APPLY ATTRIBUTES
    // =========================
    private void applyConfig(CaveSpider spider) {

        CaveSpiderConfig config = ModConfigs.getCaveSpider();

        if (spider.getAttribute(Attributes.MAX_HEALTH) != null)
            spider.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.health);

        if (spider.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            spider.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(config.attackDamage);

        if (spider.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            spider.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(config.movementSpeed);

        spider.setHealth(config.health);

        if (config.fireImmune)
            spider.setRemainingFireTicks(0);

        if (config.glowing)
            spider.setGlowingTag(true);
    }

    // =========================
    // DAMAGE EVENT
    // =========================
    @SubscribeEvent
    public void onDamage(LivingDamageEvent.Post event) {

        // Reinforcement when spider is hurt
        if (event.getEntity() instanceof CaveSpider spider &&
                spider.level() instanceof ServerLevel level) {

            CaveSpiderConfig config = ModConfigs.getCaveSpider();

            ReinforcementUtil.trySpawnReinforcement(
                    spider,
                    level,
                    config.reinforcementChance,
                    4
            );
        }

        // Mark target for poison processing
        if (event.getSource().getEntity() instanceof CaveSpider &&
                event.getEntity() instanceof LivingEntity target) {

            target.getPersistentData().putBoolean(POISON_TAG, true);
        }
    }

    // =========================
    // POISON CONTROL (NEXT TICK FIX)
    // =========================
    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof LivingEntity target))
            return;

        if (!target.getPersistentData().getBoolean(POISON_TAG))
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

    // =========================
    // LOOT MULTIPLIER
    // =========================
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