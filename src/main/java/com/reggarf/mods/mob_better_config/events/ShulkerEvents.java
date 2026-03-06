package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.ShulkerConfig;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class ShulkerEvents {
    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Shulker shulker))
            return;

        if (!(shulker.level() instanceof ServerLevel level))
            return;

        ShulkerConfig config = ModConfigs.getShulker();

        if (shulker.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(shulker, config);

        BossUtil.tryApplyBoss(
                shulker,
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

            Shulker extra = new Shulker(EntityType.SHULKER, level);

            extra.moveTo(
                    shulker.getX(),
                    shulker.getY(),
                    shulker.getZ(),
                    shulker.getYRot(),
                    shulker.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);

            applyConfig(extra, config);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(Shulker shulker, ShulkerConfig config) {

        if (config.CustomName)
            MobNameUtil.applyRandomName(shulker);

        if (shulker.getAttribute(Attributes.MAX_HEALTH) != null)
            shulker.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (shulker.getAttribute(Attributes.ARMOR) != null)
            shulker.getAttribute(Attributes.ARMOR).setBaseValue(config.armor);

        if (shulker.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            shulker.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(config.knockbackResistance);

        if (shulker.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            shulker.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(config.attackKnockback);
        if (shulker.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            shulker.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(config.reinforcementChance);

        shulker.setHealth((float) config.health);

        if (config.fireImmune)
            shulker.setRemainingFireTicks(0);

        if (config.glowing)
            shulker.setGlowingTag(true);
    }

    @SubscribeEvent
    public void onTeleport(EntityTeleportEvent.EnderEntity event) {

        if (!(event.getEntity() instanceof Shulker shulker))
            return;

        ShulkerConfig config = ModConfigs.getShulker();

        if (!config.enableTeleport) {
            event.setCanceled(true);
            return;
        }

        if (shulker.level().random.nextDouble() > config.teleportChance) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onCloneSpawn(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof Shulker shulker))
            return;

        if (!(shulker.level() instanceof ServerLevel))
            return;

        ShulkerConfig config = ModConfigs.getShulker();

        if (!shulker.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        if (!config.enableClone) {
            event.setCanceled(true);
            return;
        }

        if (shulker.level().random.nextDouble() > config.cloneChance) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Shulker shulker))
            return;

        ShulkerConfig config = ModConfigs.getShulker();
        if (!config.allowPeek) {

            shulker.setTarget(null);
            shulker.getNavigation().stop();

            shulker.goalSelector.getAvailableGoals().removeIf(wrapper -> {
                String name = wrapper.getGoal().getClass().getSimpleName();
                return name.equals("ShulkerAttackGoal") ||
                        name.equals("ShulkerPeekGoal");
            });
        }
        var armorAttr = shulker.getAttribute(Attributes.ARMOR);
        if (armorAttr == null)
            return;

        boolean closed = shulker.getTarget() == null;

        if (closed) {
            double baseNeeded = config.armorWhenClosed - 20.0D;
            if (baseNeeded < 0)
                baseNeeded = 0;

            armorAttr.setBaseValue(baseNeeded);

        } else {
            armorAttr.setBaseValue(0.0D);
        }
    }

    @SubscribeEvent
    public void onBulletSpawn(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof ShulkerBullet bullet))
            return;

        if (!(bullet.getOwner() instanceof Shulker))
            return;

        ShulkerConfig config = ModConfigs.getShulker();

        if (!config.enableBulletAttack) {
            event.setCanceled(true);
        }
    }

    // =====================================================
    // BULLET DAMAGE MULTIPLIER
    // =====================================================
    @SubscribeEvent
    public void onBulletDamage(LivingIncomingDamageEvent event) {

        if (!(event.getSource().getDirectEntity() instanceof ShulkerBullet bullet))
            return;

        if (!(bullet.getOwner() instanceof Shulker))
            return;

        ShulkerConfig config = ModConfigs.getShulker();

        float damage = event.getAmount()
                * (float) config.bulletDamageMultiplier;

        event.setAmount(damage);
    }

    // =====================================================
    // XP
    // =====================================================
    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        ShulkerConfig config = ModConfigs.getShulker();

        XPUtil.applyXpIfInstance(
                event,
                Shulker.class,
                config.xpMultiplier
        );
    }

    // =====================================================
    // LOOT
    // =====================================================
    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Shulker shulker))
            return;

        if (!(shulker.level() instanceof ServerLevel level))
            return;

        ShulkerConfig config = ModConfigs.getShulker();

        LootUtil.applyLootMultiplier(
                event,
                level,
                shulker,
                config.lootMultiplier
        );
    }

}