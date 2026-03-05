package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.ZoglinConfig;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zoglin;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

public class ZoglinEvents {

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Zoglin zoglin))
            return;

        if (!(zoglin.level() instanceof ServerLevel level))
            return;

        var data = zoglin.getPersistentData();
        if (data.getBoolean("mob_better_config_processed"))
            return;

        if (data.getBoolean("mob_better_config_converted"))
            return;

        if (BossUtil.isBoss(zoglin))
            return;

        ZoglinConfig config = ModConfigs.getZoglin();

        applyConfig(zoglin, config);

        BossUtil.tryApplyBoss(
                zoglin,
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

        for (int i = 1; i < config.spawnMultiplier; i++) {

            Zoglin extra = new Zoglin(EntityType.ZOGLIN, level);

            extra.moveTo(
                    zoglin.getX(),
                    zoglin.getY(),
                    zoglin.getZ(),
                    zoglin.getYRot(),
                    zoglin.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);
            extra.getPersistentData().putBoolean("mob_better_config_processed", true);

            applyConfig(extra, config);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(Zoglin zoglin, ZoglinConfig config) {

        if (config.customName)
            MobNameUtil.applyRandomName(zoglin);

        if (zoglin.getAttribute(Attributes.MAX_HEALTH) != null)
            zoglin.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (zoglin.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            zoglin.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage);

        if (zoglin.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            zoglin.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);

        if (zoglin.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            zoglin.getAttribute(Attributes.KNOCKBACK_RESISTANCE)
                    .setBaseValue(config.knockbackResistance);

        if (zoglin.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            zoglin.getAttribute(Attributes.ATTACK_KNOCKBACK)
                    .setBaseValue(config.attackKnockback);

        zoglin.setHealth((float) config.health);

        if (config.glowing)
            zoglin.setGlowingTag(true);

        if (config.fireImmune)
            zoglin.setRemainingFireTicks(0);

        if (config.allowBaby && zoglin.level().random.nextDouble() < config.babyChance)
            zoglin.setBaby(true);
    }

    @SubscribeEvent
    public void onDamage(LivingDamageEvent.Pre event) {

        if (!(event.getSource().getEntity() instanceof Zoglin))
            return;

        ZoglinConfig config = ModConfigs.getZoglin();

        float scaledDamage = event.getNewDamage()
                * (float) config.attackDamageMultiplier;

        event.setNewDamage(scaledDamage);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        ZoglinConfig config = ModConfigs.getZoglin();

        XPUtil.applyXpIfInstance(
                event,
                Zoglin.class,
                config.xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Zoglin zoglin))
            return;

        if (!(zoglin.level() instanceof ServerLevel level))
            return;

        ZoglinConfig config = ModConfigs.getZoglin();

        LootUtil.applyLootMultiplier(
                event,
                level,
                zoglin,
                config.lootMultiplier
        );
    }

    @SubscribeEvent
    public void onDamaged(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Zoglin zoglin))
            return;

        if (!(zoglin.level() instanceof ServerLevel level))
            return;

        ZoglinConfig config = ModConfigs.getZoglin();

        ReinforcementUtil.trySpawnReinforcement(
                zoglin,
                level,
                config.reinforcementChance,
                4
        );
    }
}