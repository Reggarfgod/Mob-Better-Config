package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.EndermanConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.damagesource.DamageSource;

public class EndermanEvents {

    private static final ResourceLocation RAGE_ID =
            ResourceLocation.fromNamespaceAndPath("mob_better_config", "rage_speed");

    public static void onJoin(EnderMan enderman, ServerLevel level) {

        if (CommonMobHandler.isInitialized(enderman))
            return;
        CommonMobHandler.markInitialized(enderman);

        EndermanConfig config = ModConfigs.getEnderman();

        applyConfig(enderman, config);

        BossUtil.tryApplyBoss(
                enderman,
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

        CommonMobHandler.spawnMultiplier(enderman, level, config.spawnMultiplier);
    }

    private static void applyConfig(EnderMan enderman, EndermanConfig config) {

        CommonMobHandler.applyCommonAttributes(
                enderman,
                config.health,
                config.armor,
                config.armorToughness,
                config.attackDamage,
                config.attackSpeed,
                config.movementSpeed,
                config.followRange,
                config.knockbackResistance,
                config.attackKnockback,
                config.stepHeight,
                config.gravity,
                config.glowing,
                config.CustomName,
                config.canBreakDoors,
                config.doorBreakMode,
                config.reinforcementChance
        );

        if (!config.canPickupBlocks)
            enderman.setCarriedBlock(null);
    }

    public static void onTeleport(EnderMan enderman,
                                  double[] target,
                                  boolean[] cancel) {

        EndermanConfig config = ModConfigs.getEnderman();

        if (!config.enableTeleport) {
            cancel[0] = true;
            return;
        }

        if (config.teleportChance < 1.0D &&
                enderman.getRandom().nextDouble() > config.teleportChance) {
            cancel[0] = true;
            return;
        }

        if (config.teleportRangeMultiplier != 1.0D) {

            double dx = target[0] - enderman.getX();
            double dy = target[1] - enderman.getY();
            double dz = target[2] - enderman.getZ();

            target[0] = enderman.getX() + dx * config.teleportRangeMultiplier;
            target[1] = enderman.getY() + dy * config.teleportRangeMultiplier;
            target[2] = enderman.getZ() + dz * config.teleportRangeMultiplier;
        }
    }

    public static void onTick(EnderMan enderman, ServerLevel level) {

        EndermanConfig config = ModConfigs.getEnderman();

        if (config.alwaysAggressive && enderman.getTarget() == null) {

            var nearest = level.getNearestPlayer(enderman, 32);

            if (nearest != null) {
                enderman.setTarget(nearest);
                enderman.startPersistentAngerTimer();
            }
        }

        AttributeInstance speedAttr =
                enderman.getAttribute(Attributes.MOVEMENT_SPEED);

        if (speedAttr != null) {

            speedAttr.removeModifier(RAGE_ID);

            if (enderman.getTarget() != null &&
                    config.rageSpeedMultiplier != 1.0D) {

                AttributeModifier rage =
                        new AttributeModifier(
                                RAGE_ID,
                                config.rageSpeedMultiplier - 1.0D,
                                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                        );

                speedAttr.addTransientModifier(rage);
            }
        }

        DaylightBurnUtil.handleDaylightBurn(
                enderman,
                config.burnInDaylight,
                config.fireImmune,
                config.burnSeconds,
                true
        );
    }

    public static void onDrops(ServerLevel level, EnderMan enderman) {

        EndermanConfig config = ModConfigs.getEnderman();

        LootUtil.applyLootMultiplier(
                null,
                level,
                enderman,
                config.lootMultiplier
        );
    }
}