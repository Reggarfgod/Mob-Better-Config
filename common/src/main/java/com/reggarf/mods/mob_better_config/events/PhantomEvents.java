package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.HuskConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.PhantomConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;

public class PhantomEvents {

    public static void onSpawn(Phantom phantom, ServerLevel level) {
        if (CommonMobHandler.isInitialized(phantom))
            return;
        CommonMobHandler.markInitialized(phantom);

        PhantomConfig config = ModConfigs.getPhantom();

        applyConfig(phantom, config);

        BossUtil.tryApplyBoss(
                phantom,
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

            extra.addTag("mob_better_config_spawned");

            applyConfig(extra, config);

            level.addFreshEntity(extra);
        }
    }

    private static void applyConfig(Phantom phantom, PhantomConfig config) {

        CommonMobHandler.applyCommonAttributes(
                phantom,
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

        if (config.fixedSize >= 0) {
            phantom.setPhantomSize(config.fixedSize);
        } else if (config.sizeMultiplier != 1.0D) {
            int newSize = (int) (phantom.getPhantomSize() * config.sizeMultiplier);
            phantom.setPhantomSize(Math.max(0, newSize));
        }

        if (config.fireImmune)
            phantom.setRemainingFireTicks(0);
    }

    public static float modifyXP(float xp) {

        PhantomConfig config = ModConfigs.getPhantom();

        return xp * (float) config.xpMultiplier;
    }

    public static void onDrops(ServerLevel level, Phantom phantom) {

        PhantomConfig config = ModConfigs.getPhantom();

        LootUtil.applyLootMultiplier(
                null,
                level,
                phantom,
                config.lootMultiplier
        );
    }

    public static void onTick(Phantom phantom) {

        if (phantom.level().isClientSide)
            return;

        PhantomConfig config = ModConfigs.getPhantom();
        Level level = phantom.level();

        if (!phantom.isAlive())
            return;

        // Daylight burning
        if (!config.burnInDaylight) {

            if (phantom.isOnFire())
                phantom.clearFire();

        } else {

            if (level.isDay() && level.canSeeSky(phantom.blockPosition())) {

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

            if (circleMultiplier != 1.0D) {
                phantom.setDeltaMovement(
                        motion.scale(circleMultiplier)
                );
            }
        }
    }
}