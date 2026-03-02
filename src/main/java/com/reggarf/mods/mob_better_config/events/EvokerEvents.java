package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.EvokerConfig;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.item.DyeColor;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class EvokerEvents {

    @SubscribeEvent
    public void onJoin(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof Evoker evoker))
            return;

        if (!(evoker.level() instanceof ServerLevel level))
            return;

        EvokerConfig config = ModConfigs.getEvoker();

        if (evoker.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(evoker, config);

        BossUtil.tryApplyBoss(
                evoker,
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

            Evoker extra = new Evoker(EntityType.EVOKER, level);

            extra.moveTo(
                    evoker.getX(),
                    evoker.getY(),
                    evoker.getZ(),
                    evoker.getYRot(),
                    evoker.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);

            applyConfig(extra, config);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(Evoker evoker, EvokerConfig config) {

        if (config.CustomName)
            MobNameUtil.applyRandomName(evoker);

        if (evoker.getAttribute(Attributes.MAX_HEALTH) != null)
            evoker.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (evoker.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            evoker.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);

        if (evoker.getAttribute(Attributes.FOLLOW_RANGE) != null)
            evoker.getAttribute(Attributes.FOLLOW_RANGE)
                    .setBaseValue(config.followRange);

        evoker.setHealth((float) config.health);

        if (config.fireImmune)
            evoker.setRemainingFireTicks(0);

        if (config.glowing)
            evoker.setGlowingTag(true);

        // Raid bonus
        if (config.strongerInRaid && evoker.hasActiveRaid()) {

            if (evoker.getAttribute(Attributes.MAX_HEALTH) != null)
                evoker.getAttribute(Attributes.MAX_HEALTH)
                        .setBaseValue(config.health * config.raidHealthBonus);

            evoker.setHealth((float) (config.health * config.raidHealthBonus));
        }
    }

    @SubscribeEvent
    public void onFangSpawn(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof EvokerFangs fangs))
            return;

        if (!(fangs.getOwner() instanceof Evoker))
            return;

        EvokerConfig config = ModConfigs.getEvoker();

        if (!config.enableFangsSpell) {
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public void onFangDamage(LivingDamageEvent.Pre event) {

        if (!(event.getSource().getDirectEntity() instanceof EvokerFangs fangs))
            return;

        if (!(fangs.getOwner() instanceof Evoker evoker))
            return;

        EvokerConfig config = ModConfigs.getEvoker();

        float damage = event.getNewDamage()
                * (float) config.fangsDamageMultiplier;

        if (config.strongerInRaid && evoker.hasActiveRaid()) {
            damage *= (float) config.raidDamageBonus;
        }

        event.setNewDamage(damage);
    }
    @SubscribeEvent
    public void onVexSpawn(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof Vex vex))
            return;

        if (!(vex.getOwner() instanceof Evoker evoker))
            return;

        if (!(evoker.level() instanceof ServerLevel level))
            return;

        EvokerConfig config = ModConfigs.getEvoker();

        // Prevent recursion for custom spawned vex
        if (vex.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        if (!config.enableVexSummon) {
            event.setCanceled(true);
            return;
        }

        // Count CURRENT existing vex from this evoker
        int existing = level.getEntitiesOfClass(
                Vex.class,
                evoker.getBoundingBox().inflate(32),
                v -> v.getOwner() == evoker
        ).size();

        //Cap to config amount
        if (existing >= config.summonVexCount) {
            event.setCanceled(true);
            return;
        }

        vex.setLimitedLife(config.vexLifeTicks);
        // Only run once per spell (when first vex appears)
        if (config.summonVexCount > 3 && existing == 1) {

            int extra = config.summonVexCount - 3;

            for (int i = 0; i < extra; i++) {

                Vex newVex = EntityType.VEX.create(level);
                if (newVex == null) continue;

                newVex.moveTo(
                        vex.getX(),
                        vex.getY(),
                        vex.getZ(),
                        0F,
                        0F
                );

                newVex.finalizeSpawn(
                        level,
                        level.getCurrentDifficultyAt(newVex.blockPosition()),
                        MobSpawnType.MOB_SUMMONED,
                        null
                );

                newVex.setOwner(evoker);
                newVex.setBoundOrigin(evoker.blockPosition());
                newVex.setLimitedLife(config.vexLifeTicks);

                // Prevent recursion
                newVex.getPersistentData().putBoolean("mob_better_config_spawned", true);

                level.addFreshEntity(newVex);
            }
        }
    }

    @SubscribeEvent
    public void onEntityTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Sheep sheep))
            return;

        EvokerConfig config = ModConfigs.getEvoker();

        if (config.allowWololo)
            return;

        if (sheep.getColor() == DyeColor.RED && sheep.tickCount < 5)
            sheep.setColor(DyeColor.BLUE);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        EvokerConfig config = ModConfigs.getEvoker();

        XPUtil.applyXpIfInstance(
                event,
                Evoker.class,
                config.xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Evoker evoker))
            return;

        if (!(evoker.level() instanceof ServerLevel level))
            return;

        EvokerConfig config = ModConfigs.getEvoker();

        LootUtil.applyLootMultiplier(
                event,
                level,
                evoker,
                config.lootMultiplier
        );
    }

    @SubscribeEvent
    public void onDamaged(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Evoker evoker))
            return;

        if (!(evoker.level() instanceof ServerLevel level))
            return;

        EvokerConfig config = ModConfigs.getEvoker();

        ReinforcementUtil.trySpawnReinforcement(
                evoker,
                level,
                config.reinforcementChance,
                4
        );
    }
}