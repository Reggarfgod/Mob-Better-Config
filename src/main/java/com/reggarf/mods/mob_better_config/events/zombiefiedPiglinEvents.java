package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.ZombifiedPiglinConfig;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.ZombifiedPiglin;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

public class zombiefiedPiglinEvents {

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof ZombifiedPiglin piglin))
            return;

        if (!(piglin.level() instanceof ServerLevel level))
            return;

        var data = piglin.getPersistentData();

        if (data.getBoolean("mob_better_config_processed"))
            return;

        if (data.getBoolean("mob_better_config_converted"))
            return;

        if (BossUtil.isBoss(piglin))
            return;

        ZombifiedPiglinConfig config = ModConfigs.getZombifiedPiglin();

        applyConfig(piglin, config);

        BossUtil.tryApplyBoss(
                piglin,
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

        data.putBoolean("mob_better_config_processed", true);

        // Spawn multiplier
        for (int i = 1; i < config.spawnMultiplier; i++) {

            ZombifiedPiglin extra =
                    EntityType.ZOMBIFIED_PIGLIN.create(level, EntitySpawnReason.NATURAL);

            if (extra == null)
                continue;

            extra.moveTo(
                    piglin.getX(),
                    piglin.getY(),
                    piglin.getZ(),
                    piglin.getYRot(),
                    piglin.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);
            extra.getPersistentData().putBoolean("mob_better_config_processed", true);

            applyConfig(extra, config);

            level.addFreshEntity(extra);
        }
    }
    private void applyConfig(ZombifiedPiglin piglin, ZombifiedPiglinConfig config) {

        if (config.customName)
            MobNameUtil.applyRandomName(piglin);

        // MAX HEALTH
        if (piglin.getAttribute(Attributes.MAX_HEALTH) != null) {

            piglin.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

            // Important: update current health
            piglin.setHealth(piglin.getMaxHealth());
        }

        // ARMOR
        if (piglin.getAttribute(Attributes.ARMOR) != null)
            piglin.getAttribute(Attributes.ARMOR)
                    .setBaseValue(config.armor);

        // ATTACK DAMAGE
        if (piglin.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            piglin.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage);

        // MOVEMENT SPEED
        if (piglin.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            piglin.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);

        // KNOCKBACK RESISTANCE
        if (piglin.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            piglin.getAttribute(Attributes.KNOCKBACK_RESISTANCE)
                    .setBaseValue(config.knockbackResistance);

        // ATTACK KNOCKBACK
        if (piglin.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            piglin.getAttribute(Attributes.ATTACK_KNOCKBACK)
                    .setBaseValue(config.attackKnockback);

        // REINFORCEMENTS
        if (piglin.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            piglin.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                    .setBaseValue(config.reinforcementChance);

        // Glow
        if (config.glowing)
            piglin.setGlowingTag(true);

        // Baby chance
        if (config.allowBaby && piglin.level().random.nextDouble() < config.babyChance)
            piglin.setBaby(true);
    }

    @SubscribeEvent
    public void onDamage(LivingDamageEvent.Pre event) {

        if (!(event.getSource().getEntity() instanceof ZombifiedPiglin))
            return;

        ZombifiedPiglinConfig config = ModConfigs.getZombifiedPiglin();

        event.setNewDamage(
                event.getNewDamage() * (float) config.attackDamageMultiplier
        );
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        ZombifiedPiglinConfig config = ModConfigs.getZombifiedPiglin();

        XPUtil.applyXpIfInstance(
                event,
                ZombifiedPiglin.class,
                config.xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof ZombifiedPiglin piglin))
            return;

        if (!(piglin.level() instanceof ServerLevel level))
            return;

        ZombifiedPiglinConfig config = ModConfigs.getZombifiedPiglin();

        LootUtil.applyLootMultiplier(
                event,
                level,
                piglin,
                config.lootMultiplier
        );
    }
}