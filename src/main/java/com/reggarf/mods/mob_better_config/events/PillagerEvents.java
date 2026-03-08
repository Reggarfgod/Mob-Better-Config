package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.PillagerConfig;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CrossbowItem;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.*;

public class PillagerEvents {

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Pillager pillager))
            return;

        if (!(pillager.level() instanceof ServerLevel level))
            return;

        PillagerConfig config = ModConfigs.getPillager();

        applyConfig(pillager, config);

        // Ensure full health on spawn
        pillager.setHealth((float) config.health);

        BossUtil.tryApplyBoss(
                pillager,
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

        // Spawn Multiplier
        for (int i = 1; i < config.spawnMultiplier; i++) {

            Pillager extra = new Pillager(EntityType.PILLAGER, level);

            extra.snapTo(
                    pillager.getX(),
                    pillager.getY(),
                    pillager.getZ(),
                    pillager.getYRot(),
                    pillager.getXRot()
            );

            applyConfig(extra, config);
            extra.setHealth((float) config.health);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(Pillager pillager, PillagerConfig config) {

        if (config.CustomName)
            MobNameUtil.applyRandomName(pillager);

        if (pillager.getAttribute(Attributes.MAX_HEALTH) != null)
            pillager.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (pillager.getAttribute(Attributes.ARMOR) != null)
            pillager.getAttribute(Attributes.ARMOR)
                    .setBaseValue(config.armor);

        if (pillager.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            pillager.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage);

        if (pillager.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            pillager.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);

        if (pillager.getAttribute(Attributes.FOLLOW_RANGE) != null)
            pillager.getAttribute(Attributes.FOLLOW_RANGE)
                    .setBaseValue(config.followRange);
        if (pillager.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            pillager.getAttribute(Attributes.ATTACK_KNOCKBACK)
                    .setBaseValue(config.attackKnockback);

        if (pillager.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            pillager.getAttribute(Attributes.KNOCKBACK_RESISTANCE)
                    .setBaseValue(config.knockbackResistance);
        if (config.fireImmune)
            pillager.setRemainingFireTicks(0);

        if (config.glowing)
            pillager.setGlowingTag(true);

        // Raid bonus
        if (config.strongerInRaid && pillager.hasActiveRaid()) {

            if (pillager.getAttribute(Attributes.MAX_HEALTH) != null)
                pillager.getAttribute(Attributes.MAX_HEALTH)
                        .setBaseValue(config.health * config.raidHealthBonus);

            if (pillager.getAttribute(Attributes.ATTACK_DAMAGE) != null)
                pillager.getAttribute(Attributes.ATTACK_DAMAGE)
                        .setBaseValue(config.attackDamage * config.raidDamageBonus);

            pillager.setHealth((float) (config.health * config.raidHealthBonus));
        }
    }

    @SubscribeEvent
    public void onDamage(LivingDamageEvent.Pre event) {

        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow))
            return;

        if (!(arrow.getOwner() instanceof Pillager pillager))
            return;

        PillagerConfig config = ModConfigs.getPillager();

        if (!(pillager.getMainHandItem().getItem() instanceof CrossbowItem))
            return;

        float scaledDamage = event.getNewDamage()
                * (float) config.crossbowDamageMultiplier;

        event.setNewDamage(scaledDamage);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        PillagerConfig config = ModConfigs.getPillager();

        XPUtil.applyXpIfInstance(
                event,
                Pillager.class,
                config.xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Pillager pillager))
            return;

        if (!(pillager.level() instanceof ServerLevel level))
            return;

        PillagerConfig config = ModConfigs.getPillager();

        LootUtil.applyLootMultiplier(
                event,
                level,
                pillager,
                config.lootMultiplier
        );
    }

    @SubscribeEvent
    public void onDamaged(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Pillager pillager))
            return;

        if (!(pillager.level() instanceof ServerLevel level))
            return;

        PillagerConfig config = ModConfigs.getPillager();

        ReinforcementUtil.trySpawnReinforcement(
                pillager,
                level,
                config.reinforcementChance,
                4
        );
    }
}