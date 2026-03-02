package com.reggarf.mods.mob_better_config.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

public class DragonMinionsUtil {

    private static final String DRAGON_MINION_TAG = "mob_better_config_dragon_minion";

    public static void trySpawnDragonMinion(
            Mob summoner,
            ServerLevel level,
            boolean enabled,
            double spawnChance,
            double healthMultiplier,
            double damageMultiplier,
            double scaleMultiplier,
            boolean customName
    ) {

        if (!enabled)
            return;

        if (level.isClientSide())
            return;

        RandomSource random = level.getRandom();

        if (random.nextDouble() > spawnChance)
            return;

        EnderDragon dragon = EntityType.ENDER_DRAGON.create(level);
        if (dragon == null)
            return;

        dragon.moveTo(
                summoner.getX(),
                summoner.getY() + 10,
                summoner.getZ(),
                0F,
                0F
        );

        dragon.setDragonFight(null);
        applyStats(dragon, healthMultiplier, damageMultiplier, scaleMultiplier);
        if (customName) {
            dragon.setCustomName(
                    Component.literal("Minion " + dragon.getName().getString())
                            .withStyle(ChatFormatting.DARK_PURPLE)
            );
            dragon.setCustomNameVisible(false);
        }

        dragon.getPersistentData().putBoolean(DRAGON_MINION_TAG, true);

        level.addFreshEntity(dragon);
    }

    private static void applyStats(
            EnderDragon dragon,
            double healthMultiplier,
            double damageMultiplier,
            double scaleMultiplier
    ) {

        AttributeInstance maxHealth = dragon.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth != null) {
            double newHealth = maxHealth.getBaseValue() * healthMultiplier;
            maxHealth.setBaseValue(newHealth);
            dragon.setHealth((float) newHealth);
        }

        AttributeInstance attackDamage = dragon.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attackDamage != null) {
            attackDamage.setBaseValue(
                    attackDamage.getBaseValue() * damageMultiplier
            );
        }

        AttributeInstance scale = dragon.getAttribute(Attributes.SCALE);
        if (scale != null) {
            scale.setBaseValue(scaleMultiplier);
        }
    }
}