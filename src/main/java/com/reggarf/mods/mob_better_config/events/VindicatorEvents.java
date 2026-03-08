package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.VindicatorConfig;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;

import net.minecraft.world.entity.monster.illager.Vindicator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

public class VindicatorEvents {

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Vindicator vindicator))
            return;

        if (!(vindicator.level() instanceof ServerLevel level))
            return;

        VindicatorConfig config = ModConfigs.getVindicator();

        if (NbtUtil.getBooleanSafe(vindicator.getPersistentData(),"mob_better_config_spawned"))
            return;

        applyConfig(vindicator);
        BossUtil.tryApplyBoss(
                vindicator,
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
        // Johnny Mode
        if (config.enableJohnnyMode) {
            vindicator.setCustomName(
                    net.minecraft.network.chat.Component.literal("Johnny"));
            vindicator.setPersistenceRequired();
        }

        for (int i = 1; i < config.spawnMultiplier; i++) {

            Vindicator extra = new Vindicator(EntityType.VINDICATOR, level);

            extra.snapTo(
                    vindicator.getX(),
                    vindicator.getY(),
                    vindicator.getZ(),
                    vindicator.getYRot(),
                    vindicator.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);

            level.addFreshEntity(extra);
        }
    }


    private void applyConfig(Vindicator vindicator) {

        VindicatorConfig config = ModConfigs.getVindicator();
        if (config.CustomName) {
            MobNameUtil.applyRandomName(vindicator);
        }
        if (vindicator.getAttribute(Attributes.MAX_HEALTH) != null)
            vindicator.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (vindicator.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            vindicator.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage);

        if (vindicator.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            vindicator.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);

        if (vindicator.getAttribute(Attributes.FOLLOW_RANGE) != null)
            vindicator.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(config.followRange);

        if (vindicator.getAttribute(Attributes.ARMOR) != null)
            vindicator.getAttribute(Attributes.ARMOR).setBaseValue(config.armor);

        if (vindicator.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            vindicator.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(config.knockbackResistance);

        if (vindicator.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            vindicator.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(config.attackKnockback);

        if (vindicator.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            vindicator.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(config.reinforcementChance);

        vindicator.setHealth(config.health);

        if (config.fireImmune)
            vindicator.setRemainingFireTicks(0);

        if (config.glowing)
            vindicator.setGlowingTag(true);
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Vindicator vindicator))
            return;

        if (!(vindicator.level() instanceof ServerLevel level))
            return;

        VindicatorConfig config = ModConfigs.getVindicator();

        LootUtil.applyLootMultiplier(
                event,
                level,
                vindicator,
                config.lootMultiplier
        );
    }
}