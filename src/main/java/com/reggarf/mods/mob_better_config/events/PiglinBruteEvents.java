package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.PiglinBruteConfig;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class PiglinBruteEvents {

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof PiglinBrute brute))
            return;

        if (!(brute.level() instanceof ServerLevel))
            return;

        PiglinBruteConfig config = ModConfigs.getPiglinBrute();

        if (brute.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(brute, config);

        BossUtil.tryApplyBoss(
                brute,
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

    private void applyConfig(PiglinBrute brute, PiglinBruteConfig config) {

        if (config.customName)
            MobNameUtil.applyRandomName(brute);

        if (brute.getAttribute(Attributes.MAX_HEALTH) != null)
            brute.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (brute.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            brute.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage);

        if (brute.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            brute.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);


        if (brute.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            brute.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                    .setBaseValue(config.reinforcementChance);

        if (brute.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            brute.getAttribute(Attributes.ATTACK_KNOCKBACK)
                    .setBaseValue(config.attackKnockback);

        if (brute.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            brute.getAttribute(Attributes.KNOCKBACK_RESISTANCE)
                    .setBaseValue(config.knockbackResistance);



        if (brute.getAttribute(Attributes.ARMOR) != null)
            brute.getAttribute(Attributes.ARMOR)
                    .setBaseValue(config.armor);


        brute.setHealth((float) config.health);

        if (config.glowing)
            brute.setGlowingTag(true);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        PiglinBruteConfig config = ModConfigs.getPiglinBrute();

        XPUtil.applyXpIfInstance(
                event,
                PiglinBrute.class,
                config.xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof PiglinBrute brute))
            return;

        if (!(brute.level() instanceof ServerLevel level))
            return;

        PiglinBruteConfig config = ModConfigs.getPiglinBrute();

        LootUtil.applyLootMultiplier(
                event,
                level,
                brute,
                config.lootMultiplier
        );
    }
}