package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ElderGuardianConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.GuardianConfig;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.LivingEntity;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class GuardianEvents {

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Guardian guardian))
            return;

        if (guardian instanceof ElderGuardian)
            return;

        if (!(guardian.level() instanceof ServerLevel))
            return;

        GuardianConfig config = ModConfigs.getGuardian();

        if (NbtUtil.getBooleanSafe(guardian.getPersistentData(),("mob_better_config_spawned")))
            return;

        applyConfig(guardian, config);

        BossUtil.tryApplyBoss(
                guardian,
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
    }

    private void applyConfig(Guardian guardian, GuardianConfig config) {

        if (config.CustomName)
            MobNameUtil.applyRandomName(guardian);

        if (guardian.getAttribute(Attributes.MAX_HEALTH) != null)
            guardian.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (guardian.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            guardian.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage);

        if (guardian.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            guardian.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);

        if (guardian.getAttribute(Attributes.FOLLOW_RANGE) != null)
            guardian.getAttribute(Attributes.FOLLOW_RANGE)
                    .setBaseValue(config.followRange);

        if (guardian.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            guardian.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(config.reinforcementChance);

        if (guardian.getAttribute(Attributes.ARMOR) != null)
            guardian.getAttribute(Attributes.ARMOR).setBaseValue(config.armor);

        if (guardian.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            guardian.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(config.knockbackResistance);

        if (guardian.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            guardian.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(config.attackKnockback);

        guardian.setHealth((float) config.health);

        if (config.glowing)
            guardian.setGlowingTag(true);
    }

    @SubscribeEvent
    public void onGuardianTick(EntityTickEvent.Pre event) {

        if (!(event.getEntity() instanceof Guardian guardian))
            return;

        // ELDER GUARDIAN
        if (guardian instanceof ElderGuardian) {

            ElderGuardianConfig config = ModConfigs.getElderGuardian();

            if (!config.enableLaser) {

                if (guardian.getTarget() != null) {
                    guardian.setTarget(null);
                }

                // Stop navigation too (extra safe)
                guardian.getNavigation().stop();
            }

            return;
        }

        // NORMAL GUARDIAN
        GuardianConfig config = ModConfigs.getGuardian();

        if (!config.enableLaser) {

            if (guardian.getTarget() != null) {
                guardian.setTarget(null);
            }

            guardian.getNavigation().stop();
        }
    }

    @SubscribeEvent
    public void onLaserDamage(LivingIncomingDamageEvent event) {

        if (!(event.getSource().getEntity() instanceof Guardian guardian))
            return;

        if (!event.getSource().is(DamageTypes.INDIRECT_MAGIC))
            return;


        // ELDER GUARDIAN
        if (guardian instanceof ElderGuardian) {

            ElderGuardianConfig config = ModConfigs.getElderGuardian();

            if (!config.enableLaser) {
                event.setCanceled(true);
                return;
            }

            event.setAmount(
                    event.getAmount() *
                            (float) config.laserDamageMultiplier
            );
            return;
        }

        // NORMAL GUARDIAN
        GuardianConfig config = ModConfigs.getGuardian();

        if (!config.enableLaser) {
            event.setCanceled(true);
            return;
        }

        event.setAmount(
                event.getAmount() *
                        (float) config.laserDamageMultiplier
        );
    }

    @SubscribeEvent
    public void onThorns(LivingIncomingDamageEvent event) {

        if (!(event.getEntity() instanceof Guardian guardian))
            return;

        if (guardian.isMoving())
            return;
        if (event.getSource().is(DamageTypes.THORNS))
            return;
        if (event.getSource().is(net.minecraft.tags.DamageTypeTags.AVOIDS_GUARDIAN_THORNS))
            return;

        if (!(event.getSource().getDirectEntity() instanceof LivingEntity attacker))
            return;

        if (guardian instanceof ElderGuardian) {

            ElderGuardianConfig config = ModConfigs.getElderGuardian();

            if (!config.enableThorns)
                return;

            attacker.hurt(
                    guardian.damageSources().thorns(guardian),
                    (float) config.thornsDamage
            );
            return;
        }

        GuardianConfig config = ModConfigs.getGuardian();

        if (!config.enableThorns)
            return;

        attacker.hurt(
                guardian.damageSources().thorns(guardian),
                (float) config.thornsDamage
        );
    }


    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Guardian guardian))
            return;

        if (guardian instanceof ElderGuardian)
            return;

        GuardianConfig config = ModConfigs.getGuardian();

        LivingEntity target = guardian.getTarget();
        if (target == null)
            return;

        if (!config.targetPlayers && target instanceof Player)
            guardian.setTarget(null);

        if (!config.targetSquid && target instanceof Squid)
            guardian.setTarget(null);

        if (!config.targetAxolotl && target instanceof Axolotl)
            guardian.setTarget(null);
    }


    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        GuardianConfig config = ModConfigs.getGuardian();

        XPUtil.applyXpIfInstance(
                event,
                Guardian.class,
                config.xpMultiplier
        );
    }


    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Guardian guardian))
            return;

        if (guardian instanceof ElderGuardian)
            return;

        if (!(guardian.level() instanceof ServerLevel level))
            return;

        GuardianConfig config = ModConfigs.getGuardian();

        LootUtil.applyLootMultiplier(
                event,
                level,
                guardian,
                config.lootMultiplier
        );
    }
}