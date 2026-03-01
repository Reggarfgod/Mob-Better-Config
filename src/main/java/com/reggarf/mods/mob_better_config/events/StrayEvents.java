package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.StrayConfig;
import com.reggarf.mods.mob_better_config.util.ArmorUtil;
import com.reggarf.mods.mob_better_config.util.LootUtil;
import com.reggarf.mods.mob_better_config.util.MobNameUtil;
import com.reggarf.mods.mob_better_config.util.ReinforcementUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class StrayEvents {

    @SubscribeEvent
    public void onJoin(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof Stray stray))
            return;

        if (!(stray.level() instanceof ServerLevel level))
            return;

        StrayConfig config = ModConfigs.getStray();

        if (stray.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(stray);

        for (int i = 1; i < config.spawnMultiplier; i++) {

            Stray extra = new Stray(EntityType.STRAY, level);

            extra.moveTo(
                    stray.getX(),
                    stray.getY(),
                    stray.getZ(),
                    stray.getYRot(),
                    stray.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);
            level.addFreshEntity(extra);
        }
    }
    @SubscribeEvent
    public void onArrowDamage(net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre event) {

        if (!(event.getSource().getDirectEntity() instanceof net.minecraft.world.entity.projectile.AbstractArrow arrow))
            return;

        if (!(arrow.getOwner() instanceof Stray))
            return;

        StrayConfig config = ModConfigs.getStray();

        if (config.arrowDamageMultiplier == 1.0D)
            return;

        float newDamage = (float)(event.getNewDamage() * config.arrowDamageMultiplier);

        event.setNewDamage(newDamage);
    }
    private void applyConfig(Stray stray) {

        StrayConfig config = ModConfigs.getStray();
        if (config.CustomName) {
            MobNameUtil.applyRandomName(stray);
        }
        if (stray.getAttribute(Attributes.MAX_HEALTH) != null)
            stray.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.health);

        if (stray.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            stray.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(config.attackDamage);

        if (stray.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            stray.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(config.movementSpeed);

        if (stray.getAttribute(Attributes.FOLLOW_RANGE) != null)
            stray.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(config.followRange);

        stray.setHealth(config.health);

        if (config.fireImmune)
            stray.setRemainingFireTicks(0);

        if (config.glowing)
            stray.setGlowingTag(true);

        // Random Armor
        if (config.randomArmor &&
                stray.getRandom().nextDouble() < config.armorChance) {

            ArmorUtil.equipRandomArmor(stray, stray.getRandom(), 0.4F);
        }
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Stray stray))
            return;

        StrayConfig config = ModConfigs.getStray();

        if (!config.burnInDaylight && stray.isOnFire()) {
            stray.clearFire();
        }
    }

    @SubscribeEvent
    public void onArrowSpawn(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof Arrow arrow))
            return;

        if (!(arrow.getOwner() instanceof Stray))
            return;

        StrayConfig config = ModConfigs.getStray();

        // Clear vanilla potion
        arrow.getPickupItemStackOrigin()
                .set(net.minecraft.core.component.DataComponents.POTION_CONTENTS,
                        PotionContents.EMPTY);

        if (!config.enableSlowness)
            return;

        arrow.addEffect(new MobEffectInstance(
                MobEffects.MOVEMENT_SLOWDOWN,
                config.slownessDuration,
                config.slownessAmplifier
        ));
    }

    @SubscribeEvent
    public void onDamaged(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Stray stray))
            return;

        if (!(stray.level() instanceof ServerLevel level))
            return;

        StrayConfig config = ModConfigs.getStray();

        ReinforcementUtil.trySpawnReinforcement(
                stray,
                level,
                config.reinforcementChance,
                4
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Stray stray))
            return;

        if (!(stray.level() instanceof ServerLevel level))
            return;

        StrayConfig config = ModConfigs.getStray();

        LootUtil.applyLootMultiplier(
                event,
                level,
                stray,
                config.lootMultiplier
        );
    }
}