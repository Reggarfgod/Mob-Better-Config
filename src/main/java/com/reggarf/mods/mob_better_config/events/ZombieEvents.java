package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.ai.CustomBreakDoorGoal;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.ZombieConfig;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;


import net.minecraft.world.entity.monster.zombie.Husk;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class ZombieEvents {

    @SubscribeEvent
    public void onZombieJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Zombie zombie) || zombie instanceof ZombifiedPiglin || zombie instanceof Husk)
            return;

        applyConfig(zombie);

        ZombieConfig config = ModConfigs.getZombie();

        BossUtil.tryApplyBoss(
                zombie,
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

        if (zombie.level() instanceof ServerLevel level) {

            for (int i = 1; i < config.spawnMultiplier; i++) {

                Zombie extra = new Zombie(EntityType.ZOMBIE, level);

                extra.snapTo(
                        zombie.getX(),
                        zombie.getY(),
                        zombie.getZ(),
                        zombie.getYRot(),
                        zombie.getXRot()
                );

                level.addFreshEntity(extra);
            }
        }
    }

    private void applyConfig(Zombie zombie) {

        ZombieConfig config = ModConfigs.getZombie();
        RandomSource random = zombie.level().getRandom();

        if (config.CustomName) {
            MobNameUtil.applyRandomName(zombie);
        }

        if (zombie.getAttribute(Attributes.MAX_HEALTH) != null)
            zombie.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.health);

        if (zombie.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            zombie.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(config.attackDamage);

        if (zombie.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            zombie.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(config.movementSpeed);

        if (zombie.getAttribute(Attributes.FOLLOW_RANGE) != null)
            zombie.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(config.followRange);

        if (zombie.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            zombie.getAttribute(Attributes.KNOCKBACK_RESISTANCE)
                    .setBaseValue(config.knockbackResistance);

        if (zombie.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            zombie.getAttribute(Attributes.ATTACK_KNOCKBACK)
                    .setBaseValue(config.attackKnockback);

//        if (zombie.getAttribute(Attributes.ARMOR) != null)
//            zombie.getAttribute(Attributes.ARMOR)
//                    .setBaseValue(config.armor);

        if (zombie.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            zombie.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                    .setBaseValue(config.reinforcementChance);

        zombie.setHealth(config.health);

        if (config.glowing)
            zombie.setGlowingTag(true);

        if (!config.burnInDaylight)
            zombie.clearFire();

        if (random.nextDouble() < config.babyChance)
            zombie.setBaby(true);

        if (config.fireImmune)
            zombie.setRemainingFireTicks(0);

        if (config.randomArmor && random.nextDouble() < config.armorChance) {
            ArmorUtil.equipRandomArmor(zombie, random, 0.5f);
        }

        applyDoorBreakMode(zombie, config);
    }

    private void applyDoorBreakMode(Zombie zombie, ZombieConfig config) {

        zombie.goalSelector.removeAllGoals(goal ->
                goal.getClass().getName().contains("BreakDoor"));

        if (!config.canBreakDoors || config.doorBreakMode == 0) {
            zombie.setCanBreakDoors(false);
            return;
        }

        zombie.setCanBreakDoors(true);

        int breakTicks;

        switch (config.doorBreakMode) {
            case 1 -> breakTicks = 400;
            case 2 -> breakTicks = 300;
            case 3 -> breakTicks = 240;
            case 4 -> breakTicks = 120;
            case 5 -> breakTicks = 60;
            default -> breakTicks = 240;
        }

        zombie.goalSelector.addGoal(
                1,
                new CustomBreakDoorGoal(zombie, breakTicks)
        );
    }

    @SubscribeEvent
    public void onZombieTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Zombie zombie) || zombie instanceof ZombifiedPiglin || zombie instanceof Husk)
            return;

        ZombieConfig config = ModConfigs.getZombie();

        if (!config.burnInDaylight) {

            if (zombie.isOnFire()) {
                zombie.clearFire();
            }

            zombie.setRemainingFireTicks(0);
        }

        if (config.fireImmune && zombie.isOnFire())
            zombie.clearFire();

        if (config.sprintAbility && zombie.getTarget() != null)
            zombie.setSprinting(true);

        if (config.rageMode) {

            if (zombie.getHealth() < zombie.getMaxHealth() * 0.3F) {

                zombie.addEffect(
                        new MobEffectInstance(
                                MobEffects.SPEED,
                                40,
                                1,
                                false,
                                false
                        )
                );

                zombie.addEffect(
                        new MobEffectInstance(
                                MobEffects.STRENGTH,
                                40,
                                1,
                                false,
                                false
                        )
                );
            }
        }
    }

    @SubscribeEvent
    public void onZombieDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Zombie zombie) || zombie instanceof ZombifiedPiglin || zombie instanceof Husk)
            return;

        if (!(zombie.level() instanceof ServerLevel level))
            return;

        double multiplier = ModConfigs.getZombie().lootMultiplier;

        LootUtil.applyLootMultiplier(event, level, zombie, multiplier);
    }
}