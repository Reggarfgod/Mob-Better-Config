package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.DrownedConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DrownedEvents {

    public static boolean onSpawnCheck(Drowned drowned) {

        DrownedConfig config = ModConfigs.getDrowned();

        if (!config.allowDaySpawn && drowned.level().isDay())
            return false;

        if (config.requireDeepWater) {

            int seaLevel = drowned.level().getSeaLevel();
            int minY = seaLevel - config.deepWaterOffset;

            if (drowned.blockPosition().getY() >= minY)
                return false;
        }

        return true;
    }

    public static void onJoin(Drowned drowned, ServerLevel level) {
        if (CommonMobHandler.isInitialized(drowned))
            return;
        CommonMobHandler.markInitialized(drowned);

        DrownedConfig config = ModConfigs.getDrowned();
        applyConfig(drowned, config);
        applyEquipment(drowned, config);

        BossUtil.tryApplyBoss(
                drowned,
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

            Drowned extra = new Drowned(EntityType.DROWNED, level);

            extra.moveTo(
                    drowned.getX(),
                    drowned.getY(),
                    drowned.getZ(),
                    drowned.getYRot(),
                    drowned.getXRot()
            );

            extra.addTag("mob_better_config_spawned");

            applyConfig(extra, config);
            applyEquipment(extra, config);

            level.addFreshEntity(extra);
        }
    }

    public static float onDamage(Drowned drowned, float damage) {
        return damage * (float) ModConfigs.getDrowned().tridentDamageMultiplier;
    }

    public static void onTick(Drowned drowned) {

        if (drowned.level().isClientSide)
            return;

        DrownedConfig config = ModConfigs.getDrowned();

        if (!config.aggressiveSwimming)
            return;

        if (drowned.isInWater() && drowned.getTarget() != null) {

            drowned.setDeltaMovement(
                    drowned.getDeltaMovement().add(0.0D, config.swimUpBoost, 0.0D)
            );
        }

        DaylightBurnUtil.handleDaylightBurn(
                drowned,
                config.burnInDaylight,
                config.fireImmune,
                config.burnSeconds,
                true
        );
    }

    public static float onXP(float xp) {
        return xp * (float) ModConfigs.getDrowned().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, Drowned drowned) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                drowned,
                ModConfigs.getDrowned().lootMultiplier
        );
    }


    public static void onTridentSpawn(Drowned drowned, ThrownTrident trident) {

        if (drowned.level().isClientSide)
            return;

        if (drowned.getTarget() == null)
            return;

        DrownedConfig config = ModConfigs.getDrowned();

        trident.setDeltaMovement(0, 0, 0);

        double d0 = drowned.getTarget().getX() - drowned.getX();
        double d1 = drowned.getTarget().getY(0.3333333333333333) - trident.getY();
        double d2 = drowned.getTarget().getZ() - drowned.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);

        trident.shoot(
                d0,
                d1 + d3 * 0.2D,
                d2,
                (float) config.tridentVelocity,
                (float) config.tridentInaccuracy
        );
    }

    private static void applyConfig(Drowned drowned, DrownedConfig config) {
        RandomSource random = drowned.level().getRandom();

        DoorBreakUtil.handleDoorBreaking(
                drowned,
                config.canBreakDoors,
                config.doorBreakMode
        );

        if (config.CustomName)
            MobNameUtil.applyRandomName(drowned);

        if (drowned.getAttribute(Attributes.MAX_HEALTH) != null)
            drowned.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.health);

        if (drowned.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            drowned.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(config.attackDamage);

        if (drowned.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            drowned.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(config.movementSpeed);

        if (drowned.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            drowned.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(config.reinforcementChance);

        if (drowned.getAttribute(Attributes.ARMOR) != null)
            drowned.getAttribute(Attributes.ARMOR).setBaseValue(config.armor);

        if (drowned.getAttribute(Attributes.FOLLOW_RANGE) != null)
            drowned.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(config.followRange);

        if (drowned.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            drowned.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(config.knockbackResistance);

        if (drowned.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            drowned.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(config.attackKnockback);

        if (drowned.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            drowned.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(config.reinforcementChance);

        drowned.setHealth((float) config.health);
    }

    private static void applyEquipment(Drowned drowned, DrownedConfig config) {

        if (drowned.getMainHandItem().isEmpty()) {

            double roll = drowned.getRandom().nextDouble();

            if (roll < config.tridentChance)
                drowned.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.TRIDENT));
            else if (roll < config.tridentChance + config.fishingRodChance)
                drowned.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.FISHING_ROD));
        }

        if (drowned.getItemBySlot(EquipmentSlot.OFFHAND).isEmpty()) {

            if (drowned.getRandom().nextDouble() < config.nautilusShellChance) {

                drowned.setItemSlot(
                        EquipmentSlot.OFFHAND,
                        new ItemStack(Items.NAUTILUS_SHELL)
                );

                drowned.setGuaranteedDrop(EquipmentSlot.OFFHAND);
            }
        }
    }
}