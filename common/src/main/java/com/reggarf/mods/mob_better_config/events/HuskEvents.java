package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.HuskConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.zombie.Husk;

import java.util.Map;
import java.util.WeakHashMap;

public class HuskEvents {

    private static final Map<Husk, Integer> WATER_TIMERS = new WeakHashMap<>();
    private static final String SPAWN_TAG = "mob_better_config_spawned";
    private static final String HUNGER_TAG = "mob_better_config_hunger_flag";
    // =========================
    // SPAWN
    // =========================
    public static void onSpawn(Husk husk, ServerLevel level) {

        if (CommonMobHandler.isInitialized(husk))
            return;

        CommonMobHandler.markInitialized(husk);

        HuskConfig config = ModConfigs.getHusk();

        CommonMobHandler.applyCommonAttributes(
                husk,
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

        if (config.fireImmune)
            husk.clearFire();

        BossUtil.tryApplyBoss(
                husk,
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

        // Desert Buff
        if (config.desertBuff &&
                husk.level().getBiome(husk.blockPosition())
                        .is(BiomeTags.HAS_DESERT_PYRAMID)) {

            husk.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH)
                    .setBaseValue(config.desertHealth);

            husk.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR)
                    .setBaseValue(config.desertarmor);

            husk.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE)
                    .setBaseValue(
                            husk.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE)
                                    .getBaseValue() * config.desertDamageBonus
                    );

            husk.setHealth(husk.getMaxHealth());
        }

        CommonMobHandler.spawnMultiplier(
                husk,
                level,
                config.spawnMultiplier
        );
    }

    public static void onDamage(LivingEntity target, LivingEntity attacker) {

        if (!(attacker instanceof Husk))
            return;

        target.addTag(HUNGER_TAG);
    }


    public static void onTargetTick(LivingEntity target) {


        if (!target.getTags().contains(HUNGER_TAG))
            return;

        target.removeTag(HUNGER_TAG);

        HuskConfig config = ModConfigs.getHusk();

        if (!config.enableHunger)
            return;


        if (target.hasEffect(MobEffects.HUNGER))
            target.removeEffect(MobEffects.HUNGER);

        float difficulty = 1.0F;

        if (target.level() instanceof ServerLevel serverLevel) {
            difficulty = serverLevel
                    .getCurrentDifficultyAt(target.blockPosition())
                    .getEffectiveDifficulty();
        }

        int duration = (int) (config.hungerDuration * difficulty);

        target.addEffect(new MobEffectInstance(
                MobEffects.HUNGER,
                duration,
                config.hungerAmplifier
        ));
    }

    public static float modifyXP(float xp) {
        HuskConfig config = ModConfigs.getHusk();
        return xp * (float) config.xpMultiplier;
    }

    // =========================
    // DROPS
    // =========================
    public static void onDrops(ServerLevel level, Husk husk) {

        HuskConfig config = ModConfigs.getHusk();

        LootUtil.applyLootMultiplier(
                null,
                level,
                husk,
                config.lootMultiplier
        );
    }
}