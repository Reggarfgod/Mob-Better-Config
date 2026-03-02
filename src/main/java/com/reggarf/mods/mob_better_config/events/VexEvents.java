//package com.reggarf.mods.mob_better_config.events;
//
//import com.reggarf.mods.mob_better_config.config.ModConfigs;
//import com.reggarf.mods.mob_better_config.config.VexConfig;
//import com.reggarf.mods.mob_better_config.util.*;
//
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraft.world.entity.ai.goal.Goal;
//import net.minecraft.world.entity.monster.Vex;
//
//import net.neoforged.bus.api.SubscribeEvent;
//import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
//import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
//import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
//import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
//
//import java.util.Iterator;
//
//public class VexEvents {
//
//    // =====================================================
//    // SPAWN
//    // =====================================================
//    @SubscribeEvent
//    public void onJoin(EntityJoinLevelEvent event) {
//
//        if (!(event.getEntity() instanceof Vex vex))
//            return;
//
//        if (!(vex.level() instanceof ServerLevel level))
//            return;
//
//        VexConfig config = ModConfigs.getVex();
//
//        if (vex.getPersistentData().getBoolean("mob_better_config_spawned"))
//            return;
//
//        applyConfig(vex, config);
//        removeDisabledGoals(vex, config);
//
//        BossUtil.tryApplyBoss(
//                vex,
//                config.bossMode,
//                config.forceAllBoss,
//                config.bossChance,
//                config.bossHealthMultiplier,
//                config.bossDamageMultiplier,
//                config.bossGlowing,
//                config.bossCustomName
//        );
//
//        // Spawn Multiplier
//        for (int i = 1; i < config.spawnMultiplier; i++) {
//
//            Vex extra = new Vex(EntityType.VEX, level);
//
//            extra.moveTo(
//                    vex.getX(),
//                    vex.getY(),
//                    vex.getZ(),
//                    vex.getYRot(),
//                    vex.getXRot()
//            );
//
//            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);
//
//            applyConfig(extra, config);
//            removeDisabledGoals(extra, config);
//
//            level.addFreshEntity(extra);
//        }
//    }
//
//    // =====================================================
//    // APPLY CONFIG
//    // =====================================================
//    private void applyConfig(Vex vex, VexConfig config) {
//
//        if (config.CustomName)
//            MobNameUtil.applyRandomName(vex);
//
//        if (vex.getAttribute(Attributes.MAX_HEALTH) != null)
//            vex.getAttribute(Attributes.MAX_HEALTH)
//                    .setBaseValue(config.health);
//
//        if (vex.getAttribute(Attributes.ATTACK_DAMAGE) != null)
//            vex.getAttribute(Attributes.ATTACK_DAMAGE)
//                    .setBaseValue(config.attackDamage);
//
//        if (vex.getAttribute(Attributes.MOVEMENT_SPEED) != null)
//            vex.getAttribute(Attributes.MOVEMENT_SPEED)
//                    .setBaseValue(config.movementSpeed);
//
//        vex.setHealth((float) config.health);
//
//        if (!config.hasLimitedLife) {
//            vex.setLimitedLife(Integer.MAX_VALUE);
//        } else {
//            vex.setLimitedLife(config.limitedLifeTicks);
//        }
//
//        if (config.fireImmune)
//            vex.setRemainingFireTicks(0);
//
//        if (config.glowing)
//            vex.setGlowingTag(true);
//    }
//
//    // =====================================================
//    // REMOVE DISABLED AI GOALS
//    // =====================================================
//    private void removeDisabledGoals(Vex vex, VexConfig config) {
//
//        Iterator<Goal> iterator = vex.goalSelector.getAvailableGoals()
//                .stream()
//                .map(wrapper -> wrapper.getGoal())
//                .iterator();
//
//        while (iterator.hasNext()) {
//
//            Goal goal = iterator.next();
//            String name = goal.getClass().getSimpleName();
//
//            if (!config.enableChargeAttack && name.contains("VexChargeAttackGoal")) {
//                vex.goalSelector.removeGoal(goal);
//            }
//
//            if (!config.copyOwnerTarget && name.contains("VexCopyOwnerTargetGoal")) {
//                vex.targetSelector.removeGoal(goal);
//            }
//        }
//    }
//
//    // =====================================================
//    // DAMAGE MULTIPLIER
//    // =====================================================
//    @SubscribeEvent
//    public void onDamage(LivingDamageEvent event) {
//
//        if (!(event.getEntity() instanceof Vex vex))
//            return;
//
//        VexConfig config = ModConfigs.getVex();
//
//        float damage = event.getAmount();
//        event.setAmount(damage);
//    }
//
//    // =====================================================
//    // XP
//    // =====================================================
//    @SubscribeEvent
//    public void onXP(LivingExperienceDropEvent event) {
//
//        VexConfig config = ModConfigs.getVex();
//
//        XPUtil.applyXpIfInstance(
//                event,
//                Vex.class,
//                config.xpMultiplier
//        );
//    }
//
//    // =====================================================
//    // LOOT
//    // =====================================================
//    @SubscribeEvent
//    public void onDrops(LivingDropsEvent event) {
//
//        if (!(event.getEntity() instanceof Vex vex))
//            return;
//
//        if (!(vex.level() instanceof ServerLevel level))
//            return;
//
//        VexConfig config = ModConfigs.getVex();
//
//        LootUtil.applyLootMultiplier(
//                event,
//                level,
//                vex,
//                config.lootMultiplier
//        );
//    }
//
//    // =====================================================
//    // REINFORCEMENT
//    // =====================================================
//    @SubscribeEvent
//    public void onDamaged(LivingDamageEvent.Post event) {
//
//        if (!(event.getEntity() instanceof Vex vex))
//            return;
//
//        if (!(vex.level() instanceof ServerLevel level))
//            return;
//
//        VexConfig config = ModConfigs.getVex();
//
//        ReinforcementUtil.trySpawnReinforcement(
//                vex,
//                level,
//                config.reinforcementChance,
//                4
//        );
//    }
//}