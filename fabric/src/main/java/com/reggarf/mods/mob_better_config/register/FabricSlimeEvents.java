package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.SlimeConfig;
import com.reggarf.mods.mob_better_config.util.*;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;

public class FabricSlimeEvents {

    private static final String SPAWN_TAG = "mob_better_config_spawned";

    public static void register() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (!(entity instanceof Slime slime))
                return;

            if (!(level instanceof ServerLevel serverLevel))
                return;

            SlimeConfig config = ModConfigs.getSlime();

            if (slime.getTags().contains(SPAWN_TAG))
                return;

            slime.addTag(SPAWN_TAG);

            applyConfig(slime, config);

            BossUtil.tryApplyBoss(
                    slime,
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

                Slime extra = new Slime(EntityType.SLIME, serverLevel);

                extra.moveTo(
                        slime.getX(),
                        slime.getY(),
                        slime.getZ(),
                        slime.getYRot(),
                        slime.getXRot()
                );

                extra.addTag(SPAWN_TAG);

                applyConfig(extra, config);

                serverLevel.addFreshEntity(extra);
            }
        });

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {

            if (!(source.getEntity() instanceof Slime slime))
                return true;

            SlimeConfig config = ModConfigs.getSlime();

            if (!config.damageScalesWithSize)
                entity.setLastHurtByMob(slime);

            return true;
        });

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Slime slime))
                return;

            if (!(slime.level() instanceof ServerLevel level))
                return;

            SlimeConfig config = ModConfigs.getSlime();

            // Apply XP multiplier
            double multiplier = ModConfigs.getSlime().xpMultiplier;

            if (multiplier > 1.0) {

                int extraXP = (int)((multiplier - 1.0) * 10); // Blaze vanilla XP ≈ 10

                if (extraXP > 0) {
                    ExperienceOrb.award(level, slime.position(), extraXP);
                }

            }

            LootUtil.applyLootMultiplier(
                    null,
                    level,
                    slime,
                    config.lootMultiplier
            );
        });

        ServerTickEvents.END_SERVER_TICK.register((MinecraftServer server) -> {

            for (ServerLevel level : server.getAllLevels()) {

                for (Slime slime : level.getEntitiesOfClass(
                        Slime.class,
                        level.getWorldBorder().getCollisionShape().bounds())) {

                    SlimeConfig config = ModConfigs.getSlime();

                    if (!config.despawnInPeaceful)
                        continue;

                    if (slime.level().getDifficulty() == Difficulty.PEACEFUL)
                        slime.discard();
                }
            }
        });
    }

    private static void applyConfig(Slime slime, SlimeConfig config) {

        int size = slime.getSize();

        if (config.fixedSize > 0)
            size = Mth.clamp(config.fixedSize, config.minSize, config.maxSize);

        size = (int) Mth.clamp(size * config.sizeMultiplier, config.minSize, config.maxSize);

        slime.setSize(size, true);

        double health = slime.getMaxHealth() * config.healthMultiplier;

        if (slime.getAttribute(Attributes.MAX_HEALTH) != null)
            slime.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);

        slime.setHealth((float) health);

        if (slime.getAttribute(Attributes.ATTACK_DAMAGE) != null) {

            double damage = slime.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
            damage *= config.attackDamageMultiplier;

            slime.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);
        }

        if (slime.getAttribute(Attributes.MOVEMENT_SPEED) != null) {

            double speed = config.baseMovementSpeed + (size * config.speedPerSize);
            slime.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(speed);
        }

        if (slime.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            slime.getAttribute(Attributes.ATTACK_KNOCKBACK)
                    .setBaseValue(config.knockbackStrength);

        if (config.fireImmune)
            slime.setRemainingFireTicks(0);

        if (config.glowing)
            slime.setGlowingTag(true);

        if (config.CustomName)
            MobNameUtil.applyRandomName(slime);
    }
}