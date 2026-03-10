package com.reggarf.mods.mob_better_config.handle;

import com.reggarf.mods.mob_better_config.ai.CustomBreakDoorGoal;

import com.reggarf.mods.mob_better_config.util.DoorBreakUtil;
import com.reggarf.mods.mob_better_config.util.MobNameUtil;
import com.reggarf.mods.mob_better_config.util.LootUtil;
import com.reggarf.mods.mob_better_config.config.ModConfigs;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;


public class CommonMobHandler {

    public static void applyCommonAttributes(
            Mob mob,
            double health,
            double armor,
            double armorToughness,
            double attackDamage,
            double attackSpeed,
            double movementSpeed,
            double followRange,
            double knockbackResistance,
            double attackKnockback,
            double stepHeight,
            double gravity,
            boolean glowing,
            boolean customName,
            boolean doorBreak,
            int doorBreakMode
    ) {

        if (customName) {
            MobNameUtil.applyRandomName(mob);
        }

        set(mob, Attributes.MAX_HEALTH, health);
        set(mob, Attributes.ARMOR, armor);
        set(mob, Attributes.ARMOR_TOUGHNESS, armorToughness);
        set(mob, Attributes.ATTACK_DAMAGE, attackDamage);
        set(mob, Attributes.ATTACK_SPEED, attackSpeed);
        set(mob, Attributes.MOVEMENT_SPEED, movementSpeed);
        set(mob, Attributes.FOLLOW_RANGE, followRange);
        set(mob, Attributes.KNOCKBACK_RESISTANCE, knockbackResistance);
        set(mob, Attributes.ATTACK_KNOCKBACK, attackKnockback);
        set(mob, Attributes.STEP_HEIGHT, stepHeight);
        set(mob, Attributes.GRAVITY, gravity);

        mob.setHealth((float) health);

        if (glowing)
            mob.setGlowingTag(true);

        // Door Breaking
        DoorBreakUtil.handleDoorBreaking(
                mob,
                doorBreak,
                doorBreakMode
        );
    }

    // FOOT MOB BEHAVIORS
    public static void applyCommonTickBehaviors(
            Mob mob,
            boolean burnInDaylight,
            boolean fireImmune,
            boolean sprintAbility,
            boolean rageMode,
            boolean jumpBoost,
            boolean nightBuff
    ) {

        if (!burnInDaylight) {
            if (mob.isOnFire())
                mob.clearFire();
            mob.setRemainingFireTicks(0);
        }

        if (fireImmune && mob.isOnFire())
            mob.clearFire();

        if (sprintAbility && mob.getTarget() != null)
            mob.setSprinting(true);

        if (jumpBoost && mob.onGround() && mob.getTarget() != null) {
            mob.addEffect(new MobEffectInstance(
                    MobEffects.JUMP,
                    40,
                    1,
                    false,
                    false
            ));
        }

        if (rageMode && mob.getHealth() < mob.getMaxHealth() * 0.3F) {

            mob.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SPEED,
                    40,
                    1,
                    false,
                    false
            ));

            mob.addEffect(new MobEffectInstance(
                    MobEffects.DAMAGE_BOOST,
                    40,
                    1,
                    false,
                    false
            ));
        }

        if (nightBuff && mob.level().isNight()) {

            mob.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SPEED,
                    60,
                    0,
                    false,
                    false
            ));
        }
    }


    public static void spawnMultiplier(Mob mob, ServerLevel level, int multiplier) {

        for (int i = 1; i < multiplier; i++) {

            Mob extra = (Mob) mob.getType().create(level);

            if (extra == null)
                return;

            extra.moveTo(
                    mob.getX(),
                    mob.getY(),
                    mob.getZ(),
                    mob.getYRot(),
                    mob.getXRot()
            );

            level.addFreshEntity(extra);
        }
    }

    private static void set(Mob mob, net.minecraft.core.Holder<?> attribute, double value) {

        AttributeInstance instance = mob.getAttribute((net.minecraft.core.Holder) attribute);

        if (instance != null)
            instance.setBaseValue(value);
    }
}