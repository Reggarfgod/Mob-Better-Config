package com.reggarf.mods.mob_better_config.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

public class BossUtil {

    private static final String BOSS_TAG = "mob_better_config_boss";
    private static final String BOSS_XP = "mob_better_config_boss_xp";

    private static final double DEFAULT_XP_MULTIPLIER = 5.0D;

    public static void tryApplyBoss(
            LivingEntity entity,
            boolean enableBossMode,
            boolean forceAllBoss,
            double bossChance,
            double healthMultiplier,
            double damageMultiplier,
            boolean glowing,
            boolean customName,
            double bossXpMultiplier
    ) {

        if (!enableBossMode)
            return;

        if (entity.getPersistentData().getBoolean(BOSS_TAG))
            return;

        boolean makeBoss = forceAllBoss ||
                entity.getRandom().nextDouble() < bossChance;

        if (!makeBoss)
            return;

        applyBoss(entity, healthMultiplier, damageMultiplier,
                glowing, customName, bossXpMultiplier);
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
                DEFAULT_XP_MULTIPLIER
        );
    }

    private static void applyBoss(
            LivingEntity entity,
            double healthMultiplier,
            double damageMultiplier,
            boolean glowing,
            boolean customName,
            double xpMultiplier
    ) {

        entity.getPersistentData().putBoolean(BOSS_TAG, true);
        entity.getPersistentData().putDouble(BOSS_XP, xpMultiplier);

        if (entity.getAttribute(Attributes.MAX_HEALTH) != null) {

            double baseHealth = entity.getAttribute(Attributes.MAX_HEALTH).getBaseValue();
            double newHealth = baseHealth * healthMultiplier;

            entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(newHealth);
            entity.setHealth((float) newHealth);
        }

        if (entity.getAttribute(Attributes.ATTACK_DAMAGE) != null) {

            double baseDamage = entity.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
            entity.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(baseDamage * damageMultiplier);
        }

        if (glowing)
            entity.setGlowingTag(true);

        if (customName)
            entity.setCustomName(Component.literal("§cBoss " + entity.getName().getString()));
    }

    public static boolean isBoss(LivingEntity entity) {
        return entity.getPersistentData().getBoolean(BOSS_TAG);
    }

    @SubscribeEvent
    public static void onExperienceDrop(LivingExperienceDropEvent event) {

        if (!(event.getEntity() instanceof LivingEntity living))
            return;

        if (!living.getPersistentData().getBoolean(BOSS_TAG))
            return;

        double multiplier = living.getPersistentData().getDouble(BOSS_XP);

        if (multiplier <= 0)
            multiplier = DEFAULT_XP_MULTIPLIER;

        int newXp = (int) (event.getDroppedExperience() * multiplier);
        event.setDroppedExperience(newXp);
    }
}