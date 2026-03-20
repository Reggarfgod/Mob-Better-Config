package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.BlazeConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BlazeEvents {

    public static void onSpawn(Blaze blaze, ServerLevel level) {
        if (CommonMobHandler.isInitialized(blaze))
            return;
        CommonMobHandler.markInitialized(blaze);

        BlazeConfig config = ModConfigs.getBlaze();

        CommonMobHandler.applyCommonAttributes(
                blaze,
                config.health,
                config.armor,
                config.armorToughness,
                config.attackDamage,
                config.attackSpeed,
                config.movementSpeed,
                config.followRange,
                config.knockbackResistance,
                config.attackKnockback,
                config.stepHeight,
                config.gravity,
                config.glowing,
                config.CustomName,
                config.canBreakDoors,
                config.doorBreakMode,
                config.reinforcementChance
        );

        blaze.removeAllGoals(goal ->
                goal.getClass().getSimpleName().equals("BlazeAttackGoal")
        );

        GoalUtil.addGoal(blaze, 4, new ConfigurableBlazeAttackGoal(blaze));

        BossUtil.tryApplyBoss(
                blaze,
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

        CommonMobHandler.spawnMultiplier(
                blaze,
                level,
                config.spawnMultiplier
        );
    }

    public static void onTick(Blaze blaze) {

        BlazeConfig config = ModConfigs.getBlaze();

        Vec3 motion = blaze.getDeltaMovement();

        if (!blaze.onGround() && motion.y < 0.0D) {

            blaze.setDeltaMovement(
                    motion.x,
                    motion.y * config.fallSlowMultiplier,
                    motion.z
            );

            blaze.hasImpulse = true;
        }

        LivingEntity target = blaze.getTarget();

        if (target != null && blaze.canAttack(target)) {

            if (target.getEyeY() > blaze.getEyeY()) {

                Vec3 current = blaze.getDeltaMovement();

                blaze.setDeltaMovement(
                        current.x,
                        current.y + 0.15D * config.verticalBoostMultiplier,
                        current.z
                );

                blaze.hasImpulse = true;
            }
        }
    }

    public static void onFireballDamage(float[] damageHolder) {

        damageHolder[0] *= (float) ModConfigs.getBlaze().fireballDamageMultiplier;
    }

    public static void onXP(float[] xpHolder) {

        xpHolder[0] *= (float) ModConfigs.getBlaze().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, Blaze blaze) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                blaze,
                ModConfigs.getBlaze().lootMultiplier
        );
    }

    private static class ConfigurableBlazeAttackGoal extends Goal {

        private final Blaze blaze;
        private int attackStep;
        private int attackTime;

        public ConfigurableBlazeAttackGoal(Blaze blaze) {
            this.blaze = blaze;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = blaze.getTarget();
            return target != null && target.isAlive() && blaze.canAttack(target);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {

            BlazeConfig config = ModConfigs.getBlaze();

            if (attackTime > 0)
                attackTime--;

            LivingEntity target = blaze.getTarget();

            if (target == null)
                return;

            boolean canSee = blaze.getSensing().hasLineOfSight(target);

            double dist = blaze.distanceToSqr(target);
            double range = blaze.getAttributeValue(Attributes.FOLLOW_RANGE);

            if (dist <= range * range && canSee) {

                if (attackTime <= 0) {

                    attackStep++;

                    if (attackStep == 1)
                        attackTime = config.chargedTime;

                    else if (attackStep <= 4) {

                        attackTime = 6;

                        Vec3 dir = target.position().subtract(blaze.position()).normalize();

                        for (int i = 0; i < config.fireballCount; i++) {

                            SmallFireball fireball =
                                    new SmallFireball(blaze.level(), blaze, dir);

                            fireball.setPos(
                                    blaze.getX(),
                                    blaze.getY(0.5D) + 0.5D,
                                    blaze.getZ()
                            );

                            blaze.level().addFreshEntity(fireball);
                        }

                    } else {

                        attackTime = config.burstCooldown;
                        attackStep = 0;
                    }
                }

                blaze.getLookControl().setLookAt(target);
            }
        }
    }
    public static boolean onDamage(Blaze blaze, DamageSource source) {

        BlazeConfig config = ModConfigs.getBlaze();

        return WaterDamageUtil.shouldBlockWaterDamage(
                blaze,
                source,
                config.takeWaterDamage
        );
    }
}