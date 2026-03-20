package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ElderGuardianConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.BossUtil;
import com.reggarf.mods.mob_better_config.util.LootUtil;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ElderGuardian;

public class ElderGuardianEvents {

    public static void onJoin(ElderGuardian elder, ServerLevel level) {
        if (CommonMobHandler.isInitialized(elder))
            return;
        CommonMobHandler.markInitialized(elder);

        ElderGuardianConfig config = ModConfigs.getElderGuardian();

        CommonMobHandler.applyCommonAttributes(
                elder,
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
                config.customName,
                config.canBreakDoors,
                config.doorBreakMode,
                config.reinforcementChance
        );

        BossUtil.tryApplyBoss(
                elder,
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
    }

    public static float modifyLaserDamage(DamageSource source, float damage) {

        if (!(source.getEntity() instanceof ElderGuardian))
            return damage;

        if (!source.is(DamageTypes.INDIRECT_MAGIC))
            return damage;

        ElderGuardianConfig config = ModConfigs.getElderGuardian();

        if (!config.enableLaser)
            return 0F;

        return damage * (float) config.laserDamageMultiplier;
    }

    public static void applyThorns(LivingEntity victim, DamageSource source) {

        if (!(victim instanceof ElderGuardian guardian))
            return;

        if (guardian.isMoving())
            return;

        if (source.is(DamageTypes.THORNS))
            return;

        if (source.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS))
            return;

        if (!(source.getDirectEntity() instanceof LivingEntity attacker))
            return;

        ElderGuardianConfig config = ModConfigs.getElderGuardian();

        if (!config.enableThorns)
            return;

        attacker.hurt(
                guardian.damageSources().thorns(guardian),
                (float) config.thornsDamage
        );
    }

    public static void onDrops(ServerLevel level, ElderGuardian elder) {

        ElderGuardianConfig config = ModConfigs.getElderGuardian();

        LootUtil.applyLootMultiplier(
                null,
                level,
                elder,
                config.lootMultiplier
        );
    }
}