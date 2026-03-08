package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.BlazeConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.EnumSet;

public class BlazeEvents {

    private static final String SPAWN_TAG = "mbc_spawned";

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {


        if (!(event.getEntity() instanceof Blaze blaze))
            return;

        if (!(blaze.level() instanceof ServerLevel level))
            return;

        BlazeConfig config = ModConfigs.getBlaze();


        if (NbtUtil.getBooleanSafe(blaze.getPersistentData(), (SPAWN_TAG)))
            return;

        if (blaze.getAttribute(Attributes.MAX_HEALTH) != null)
            blaze.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.health);

        if (blaze.getAttribute(Attributes.FOLLOW_RANGE) != null)
            blaze.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(config.followRange);
        if (config.CustomName) {
            MobNameUtil.applyRandomName(blaze);
        }
        blaze.setHealth((float) config.health);

        blaze.goalSelector.getAvailableGoals().removeIf(
                wrapper -> wrapper.getGoal().getClass().getSimpleName().equals("BlazeAttackGoal")
        );

        blaze.goalSelector.addGoal(4, new ConfigurableBlazeAttackGoal(blaze));

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

        for (int i = 1; i < config.spawnMultiplier; i++) {
            Blaze extra = new Blaze(EntityType.BLAZE, level);
            extra.snapTo
                    (blaze.getX(),
                            blaze.getY(),
                            blaze.getZ());
            extra.getPersistentData().putBoolean(SPAWN_TAG, true);
            level.addFreshEntity(extra);
        }

    }

    private static class ConfigurableBlazeAttackGoal extends Goal {

        private final Blaze blaze;
        private int attackStep;
        private int attackTime;
        private int lastSeen;

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
            if (canSee) lastSeen = 0;
            else lastSeen++;

            double dist = blaze.distanceToSqr(target);
            double followRange = blaze.getAttributeValue(Attributes.FOLLOW_RANGE);
            double followRangeSq = followRange * followRange;

            if (dist < 4.0D) {

                if (!canSee)
                    return;

                if (attackTime <= 0) {
                    attackTime = 20;
                    blaze.doHurtTarget(getServerLevel(blaze), target);
                }

                blaze.getMoveControl().setWantedPosition(
                        target.getX(), target.getY(), target.getZ(), 1.0D
                );
            }

            else if (dist <= followRangeSq && canSee) {

                if (attackTime <= 0) {

                    attackStep++;

                    if (attackStep == 1) {
                        attackTime = Math.max(1, config.chargedTime);
                    }

                    else if (attackStep <= 4) {

                        attackTime = 6;

                        double dx = target.getX() - blaze.getX();
                        double dy = target.getY(0.5D) - blaze.getY(0.5D);
                        double dz = target.getZ() - blaze.getZ();

                        double spread = Math.sqrt(Math.sqrt(dist)) * 0.5D;
                        int shots = Math.max(1, config.fireballCount);

                        for (int i = 0; i < shots; i++) {

                            Vec3 vec3 = new Vec3(
                                    blaze.getRandom().triangle(dx, 2.297 * spread),
                                    dy,
                                    blaze.getRandom().triangle(dz, 2.297 * spread)
                            );

                            SmallFireball fireball =
                                    new SmallFireball(blaze.level(), blaze, vec3.normalize());

                            fireball.setPos(
                                    blaze.getX(),
                                    blaze.getY(0.5D) + 0.5D,
                                    blaze.getZ()
                            );

                            blaze.level().addFreshEntity(fireball);
                        }
                    }

                    else {
                        attackTime = Math.max(1, config.burstCooldown);
                        attackStep = 0;
                    }
                }

                blaze.getLookControl().setLookAt(target, 10.0F, 10.0F);
            }
        }
    }

    @SubscribeEvent
    public void onFireballDamage(LivingDamageEvent.Pre event) {

        if (!(event.getSource().getDirectEntity() instanceof SmallFireball fireball))
            return;

        if (!(fireball.getOwner() instanceof Blaze))
            return;

        event.setNewDamage(
                event.getNewDamage() * (float) ModConfigs.getBlaze().fireballDamageMultiplier
        );
    }


    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Blaze blaze))
            return;

        if (blaze.level().isClientSide())
            return;

        BlazeConfig config = ModConfigs.getBlaze();

        Vec3 motion = blaze.getDeltaMovement();

        if (!blaze.onGround() && motion.y < 0.0D) {

            double newY = motion.y * config.fallSlowMultiplier;

            blaze.setDeltaMovement(motion.x, newY, motion.z);
        }

        LivingEntity target = blaze.getTarget();

        if (target != null && blaze.canAttack(target)) {

            if (target.getEyeY() > blaze.getEyeY()) {

                Vec3 current = blaze.getDeltaMovement();

                double boost = 0.15D * config.verticalBoostMultiplier;

                blaze.setDeltaMovement(
                        current.x,
                        current.y + boost,
                        current.z
                );
            }
        }
    }
    @SubscribeEvent
    public void onWaterDamage(LivingIncomingDamageEvent event) {

        if (!(event.getEntity() instanceof Blaze blaze))
            return;

        WaterDamageUtil.handleWaterDamage(
                event,
                blaze,
                ModConfigs.getBlaze().takeWaterDamage
        );
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        XPUtil.applyXpIfInstance(
                event,
                Blaze.class,
                ModConfigs.getBlaze().xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Blaze blaze))
            return;

        if (!(blaze.level() instanceof ServerLevel level))
            return;

        LootUtil.applyLootMultiplier(
                event,
                level,
                blaze,
                ModConfigs.getBlaze().lootMultiplier
        );
    }

    @SubscribeEvent
    public void onDamaged(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Blaze blaze))
            return;

        if (!(blaze.level() instanceof ServerLevel level))
            return;

        ReinforcementUtil.trySpawnReinforcement(
                blaze,
                level,
                ModConfigs.getBlaze().reinforcementChance,
                4
        );
    }
}