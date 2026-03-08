package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.HoglinConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.PiglinBruteConfig;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;

import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingConversionEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class PiglinBruteEvents {

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof PiglinBrute piglinBrute))
            return;

        if (!(piglinBrute.level() instanceof ServerLevel level))
            return;

        PiglinBruteConfig config = ModConfigs.getPiglinBrute();

        if (NbtUtil.getBooleanSafe(piglinBrute.getPersistentData(),("mob_better_config_spawned")))
            return;

        applyConfig(piglinBrute, config);

        BossUtil.tryApplyBoss(
                piglinBrute,
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

        // Spawn Multiplier
        for (int i = 1; i < config.spawnMultiplier; i++) {

            PiglinBrute extra = new PiglinBrute(EntityType.PIGLIN_BRUTE, level);

            extra.snapTo(
                    piglinBrute.getX(),
                    piglinBrute.getY(),
                    piglinBrute.getZ(),
                    piglinBrute.getYRot(),
                    piglinBrute.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);

            applyConfig(extra, config);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(PiglinBrute brute, PiglinBruteConfig config) {

        if (config.customName)
            MobNameUtil.applyRandomName(brute);

        if (brute.getAttribute(Attributes.MAX_HEALTH) != null)
            brute.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (brute.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            brute.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage);

        if (brute.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            brute.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);


        if (brute.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            brute.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                    .setBaseValue(config.reinforcementChance);

        if (brute.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            brute.getAttribute(Attributes.ATTACK_KNOCKBACK)
                    .setBaseValue(config.attackKnockback);

        if (brute.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            brute.getAttribute(Attributes.KNOCKBACK_RESISTANCE)
                    .setBaseValue(config.knockbackResistance);



        if (brute.getAttribute(Attributes.ARMOR) != null)
            brute.getAttribute(Attributes.ARMOR)
                    .setBaseValue(config.armor);


        brute.setHealth((float) config.health);

        if (config.glowing)
            brute.setGlowingTag(true);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        PiglinBruteConfig config = ModConfigs.getPiglinBrute();

        XPUtil.applyXpIfInstance(
                event,
                PiglinBrute.class,
                config.xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof PiglinBrute brute))
            return;

        if (!(brute.level() instanceof ServerLevel level))
            return;

        PiglinBruteConfig config = ModConfigs.getPiglinBrute();

        LootUtil.applyLootMultiplier(
                event,
                level,
                brute,
                config.lootMultiplier
        );
    }
    @SubscribeEvent
    public void onConvertPre(LivingConversionEvent.Pre event) {

        if (!(event.getEntity() instanceof PiglinBrute brute))
            return;

        PiglinBruteConfig config = ModConfigs.getPiglinBrute();

        if (!config.disableZombification)
            return;

        event.setCanceled(true);
    }
    @SubscribeEvent
    public void onConvert(LivingConversionEvent.Post event) {

        if (!(event.getEntity() instanceof PiglinBrute brute))
            return;

        if (!(event.getOutcome() instanceof ZombifiedPiglin zombified))
            return;

        if (!(zombified.level() instanceof ServerLevel))
            return;

        // Copy attributes
        copyAttribute(brute, zombified, Attributes.MAX_HEALTH);
        copyAttribute(brute, zombified, Attributes.ATTACK_DAMAGE);
        copyAttribute(brute, zombified, Attributes.MOVEMENT_SPEED);
        copyAttribute(brute, zombified, Attributes.ARMOR);
        copyAttribute(brute, zombified, Attributes.KNOCKBACK_RESISTANCE);
        copyAttribute(brute, zombified, Attributes.SCALE);

        // Preserve current health
        zombified.setHealth(brute.getHealth());

        // Preserve glowing
        if (brute.isCurrentlyGlowing())
            zombified.setGlowingTag(true);

        // Preserve custom name
        if (brute.hasCustomName()) {
            zombified.setCustomName(brute.getCustomName());
            zombified.setCustomNameVisible(brute.isCustomNameVisible());
        }

        // Preserve boss state (persistent data)
        zombified.getPersistentData().merge(brute.getPersistentData());
    }

    private static void copyAttribute(LivingEntity from, LivingEntity to, Holder<Attribute> attribute) {

        var fromAttr = from.getAttribute(attribute);
        var toAttr = to.getAttribute(attribute);

        if (fromAttr == null || toAttr == null)
            return;

        toAttr.setBaseValue(fromAttr.getBaseValue());
    }
}