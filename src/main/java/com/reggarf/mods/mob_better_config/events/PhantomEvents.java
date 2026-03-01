package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.PhantomConfig;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class PhantomEvents {

    @SubscribeEvent
    public void onJoin(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof Phantom phantom))
            return;

        if (!(phantom.level() instanceof ServerLevel level))
            return;

        PhantomConfig config = ModConfigs.getPhantom();

        if (phantom.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(phantom, config);
        BossUtil.tryApplyBoss(
                phantom,
                config.bossMode,
                config.forceAllBoss,
                config.bossChance,
                config.bossHealthMultiplier,
                config.bossDamageMultiplier,
                config.bossGlowing,
                config.bossCustomName
        );
        // Spawn multiplier
        for (int i = 1; i < config.spawnMultiplier; i++) {

            Phantom extra = new Phantom(EntityType.PHANTOM, level);

            extra.moveTo(
                    phantom.getX(),
                    phantom.getY(),
                    phantom.getZ(),
                    phantom.getYRot(),
                    phantom.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);

            applyConfig(extra, config);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(Phantom phantom, PhantomConfig config) {

        DoorBreakUtil.handleDoorBreaking(
                phantom,
                config.canBreakDoors,
                config.doorBreakMode
        );
        if (config.CustomName) {
            MobNameUtil.applyRandomName(phantom);
        }
        if (phantom.getAttribute(Attributes.MAX_HEALTH) != null)
            phantom.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (phantom.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            phantom.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage);

        if (phantom.getAttribute(Attributes.FLYING_SPEED) != null)
            phantom.getAttribute(Attributes.FLYING_SPEED)
                    .setBaseValue(config.movementSpeed);

        if (phantom.getAttribute(Attributes.FOLLOW_RANGE) != null)
            phantom.getAttribute(Attributes.FOLLOW_RANGE)
                    .setBaseValue(config.followRange);

        phantom.setHealth((float) config.health);

        if (config.fixedSize >= 0) {
            phantom.setPhantomSize(config.fixedSize);
        } else if (config.sizeMultiplier != 1.0D) {
            int newSize = (int) (phantom.getPhantomSize() * config.sizeMultiplier);
            phantom.setPhantomSize(Math.max(0, newSize));
        }

        if (config.fireImmune)
            phantom.setRemainingFireTicks(0);

        if (config.glowing)
            phantom.setGlowingTag(true);

    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        PhantomConfig config = ModConfigs.getPhantom();

        XPUtil.applyXpIfInstance(
                event,
                Phantom.class,
                config.xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Phantom phantom))
            return;

        if (!(phantom.level() instanceof ServerLevel level))
            return;

        PhantomConfig config = ModConfigs.getPhantom();

        LootUtil.applyLootMultiplier(
                event,
                level,
                phantom,
                config.lootMultiplier
        );
    }

    @SubscribeEvent
    public void onDamaged(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Phantom phantom))
            return;

        if (!(phantom.level() instanceof ServerLevel level))
            return;

        PhantomConfig config = ModConfigs.getPhantom();

        ReinforcementUtil.trySpawnReinforcement(
                phantom,
                level,
                config.reinforcementChance,
                4
        );
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Phantom phantom))
            return;

        if (phantom.level().isClientSide)
            return;

        PhantomConfig config = ModConfigs.getPhantom();

        Level level = phantom.level();

        if (!phantom.isAlive())
            return;

        if (!config.burnInDaylight) {

            if (phantom.isOnFire())
                phantom.clearFire();

        } else {

            if (level.isDay()
                    && level.canSeeSky(phantom.blockPosition())) {

                float brightness = phantom.getLightLevelDependentMagicValue();

                if (brightness > 0.5F &&
                        phantom.getRandom().nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F) {

                    phantom.igniteForSeconds(8.0F);
                }
            }
        }

        double circleMultiplier = config.circleSpeedMultiplier;
        double swoopMultiplier = config.swoopSpeedMultiplier;

        if (circleMultiplier == 1.0D && swoopMultiplier == 1.0D)
            return;

        var target = phantom.getTarget();
        var motion = phantom.getDeltaMovement();

        if (target != null) {

            double distance = phantom.distanceTo(target);

            // SWOOP detection (close dive range)
            if (distance < 16.0D) {

                if (swoopMultiplier != 1.0D) {
                    phantom.setDeltaMovement(
                            motion.scale(swoopMultiplier)
                    );
                }

            } else {

                if (circleMultiplier != 1.0D) {
                    phantom.setDeltaMovement(
                            motion.scale(circleMultiplier)
                    );
                }
            }

        } else {

            // No target = circle mode
            if (circleMultiplier != 1.0D) {
                phantom.setDeltaMovement(
                        motion.scale(circleMultiplier)
                );
            }
        }
    }

}