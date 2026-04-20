package com.reggarf.mods.mob_better_config.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BossUtil {

    private static final double DEFAULT_SCALE = 1.5;
    private static final String TAG_BOSS = "mbc_boss";
    private static final String TAG_XP = "mbc_xp=";
    private static final String TAG_LOOT = "mbc_loot=";
    private static final String TAG_TIER = "mbc_tier=";

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

        if (!enableBossMode) return;
        if (isBoss(entity)) return;

        boolean makeBoss = forceAllBoss || entity.getRandom().nextDouble() < bossChance;
        if (!makeBoss) return;

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

        if (!(entity instanceof Mob)) return;

        entity.getTags().removeIf(tag ->
                tag.startsWith("mbc_")
        );

        entity.addTag(TAG_BOSS);
        entity.addTag(TAG_TIER + tier.name());
        entity.addTag(TAG_XP + xpMultiplier);
        entity.addTag(TAG_LOOT + lootMultiplier);

        // HEALTH
        AttributeInstance maxHealth = entity.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth != null) {
            double newHealth = maxHealth.getBaseValue() * healthMultiplier;
            maxHealth.setBaseValue(newHealth);
            entity.setHealth((float) newHealth);
        }

        // DAMAGE
        AttributeInstance attackDamage = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attackDamage != null) {
            attackDamage.setBaseValue(
                    attackDamage.getBaseValue() * damageMultiplier
            );
        }

        // SCALE
        AttributeInstance scale = entity.getAttribute(Attributes.SCALE);
        if (scale != null) {
            scale.setBaseValue(DEFAULT_SCALE + tier.healthMul * 0.3);
        }

        // GLOWING
        if (glowing) {
            entity.setGlowingTag(true);

            if (!entity.level().isClientSide()) {
                if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {

                    var server = serverLevel.getServer();
                    var scoreboard = server.getScoreboard();

                    String teamName = "mbc_glow_" + tier.name().toLowerCase();

                    var team = scoreboard.getPlayerTeam(teamName);

                    if (team == null) {
                        team = scoreboard.addPlayerTeam(teamName);
                        team.setColor(tier.color);
                    }
                    scoreboard.addPlayerToTeam(entity.getScoreboardName(), team);
                }
            }
        }

        // NAME
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
        return entity.getTags().contains(TAG_BOSS);
    }

    public static double getXpMultiplier(LivingEntity entity) {
        for (String tag : entity.getTags()) {
            if (tag.startsWith(TAG_XP)) {
                try {
                    return Double.parseDouble(tag.split("=")[1]);
                } catch (Exception ignored) {}
            }
        }
        return 1.0;
    }

    public static double getLootMultiplier(LivingEntity entity) {
        for (String tag : entity.getTags()) {
            if (tag.startsWith(TAG_LOOT)) {
                try {
                    return Double.parseDouble(tag.split("=")[1]);
                } catch (Exception ignored) {}
            }
        }
        return 1.0;
    }

    public static BossTier getTier(LivingEntity entity) {
        for (String tag : entity.getTags()) {
            if (tag.startsWith(TAG_TIER)) {
                try {
                    return BossTier.valueOf(tag.split("=")[1]);
                } catch (Exception ignored) {}
            }
        }
        return BossTier.COMMON;
    }
}