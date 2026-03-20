package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.WitchConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.phys.AABB;

public class WitchEvents {

    public static void onSpawn(Witch witch, ServerLevel level) {
        if (CommonMobHandler.isInitialized(witch))
            return;

        CommonMobHandler.markInitialized(witch);
        WitchConfig config = ModConfigs.getWitch();

        applyAll(witch, config);

        // Boss
        BossUtil.tryApplyBoss(
                witch,
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

        CommonMobHandler.spawnMultiplier(witch, level, config.spawnMultiplier);
    }

    private static void applyAll(Witch witch, WitchConfig config) {

        CommonMobHandler.applyCommonAttributes(
                witch,
                config.health,
                config.armor,
                0.0,
                config.attackDamage,
                0.0,
                config.movementSpeed,
                config.followRange,
                config.knockbackResistance,
                config.attackKnockback,
                0.6,
                0.08,
                config.glowing,
                config.CustomName,
                false,
                0,
                config.reinforcementChance
        );

        witch.setHealth((float) config.health);

        if (config.fireImmune)
            witch.setRemainingFireTicks(0);
    }

    public static void onTick(Witch witch) {

        WitchConfig config = ModConfigs.getWitch();

        CommonMobHandler.applyCommonTickBehaviors(
                witch,
                false,
                config.fireImmune,
                false,
                false,
                false,
                false
        );


    }

    // Potion impact damage
    public static void onPotionImpact(ThrownPotion potion, ServerLevel level) {

        if (!(potion.getOwner() instanceof Witch witch))
            return;

        WitchConfig config = ModConfigs.getWitch();

        if (config.potionDamageMultiplier == 1.0D)
            return;

        for (LivingEntity target : level.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(potion.blockPosition()).inflate(4)
        )) {

            if (target == witch)
                continue;

            float extraDamage = (float) (4.0F * (config.potionDamageMultiplier - 1.0D));

            if (extraDamage > 0)
                target.hurt(level.damageSources().magic(), extraDamage);
        }
    }

    public static float modifyXP(float xp) {
        return xp * (float) ModConfigs.getWitch().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, Witch witch) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                witch,
                ModConfigs.getWitch().lootMultiplier
        );
    }
}