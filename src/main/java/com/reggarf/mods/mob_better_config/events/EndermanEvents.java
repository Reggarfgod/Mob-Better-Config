package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.EndermanConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class EndermanEvents {

    private static final ResourceLocation RAGE_ID =
            ResourceLocation.fromNamespaceAndPath(
                    "mob_better_config",
                    "rage_speed"
            );

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof EnderMan enderman))
            return;

        if (!(enderman.level() instanceof ServerLevel level))
            return;

        if (enderman.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

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

        for (int i = 1; i < config.spawnMultiplier; i++) {

            EnderMan extra = new EnderMan(EntityType.ENDERMAN, level);

            extra.moveTo(
                    enderman.getX(),
                    enderman.getY(),
                    enderman.getZ(),
                    enderman.getYRot(),
                    enderman.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);

            applyConfig(extra, config);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(EnderMan enderman, EndermanConfig config) {
        DoorBreakUtil.handleDoorBreaking(
                enderman,
                config.canBreakDoors,
                config.doorBreakMode
        );
        if (config.CustomName) {
            MobNameUtil.applyRandomName(enderman);
        }
        if (enderman.getAttribute(Attributes.MAX_HEALTH) != null)
            enderman.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.health);

        if (enderman.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            enderman.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(config.attackDamage);

        if (enderman.getAttribute(Attributes.FOLLOW_RANGE) != null)
            enderman.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(config.followRange);

        enderman.setHealth((float) config.health);

        if (!config.canPickupBlocks)
            enderman.setCarriedBlock(null);

        if (config.glowing)
            enderman.setGlowingTag(true);
    }

    @SubscribeEvent
    public void onTeleport(EntityTeleportEvent.EnderEntity event) {

        if (!(event.getEntity() instanceof EnderMan enderman))
            return;

        EndermanConfig config = ModConfigs.getEnderman();

        if (!config.enableTeleport) {
            event.setCanceled(true);
            return;
        }

        if (config.teleportChance < 1.0D &&
                enderman.getRandom().nextDouble() > config.teleportChance) {
            event.setCanceled(true);
            return;
        }

        if (config.teleportRangeMultiplier != 1.0D) {

            double dx = event.getTargetX() - enderman.getX();
            double dy = event.getTargetY() - enderman.getY();
            double dz = event.getTargetZ() - enderman.getZ();

            event.setTargetX(enderman.getX() + dx * config.teleportRangeMultiplier);
            event.setTargetY(enderman.getY() + dy * config.teleportRangeMultiplier);
            event.setTargetZ(enderman.getZ() + dz * config.teleportRangeMultiplier);
        }
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof EnderMan enderman))
            return;

        if (!(enderman.level() instanceof ServerLevel level))
            return;

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

    @SubscribeEvent
    public void onWaterDamage(LivingDamageEvent.Pre event) {

        if (!(event.getEntity() instanceof EnderMan enderman))
            return;

        EndermanConfig config = ModConfigs.getEnderman();

        // If water damage is allowed, do nothing
        if (config.takeWaterDamage)
            return;

        // Vanilla water damage happens when entity is wet
        if (enderman.isInWaterRainOrBubble()
                || event.getSource().is(DamageTypeTags.IS_DROWNING)) {

            event.setNewDamage(0.0F);
        }

        // Handle splash water bottle
        if (event.getSource().getDirectEntity() instanceof ThrownPotion potion) {

            PotionContents contents =
                    potion.getItem().getOrDefault(
                            DataComponents.POTION_CONTENTS,
                            PotionContents.EMPTY
                    );

            if (contents.is(Potions.WATER)) {
                event.setNewDamage(0.0F);
            }
        }
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {
        EndermanConfig config = ModConfigs.getEnderman();
        XPUtil.applyXpIfInstance(event, EnderMan.class, config.xpMultiplier);
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof EnderMan enderman))
            return;

        if (!(enderman.level() instanceof ServerLevel level))
            return;

        EndermanConfig config = ModConfigs.getEnderman();

        LootUtil.applyLootMultiplier(event, level, enderman, config.lootMultiplier);
    }

    @SubscribeEvent
    public void onDamaged(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof EnderMan enderman))
            return;

        if (!(enderman.level() instanceof ServerLevel level))
            return;

        EndermanConfig config = ModConfigs.getEnderman();

        ReinforcementUtil.trySpawnReinforcement(
                enderman,
                level,
                config.reinforcementChance,
                4
        );
    }
}