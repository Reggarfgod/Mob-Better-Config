package com.reggarf.mods.mob_better_config.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

public class BossUtil {

    private static final String BOSS_TAG = "mob_better_config_boss";
    private static final String BOSS_XP = "mob_better_config_boss_xp";
    private static final String BOSS_LOOT = "mob_better_config_boss_loot";
    private static final String JOIN_MARK = "mob_better_config_processed";

    private static final double DEFAULT_XP_MULTIPLIER = 5.0D;
    private static final double DEFAULT_LOOT_MULTIPLIER = 2.0D;
    private static final double DEFAULT_SCALE = 1.5D;

    public static void tryApplyBoss(
            LivingEntity entity,
            boolean enableBossMode,
            boolean forceAllBoss,
            double bossChance,
            double healthMultiplier,
            double damageMultiplier,
            boolean glowing,
            boolean customName,
            double bossXpMultiplier,
            double bossLootMultiplier
    ) {

        if (!enableBossMode)
            return;

        var data = entity.getPersistentData();

        if (NbtUtil.getBooleanSafe(data, JOIN_MARK))
            return;

        data.putBoolean(JOIN_MARK, true);

        if (NbtUtil.getBooleanSafe(data, BOSS_TAG))
            return;

        boolean makeBoss;

        if (forceAllBoss) {
            makeBoss = true;
        } else {
            makeBoss = entity.getRandom().nextDouble() < bossChance;
        }

        if (!makeBoss)
            return;

        applyBoss(entity,
                healthMultiplier,
                damageMultiplier,
                glowing,
                customName,
                bossXpMultiplier,
                bossLootMultiplier);
    }

    public static void tryApplyBoss(
            LivingEntity entity,
            boolean enableBossMode,
            boolean forceAllBoss,
            double bossChance,
            double healthMultiplier,
            double damageMultiplier,
            boolean glowing,
            boolean customName
    ) {
        tryApplyBoss(entity,
                enableBossMode,
                forceAllBoss,
                bossChance,
                healthMultiplier,
                damageMultiplier,
                glowing,
                customName,
                DEFAULT_XP_MULTIPLIER,
                DEFAULT_LOOT_MULTIPLIER
        );
    }

    private static void applyBoss(
            LivingEntity entity,
            double healthMultiplier,
            double damageMultiplier,
            boolean glowing,
            boolean customName,
            double xpMultiplier,
            double lootMultiplier
    ) {

        // Mark as boss
        entity.getPersistentData().putBoolean(BOSS_TAG, true);
        entity.getPersistentData().putDouble(BOSS_XP, xpMultiplier);
        entity.getPersistentData().putDouble(BOSS_LOOT, lootMultiplier);

        // Health
        AttributeInstance maxHealth = entity.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth != null) {
            double newHealth = maxHealth.getBaseValue() * healthMultiplier;
            maxHealth.setBaseValue(newHealth);
            entity.setHealth((float) newHealth);
        }

        // Damage
        AttributeInstance attackDamage = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attackDamage != null) {
            attackDamage.setBaseValue(
                    attackDamage.getBaseValue() * damageMultiplier
            );
        }

        // Scale
        AttributeInstance scale = entity.getAttribute(Attributes.SCALE);
        if (scale != null) {
            scale.setBaseValue(DEFAULT_SCALE);
        }

        // Effects
        if (glowing) {
            entity.setGlowingTag(true);
        }

        if (customName) {
            entity.setCustomName(
                    Component.literal("Boss " + entity.getName().getString())
                            .withStyle(ChatFormatting.RED)
            );
            entity.setCustomNameVisible(false);
        }
    }

    public static boolean isBoss(LivingEntity entity) {
        return NbtUtil.getBooleanSafe(entity.getPersistentData(), BOSS_TAG);
    }

    @SubscribeEvent
    public static void onExperienceDrop(LivingExperienceDropEvent event) {

        LivingEntity living = event.getEntity();

        if (!NbtUtil.getBooleanSafe(living.getPersistentData(), BOSS_TAG))
            return;

        double multiplier = NbtUtil.getDoubleSafe(living.getPersistentData(), BOSS_XP);

        if (multiplier <= 0)
            multiplier = DEFAULT_XP_MULTIPLIER;

        int newXp = (int) (event.getDroppedExperience() * multiplier);
    }
    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {

        LivingEntity entity = event.getEntity();

        if (!(entity.level() instanceof ServerLevel level))
            return;

        if (!NbtUtil.getBooleanSafe(entity.getPersistentData(), BOSS_TAG))
            return;

        double multiplier = NbtUtil.getDoubleSafe(entity.getPersistentData(), BOSS_LOOT);

        if (multiplier <= 1.0D)
            multiplier = DEFAULT_LOOT_MULTIPLIER;

        LootUtil.applyLootMultiplier(event, level, entity, multiplier);
    }
}