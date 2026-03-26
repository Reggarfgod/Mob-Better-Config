package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.ai.CustomBreakDoorGoal;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.PhantomConfig;
import com.reggarf.mods.mob_better_config.config.ZombieConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;

public class ZombieEvents {

    public static void onSpawn(Zombie zombie, ServerLevel level) {
        if (CommonMobHandler.isInitialized(zombie))
            return;
        CommonMobHandler.markInitialized(zombie);

        if (zombie instanceof ZombifiedPiglin || zombie instanceof Husk)
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

        CommonMobHandler.spawnMultiplier(zombie, level, config.spawnMultiplier);
    }

    private static void applyConfig(Zombie zombie) {

        ZombieConfig config = ModConfigs.getZombie();

        RandomSource random = zombie.level().getRandom();

        CommonMobHandler.applyCommonAttributes(
                zombie,
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

        if (random.nextDouble() < config.babyChance)
            zombie.setBaby(true);

    }



    public static void onTick(Zombie zombie) {

        if (zombie instanceof ZombifiedPiglin || zombie instanceof Husk)
            return;

        ZombieConfig config = ModConfigs.getZombie();

        CommonMobHandler.applyCommonTickBehaviors(
                zombie,
                config.burnInDaylight,
                config.fireImmune,
                config.sprintAbility,
                config.rageMode,
                false,
                config.nightBuff
        );
    }
    public static float modifyXP(float xp) {

        ZombieConfig config = ModConfigs.getZombie();
        //if (zombie instanceof ZombifiedPiglin);
        return xp * (float) config.xpMultiplier;
    }
    public static void onDrops(ServerLevel level, Zombie zombie) {

        if (zombie instanceof ZombifiedPiglin || zombie instanceof Husk)
            return;

        LootUtil.applyLootMultiplier(
                null,
                level,
                zombie,
                ModConfigs.getZombie().lootMultiplier
        );
    }
}