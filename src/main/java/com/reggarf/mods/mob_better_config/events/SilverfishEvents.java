package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.SilverfishConfig;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Silverfish;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.lang.reflect.Field;

public class SilverfishEvents {

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Silverfish silverfish))
            return;

        if (!(silverfish.level() instanceof ServerLevel level))
            return;

        SilverfishConfig config = ModConfigs.getSilverfish();

        if (NbtUtil.getBooleanSafe(silverfish.getPersistentData(),("mob_better_config_spawned")))
            return;

        applyConfig(silverfish, config);

        if (!config.enableWakeFriends) {
            try {
                Field field = Silverfish.class.getDeclaredField("friendsGoal");
                field.setAccessible(true);
                field.set(silverfish, null);
            } catch (Exception ignored) {
            }
        }

        if (!config.enableMergeWithStone) {
            silverfish.goalSelector.getAvailableGoals().removeIf(wrapper ->
                    wrapper.getGoal().getClass().getName()
                            .contains("SilverfishMergeWithStoneGoal"));
        }

        BossUtil.tryApplyBoss(
                silverfish,
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

            Silverfish extra = new Silverfish(EntityType.SILVERFISH, level);

            extra.snapTo(
                    silverfish.getX(),
                    silverfish.getY(),
                    silverfish.getZ(),
                    silverfish.getYRot(),
                    silverfish.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);

            applyConfig(extra, config);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(Silverfish silverfish, SilverfishConfig config) {

        if (config.CustomName)
            MobNameUtil.applyRandomName(silverfish);

        if (silverfish.getAttribute(Attributes.MAX_HEALTH) != null)
            silverfish.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (silverfish.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            silverfish.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);

        if (silverfish.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            silverfish.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage);

        if (silverfish.getAttribute(Attributes.ARMOR) != null)
            silverfish.getAttribute(Attributes.ARMOR).setBaseValue(config.armor);

        if (silverfish.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            silverfish.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(config.knockbackResistance);

        if (silverfish.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            silverfish.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(config.attackKnockback);
        if (silverfish.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            silverfish.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(config.reinforcementChance);

        silverfish.setHealth((float) config.health);

        if (config.fireImmune)
            silverfish.setRemainingFireTicks(0);

        if (config.glowing)
            silverfish.setGlowingTag(true);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        SilverfishConfig config = ModConfigs.getSilverfish();

        XPUtil.applyXpIfInstance(
                event,
                Silverfish.class,
                config.xpMultiplier
        );
    }
    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Silverfish silverfish))
            return;

        if (!(silverfish.level() instanceof ServerLevel level))
            return;

        SilverfishConfig config = ModConfigs.getSilverfish();

        LootUtil.applyLootMultiplier(
                event,
                level,
                silverfish,
                config.lootMultiplier
        );
    }
}