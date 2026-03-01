package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.WardenConfig;
import com.reggarf.mods.mob_better_config.util.LootUtil;
import com.reggarf.mods.mob_better_config.util.MobNameUtil;
import com.reggarf.mods.mob_better_config.util.ReinforcementUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class WardenEvents {

    @SubscribeEvent
    public void onJoin(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof Warden warden))
            return;

        if (!(warden.level() instanceof ServerLevel level))
            return;

        WardenConfig config = ModConfigs.getWarden();

        if (warden.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(warden);

        for (int i = 1; i < config.spawnMultiplier; i++) {

            Warden extra = new Warden(EntityType.WARDEN, level);

            extra.moveTo(
                    warden.getX(),
                    warden.getY(),
                    warden.getZ(),
                    warden.getYRot(),
                    warden.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(Warden warden) {

        WardenConfig config = ModConfigs.getWarden();
        if (config.CustomName) {
            MobNameUtil.applyRandomName(warden);
        }
        if (warden.getAttribute(Attributes.MAX_HEALTH) != null)
            warden.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (warden.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            warden.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage);

        if (warden.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            warden.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);

        if (warden.getAttribute(Attributes.FOLLOW_RANGE) != null)
            warden.getAttribute(Attributes.FOLLOW_RANGE)
                    .setBaseValue(config.followRange);

        // Knockback Resistance
        if (warden.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            warden.getAttribute(Attributes.KNOCKBACK_RESISTANCE)
                    .setBaseValue(config.knockbackResistance);

        // Attack Knockback
        if (warden.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            warden.getAttribute(Attributes.ATTACK_KNOCKBACK)
                    .setBaseValue(config.attackKnockback);

        warden.setHealth(config.health);

        if (config.fireImmune)
            warden.setRemainingFireTicks(0);

        if (config.glowing)
            warden.setGlowingTag(true);
    }


    @SubscribeEvent
    public void onDamage(LivingDamageEvent.Pre event) {

        if (!(event.getSource().getEntity() instanceof Warden))
            return;

        WardenConfig config = ModConfigs.getWarden();

        if (config.sonicBoomDamageMultiplier != 1.0D) {

            float newDamage = (float)
                    (event.getNewDamage() * config.sonicBoomDamageMultiplier);

            event.setNewDamage(newDamage);
        }
    }


    @SubscribeEvent
    public void onWardenHurt(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Warden warden))
            return;

        if (!(warden.level() instanceof ServerLevel level))
            return;

        WardenConfig config = ModConfigs.getWarden();

        DamageSource source = event.getSource();
        LivingEntity attacker = source.getEntity() instanceof LivingEntity l ? l : null;

        if (attacker == null)
            return;

        // Custom anger boost
        warden.increaseAngerAt(
                attacker,
                config.defaultAnger + config.onHurtAngerBoost,
                false
        );

        ReinforcementUtil.trySpawnReinforcement(
                warden,
                level,
                config.reinforcementChance,
                8
        );
    }


    @SubscribeEvent
    public void onProjectileDamage(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Warden warden))
            return;

        if (!(event.getSource().getDirectEntity() instanceof net.minecraft.world.entity.projectile.Projectile projectile))
            return;

        if (!(projectile.getOwner() instanceof LivingEntity attacker))
            return;

        WardenConfig config = ModConfigs.getWarden();

        warden.increaseAngerAt(
                attacker,
                config.projectileAnger,
                true
        );
    }


    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Warden warden))
            return;

        if (!(warden.level() instanceof ServerLevel level))
            return;

        WardenConfig config = ModConfigs.getWarden();

        // Custom darkness
        if (config.enableDarkness &&
            (warden.tickCount + warden.getId()) % config.darknessInterval == 0) {

            AABB box = new AABB(warden.blockPosition())
                    .inflate(config.darknessRadius);

            for (LivingEntity target :
                    level.getEntitiesOfClass(LivingEntity.class, box)) {

                if (target == warden)
                    continue;

                target.addEffect(new MobEffectInstance(
                        MobEffects.DARKNESS,
                        config.darknessDuration,
                        0,
                        false,
                        false
                ));
            }
        }

        // Custom sonic boom cooldown override
        if (warden.getTarget() != null &&
            warden.tickCount % config.sonicBoomInitialDelay == 0) {

            SonicBoom.setCooldown(warden, config.sonicBoomCooldown);
        }
    }


    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Warden warden))
            return;

        if (!(warden.level() instanceof ServerLevel level))
            return;

        WardenConfig config = ModConfigs.getWarden();

        LootUtil.applyLootMultiplier(
                event,
                level,
                warden,
                config.lootMultiplier
        );
    }
}