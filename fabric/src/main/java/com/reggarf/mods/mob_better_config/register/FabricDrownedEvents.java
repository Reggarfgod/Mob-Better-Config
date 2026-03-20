package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.DrownedConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.util.*;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FabricDrownedEvents {

    public static void register() {

        // When entity loads
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (!(entity instanceof Drowned drowned))
                return;

            if (!(level instanceof ServerLevel serverLevel))
                return;

            DrownedConfig config = ModConfigs.getDrowned();

            // Prevent duplicate spawn multiplier
            if (drowned.getTags().contains("mob_better_config_spawned"))
                return;
            drowned.addTag("mob_better_config_spawned");

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

            // Spawn multiplier
            for (int i = 1; i < config.spawnMultiplier; i++) {

                Drowned extra = new Drowned(EntityType.DROWNED, serverLevel);

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

                serverLevel.addFreshEntity(extra);
            }
        });

        // Tick event

                ServerTickEvents.END_WORLD_TICK.register((ServerLevel level) -> {

                    for (Entity entity : level.getAllEntities()) {

                        if (!(entity instanceof Drowned drowned))
                            continue;

                        DrownedConfig config = ModConfigs.getDrowned();

                        if (!config.aggressiveSwimming)
                            continue;

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
                });


        // Modify trident velocity when spawned
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (!(entity instanceof ThrownTrident trident))
                return;

            if (!(trident.getOwner() instanceof Drowned drowned))
                return;

            if (drowned.level().isClientSide)
                return;

            if (drowned.getTarget() == null)
                return;

            DrownedConfig config = ModConfigs.getDrowned();

            // Cancel vanilla motion
            trident.setDeltaMovement(0,0,0);

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
        });
    }

    private static void applyConfig(Drowned drowned, DrownedConfig config) {

        RandomSource random = drowned.level().getRandom();

        DoorBreakUtil.handleDoorBreaking(
                drowned,
                config.canBreakDoors,
                config.doorBreakMode
        );

        if (config.CustomName) {
            MobNameUtil.applyRandomName(drowned);
        }

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

        drowned.setHealth((float) config.health);

    }

    private static void applyEquipment(Drowned drowned, DrownedConfig config) {

        if (drowned.getMainHandItem().isEmpty()) {

            double roll = drowned.getRandom().nextDouble();

            if (roll < config.tridentChance) {
                drowned.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.TRIDENT));
            } else if (roll < config.tridentChance + config.fishingRodChance) {
                drowned.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.FISHING_ROD));
            }
        }

        if (drowned.getItemBySlot(EquipmentSlot.OFFHAND).isEmpty()) {

            if (drowned.getRandom().nextDouble() < config.nautilusShellChance) {

                drowned.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.NAUTILUS_SHELL));
                drowned.setGuaranteedDrop(EquipmentSlot.OFFHAND);
            }
        }
    }
}