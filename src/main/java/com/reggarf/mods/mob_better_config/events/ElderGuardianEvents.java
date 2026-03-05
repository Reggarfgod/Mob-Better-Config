package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.ElderGuardianConfig;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.ElderGuardian;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class ElderGuardianEvents {

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof ElderGuardian elder))
            return;

        if (!(elder.level() instanceof ServerLevel))
            return;

        ElderGuardianConfig config = ModConfigs.getElderGuardian();

        if (elder.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(elder, config);

        BossUtil.tryApplyBoss(
                elder,
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

    private void applyConfig(ElderGuardian elder, ElderGuardianConfig config) {

        if (config.customName)
            MobNameUtil.applyRandomName(elder);

        if (elder.getAttribute(Attributes.MAX_HEALTH) != null)
            elder.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (elder.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            elder.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage);

        if (elder.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            elder.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);

        elder.setHealth((float) config.health);

        if (config.glowing)
            elder.setGlowingTag(true);
    }

//    @SubscribeEvent
//    public void onMiningFatigueTick(EntityTickEvent.Post event) {
//
//        if (!(event.getEntity() instanceof ElderGuardian elder))
//            return;
//
//        if (!(elder.level() instanceof ServerLevel level))
//            return;
//
//        ElderGuardianConfig config = ModConfigs.getElderGuardian();
//
//        if (!config.enableMiningFatigue)
//            return;
//
//        if ((elder.tickCount + elder.getId())
//                % config.miningFatigueInterval != 0)
//            return;
//
//        MobEffectInstance effect = new MobEffectInstance(
//                net.minecraft.world.effect.MobEffects.DIG_SLOWDOWN,
//                config.miningFatigueDuration,
//                config.miningFatigueAmplifier
//        );
//
//        net.minecraft.world.effect.MobEffectUtil.addEffectToPlayersAround(
//                level,
//                elder,
//                elder.position(),
//                config.miningFatigueRadius,
//                effect,
//                config.miningFatigueInterval
//        );
//    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        ElderGuardianConfig config = ModConfigs.getElderGuardian();

        XPUtil.applyXpIfInstance(
                event,
                ElderGuardian.class,
                config.xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof ElderGuardian elder))
            return;

        if (!(elder.level() instanceof ServerLevel level))
            return;

        ElderGuardianConfig config = ModConfigs.getElderGuardian();

        LootUtil.applyLootMultiplier(
                event,
                level,
                elder,
                config.lootMultiplier
        );
    }
}