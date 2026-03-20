package com.reggarf.mods.mob_better_config.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BossUtil {

    public static final String BOSS_TAG = "mob_better_config_boss";
    public static final String TIER_TAG = "mob_better_config_tier_";

    public static final String XP_TAG = "mob_better_config_xp_";
    public static final String LOOT_TAG = "mob_better_config_loot_";

    private static final double DEFAULT_SCALE = 1.5;

    public enum BossTier {
        COMMON(1.0, 1.0, 1.0, 1.0, ChatFormatting.WHITE),
        RARE(1.3, 1.2, 1.5, 1.3, ChatFormatting.AQUA),
        EPIC(1.7, 1.4, 2.0, 1.6, ChatFormatting.DARK_PURPLE),
        LEGENDARY(2.5, 1.8, 3.0, 2.2, ChatFormatting.GOLD),
        MYTHICAL(4.0, 2.5, 5.0, 3.0, ChatFormatting.RED);

        public final double healthMul;
        public final double damageMul;
        public final double xpMul;
        public final double lootMul;
        public final ChatFormatting color;

        BossTier(double h, double d, double xp, double loot, ChatFormatting color) {
            this.healthMul = h;
            this.damageMul = d;
            this.xpMul = xp;
            this.lootMul = loot;
            this.color = color;
        }
    }

    public static void tryApplyBoss(
            LivingEntity entity,
            boolean enableBossMode,
            boolean forceAllBoss,
            double bossChance,
            double healthMultiplier,
            double damageMultiplier,
            boolean glowing,
            boolean customName,
            double xpMultiplier,
            double lootMultiplier
    ) {

        if (!enableBossMode)
            return;

        if (isBoss(entity))
            return;

        boolean makeBoss;

        if (forceAllBoss) {
            makeBoss = true;
        } else {
            makeBoss = entity.getRandom().nextDouble() < bossChance;
        }

        if (!makeBoss)
            return;

        BossTier tier = rollTier(entity);

        applyBoss(entity,
                healthMultiplier * tier.healthMul,
                damageMultiplier * tier.damageMul,
                glowing,
                customName,
                xpMultiplier * tier.xpMul,
                lootMultiplier * tier.lootMul,
                tier);
    }

    private static BossTier rollTier(LivingEntity entity) {
        double r = entity.getRandom().nextDouble();

        if (r < 0.50) return BossTier.COMMON;
        if (r < 0.75) return BossTier.RARE;
        if (r < 0.90) return BossTier.EPIC;
        if (r < 0.98) return BossTier.LEGENDARY;
        return BossTier.MYTHICAL;
    }

    private static void applyBoss(
            LivingEntity entity,
            double healthMultiplier,
            double damageMultiplier,
            boolean glowing,
            boolean customName,
            double xpMultiplier,
            double lootMultiplier,
            BossTier tier
    ) {

        entity.addTag(BOSS_TAG);
        entity.addTag(TIER_TAG + tier.name());

        entity.addTag(XP_TAG + xpMultiplier);
        entity.addTag(LOOT_TAG + lootMultiplier);

        AttributeInstance maxHealth = entity.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth != null) {
            double newHealth = maxHealth.getBaseValue() * healthMultiplier;
            maxHealth.setBaseValue(newHealth);
            entity.setHealth((float) newHealth);
        }

        AttributeInstance attackDamage = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attackDamage != null) {
            attackDamage.setBaseValue(
                    attackDamage.getBaseValue() * damageMultiplier
            );
        }

        AttributeInstance scale = entity.getAttribute(Attributes.SCALE);
        if (scale != null) {
            scale.setBaseValue(DEFAULT_SCALE + tier.healthMul * 0.3);
        }

        if (glowing) {
            entity.setGlowingTag(true);

            if (!entity.level().isClientSide) {
                var server = entity.getServer();
                if (server != null) {
                    var scoreboard = server.getScoreboard();

                    String teamName = "mbc_glow_" + tier.name().toLowerCase();

                    var team = scoreboard.getPlayerTeam(teamName);

                    if (team == null) {
                        team = scoreboard.addPlayerTeam(teamName);
                        team.setColor(tier.color);
                    }

                    scoreboard.addPlayerToTeam(entity.getStringUUID(), team);
                }
            }
        }

        if (customName) {
            entity.setCustomName(
                    Component.literal(formatTier(tier) + " Boss " + entity.getName().getString())
                            .withStyle(tier.color)
            );
            entity.setCustomNameVisible(true);
        }
    }



    private static String formatTier(BossTier tier) {
        String n = tier.name().toLowerCase();
        return Character.toUpperCase(n.charAt(0)) + n.substring(1);
    }

    public static boolean isBoss(LivingEntity entity) {
        return entity.getTags().contains(BOSS_TAG);
    }

    public static double getXpMultiplier(LivingEntity entity) {
        for (String tag : entity.getTags()) {
            if (tag.startsWith(XP_TAG)) {
                try {
                    return Double.parseDouble(tag.replace(XP_TAG, ""));
                } catch (Exception ignored) {}
            }
        }
        return 5.0;
    }

    public static double getLootMultiplier(LivingEntity entity) {
        for (String tag : entity.getTags()) {
            if (tag.startsWith(LOOT_TAG)) {
                try {
                    return Double.parseDouble(tag.replace(LOOT_TAG, ""));
                } catch (Exception ignored) {}
            }
        }
        return 2.0;
    }
}