package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.WitherConfig;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.WitherSkull;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class WitherEvents {
    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof WitherBoss wither))
            return;

        if (!(wither.level() instanceof ServerLevel))
            return;

        WitherConfig config = ModConfigs.getWither();

        if (NbtUtil.getBooleanSafe(wither.getPersistentData(),"mob_better_config_spawned"))
            return;

        applyConfig(wither, config);

        wither.setInvulnerableTicks(config.invulnerableTicks);

        BossUtil.tryApplyBoss(
                wither,
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

    private void applyConfig(WitherBoss wither, WitherConfig config) {

        if (config.customName)
            MobNameUtil.applyRandomName(wither);

        if (wither.getAttribute(Attributes.MAX_HEALTH) != null)
            wither.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (wither.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            wither.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);

        if (wither.getAttribute(Attributes.FLYING_SPEED) != null)
            wither.getAttribute(Attributes.FLYING_SPEED)
                    .setBaseValue(config.flyingSpeed);

        if (wither.getAttribute(Attributes.FOLLOW_RANGE) != null)
            wither.getAttribute(Attributes.FOLLOW_RANGE)
                    .setBaseValue(config.followRange);

        if (wither.getAttribute(Attributes.ARMOR) != null)
            wither.getAttribute(Attributes.ARMOR)
                    .setBaseValue(config.armor);

        wither.setHealth((float) config.health);

        if (config.glowing)
            wither.setGlowingTag(true);
    }

    @SubscribeEvent
    public void onSkullDamage(LivingIncomingDamageEvent event) {

        if (!(event.getSource().getDirectEntity() instanceof WitherSkull skull))
            return;

        if (!(skull.getOwner() instanceof WitherBoss))
            return;

        WitherConfig config = ModConfigs.getWither();

        event.setAmount(
                event.getAmount() *
                        (float) config.skullDamageMultiplier
        );
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        WitherConfig config = ModConfigs.getWither();

        XPUtil.applyXpIfInstance(
                event,
                WitherBoss.class,
                config.xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof WitherBoss wither))
            return;

        if (!(wither.level() instanceof ServerLevel level))
            return;

        WitherConfig config = ModConfigs.getWither();

        LootUtil.applyLootMultiplier(
                event,
                level,
                wither,
                config.lootMultiplier
        );
    }
}