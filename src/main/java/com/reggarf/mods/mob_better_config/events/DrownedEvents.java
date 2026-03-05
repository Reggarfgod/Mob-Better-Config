package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.DrownedConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;

import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class DrownedEvents {

    @SubscribeEvent
    public void onSpawnCheck(MobSpawnEvent.PositionCheck event) {

        if (!(event.getEntity() instanceof Drowned drowned))
            return;

        DrownedConfig config = ModConfigs.getDrowned();

        if (!config.allowDaySpawn && drowned.level().isDay()) {
            event.setResult(MobSpawnEvent.PositionCheck.Result.FAIL);
            return;
        }

        if (config.requireDeepWater) {

            int seaLevel = drowned.level().getSeaLevel();
            int minY = seaLevel - config.deepWaterOffset;

            if (drowned.blockPosition().getY() >= minY) {
                event.setResult(MobSpawnEvent.PositionCheck.Result.FAIL);
            }
        }
    }

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Drowned drowned))
            return;

        if (!(drowned.level() instanceof ServerLevel level))
            return;

        DrownedConfig config = ModConfigs.getDrowned();

        if (drowned.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(drowned, config);
        applyEquipment(drowned, config);

        BossUtil.tryApplyBoss(
                drowned,
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

        // Spawn multiplier
        for (int i = 1; i < config.spawnMultiplier; i++) {

            Drowned extra = new Drowned(EntityType.DROWNED, level);

            extra.moveTo(
                    drowned.getX(),
                    drowned.getY(),
                    drowned.getZ(),
                    drowned.getYRot(),
                    drowned.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);

            applyConfig(extra, config);
            applyEquipment(extra, config);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(Drowned drowned, DrownedConfig config) {
        RandomSource random = drowned.level().getRandom();
        DoorBreakUtil.handleDoorBreaking(
                drowned,
                config.canBreakDoors,
                config.doorBreakMode
        );
        if (config.CustomName) {
            MobNameUtil.applyRandomName(drowned);
        }
        if (drowned.getAttribute(Attributes.MAX_HEALTH) != null)
            drowned.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (drowned.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            drowned.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage);

        if (drowned.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            drowned.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);

        if (drowned.getAttribute(Attributes.FOLLOW_RANGE) != null)
            drowned.getAttribute(Attributes.FOLLOW_RANGE)
                    .setBaseValue(config.followRange);

        drowned.setHealth((float) config.health);
        if (random.nextDouble() < config.randomArmorChance) {
            ArmorUtil.equipRandomArmor(drowned, random, 0.5f);
        }
    }

    private void applyEquipment(Drowned drowned, DrownedConfig config) {

        if (drowned.getMainHandItem().isEmpty()) {

            double roll = drowned.getRandom().nextDouble();

            if (roll < config.tridentChance) {
                drowned.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.TRIDENT));
            } else if (roll < config.tridentChance + config.fishingRodChance) {
                drowned.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.FISHING_ROD));
            }
        }

        if (drowned.getItemBySlot(EquipmentSlot.OFFHAND).isEmpty()) {

            if (drowned.getRandom().nextDouble() < config.nautilusShellChance) {
                drowned.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.NAUTILUS_SHELL));
                drowned.setGuaranteedDrop(EquipmentSlot.OFFHAND);
            }
        }
    }


    @SubscribeEvent
    public void onDamage(LivingDamageEvent.Pre event) {

        if (!(event.getSource().getDirectEntity() instanceof ThrownTrident trident))
            return;

        if (!(trident.getOwner() instanceof Drowned drowned))
            return;

        DrownedConfig config = ModConfigs.getDrowned();

        float newDamage = event.getNewDamage() * (float) config.tridentDamageMultiplier;

        event.setNewDamage(newDamage);
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Drowned drowned))
            return;

        if (drowned.level().isClientSide)
            return;

        DrownedConfig config = ModConfigs.getDrowned();

        if (!config.aggressiveSwimming)
            return;

        if (drowned.isInWater() && drowned.getTarget() != null) {

            drowned.setDeltaMovement(
                    drowned.getDeltaMovement().add(0.0D, config.swimUpBoost, 0.0D)
            );
        }

        DaylightBurnUtil.handleDaylightBurn(
                drowned,
                config.burnInDaylight,
                config.fireImmune,
                config.burnSeconds,
                true
        );
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        XPUtil.applyXpIfInstance(
                event,
                Drowned.class,
                ModConfigs.getDrowned().xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Drowned drowned))
            return;

        if (!(drowned.level() instanceof ServerLevel level))
            return;

        LootUtil.applyLootMultiplier(
                event,
                level,
                drowned,
                ModConfigs.getDrowned().lootMultiplier
        );
    }

    @SubscribeEvent
    public void onDamaged(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Drowned drowned))
            return;

        if (!(drowned.level() instanceof ServerLevel level))
            return;

        ReinforcementUtil.trySpawnReinforcement(
                drowned,
                level,
                ModConfigs.getDrowned().reinforcementChance,
                4
        );
    }

    @SubscribeEvent
    public void onEntityJoin(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof ThrownTrident trident))
            return;

        if (!(trident.getOwner() instanceof Drowned drowned))
            return;

        if (drowned.level().isClientSide)
            return;

        DrownedConfig config = ModConfigs.getDrowned();

        if (drowned.getTarget() == null)
            return;

        // Cancel vanilla motion
        trident.setDeltaMovement(0, 0, 0);

        // Recalculate direction like vanilla
        double d0 = drowned.getTarget().getX() - drowned.getX();
        double d1 = drowned.getTarget().getY(0.3333333333333333) - trident.getY();
        double d2 = drowned.getTarget().getZ() - drowned.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);

        // Apply YOUR config values
        trident.shoot(
                d0,
                d1 + d3 * 0.2D,
                d2,
                (float) config.tridentVelocity,
                (float) config.tridentInaccuracy
        );
    }
}