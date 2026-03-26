package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.MagmaCubeConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.util.*;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.MagmaCube;

public class FabricMagmaCubeEvents {

    public static void register() {

        /*
         * SPAWN EVENT
         */
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (!(entity instanceof MagmaCube cube))
                return;

            if (!(level instanceof ServerLevel serverLevel))
                return;

            MagmaCubeConfig config = ModConfigs.getMagmaCube();

            if (cube.getTags().contains("mob_better_config_spawned"))
                return;

            cube.addTag("mob_better_config_spawned");

            applyConfig(cube, config);

            BossUtil.tryApplyBoss(
                    cube,
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

                MagmaCube extra = new MagmaCube(EntityType.MAGMA_CUBE, serverLevel);

                extra.snapTo(
                        cube.getX(),
                        cube.getY(),
                        cube.getZ(),
                        cube.getYRot(),
                        cube.getXRot()
                );

                extra.addTag("mob_better_config_spawned");

                applyConfig(extra, config);

                serverLevel.addFreshEntity(extra);
            }
        });

        /*
         * DAMAGE EVENT
         */
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {

            if (!(source.getEntity() instanceof MagmaCube cube))
                return true;

            MagmaCubeConfig config = ModConfigs.getMagmaCube();

            if (!config.damageScalesWithSize)
                entity.setLastHurtByMob(cube);

//            ReinforcementUtil.trySpawnReinforcement(
//                    cube,
//                    (ServerLevel) cube.level(),
//                    config.reinforcementChance,
//                    4
//            );

            return true;
        });

        /*
         * DEATH EVENT (XP + LOOT)
         */
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof MagmaCube cube))
                return;

            if (!(cube.level() instanceof ServerLevel level))
                return;

            MagmaCubeConfig config = ModConfigs.getMagmaCube();

            double multiplier = ModConfigs.getMagmaCube().xpMultiplier;

            if (multiplier > 1.0) {

                int extraXP = (int)((multiplier - 1.0) * 10);

                if (extraXP > 0) {
                    ExperienceOrb.award(level, cube.position(), extraXP);
                }

            }

            LootUtil.applyLootMultiplier(
                    null,
                    level,
                    cube,
                    config.lootMultiplier
            );
        });


        ServerTickEvents.END_SERVER_TICK.register((MinecraftServer server) -> {

            for (ServerLevel level : server.getAllLevels()) {

                for (MagmaCube cube : level.getEntitiesOfClass(
                        MagmaCube.class,
                        level.getWorldBorder().getCollisionShape().bounds()
                )) {

                    if (level.getDifficulty() == Difficulty.PEACEFUL &&
                            ModConfigs.getMagmaCube().despawnInPeaceful) {

                        cube.discard();
                    }
                }
            }
        });
    }

    /*
     * APPLY CONFIG
     */
    private static void applyConfig(MagmaCube cube, MagmaCubeConfig config) {

        int size = cube.getSize();

        if (config.fixedSize > 0)
            size = Mth.clamp(config.fixedSize, config.minSize, config.maxSize);

        size = (int) Mth.clamp(size * config.sizeMultiplier, config.minSize, config.maxSize);

        cube.setSize(size, true);

        if (cube.getAttribute(Attributes.MAX_HEALTH) != null) {
            double health = cube.getMaxHealth() * config.healthMultiplier;
            cube.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
            cube.setHealth((float) health);
        }

        if (cube.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
            double damage = cube.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
            damage *= config.attackDamageMultiplier;
            cube.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);
        }

        if (cube.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
            cube.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.baseMovementSpeed + (size * config.speedPerSize));
        }

        if (cube.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            cube.getAttribute(Attributes.ATTACK_KNOCKBACK)
                    .setBaseValue(config.knockbackStrength);

        if (config.glowing)
            cube.setGlowingTag(true);

        if (config.CustomName)
            MobNameUtil.applyRandomName(cube);
    }
}