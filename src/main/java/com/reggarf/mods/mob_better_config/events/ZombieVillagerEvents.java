package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.ZombieVillagerConfig;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class ZombieVillagerEvents {

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof ZombieVillager zv))
            return;

        if (!(zv.level() instanceof ServerLevel level))
            return;

        ZombieVillagerConfig config = ModConfigs.getZombieVillager();

        //Prevent multiplied mobs from multiplying again
        if (NbtUtil.getBooleanSafe(zv.getPersistentData(),"mob_better_config_spawned"))
            return;

        applyConfig(zv);
        BossUtil.tryApplyBoss(
                zv,
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

            ZombieVillager extra = new ZombieVillager(EntityType.ZOMBIE_VILLAGER, level);

            extra.snapTo(
                    zv.getX(),
                    zv.getY(),
                    zv.getZ(),
                    zv.getYRot(),
                    zv.getXRot()
            );

            // Mark so it doesn't multiply again
            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);

            level.addFreshEntity(extra);
        }
    }
    private void applyConfig(ZombieVillager zv) {

        ZombieVillagerConfig config = ModConfigs.getZombieVillager();
        RandomSource random = zv.level().getRandom();
        if (config.CustomName) {
            MobNameUtil.applyRandomName(zv);
        }
        if (zv.getAttribute(Attributes.MAX_HEALTH) != null)
            zv.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.health);

        if (zv.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            zv.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(config.attackDamage);

        if (zv.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            zv.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(config.movementSpeed);

        if (zv.getAttribute(Attributes.FOLLOW_RANGE) != null)
            zv.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(config.followRange);

        if (zv.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            zv.getAttribute(Attributes.KNOCKBACK_RESISTANCE)
                    .setBaseValue(config.knockbackResistance);

        if (zv.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            zv.getAttribute(Attributes.ATTACK_KNOCKBACK)
                    .setBaseValue(config.attackKnockback);

//        if (zv.getAttribute(Attributes.ARMOR) != null)
//            zv.getAttribute(Attributes.ARMOR).setBaseValue(config.armor);

        if (zv.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            zv.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                    .setBaseValue(config.reinforcementChance);

        zv.setHealth(config.health);

        if (!config.burnInDaylight)
            zv.clearFire();

        if (config.fireImmune)
            zv.setRemainingFireTicks(0);

        if (config.glowing)
            zv.setGlowingTag(true);

        if (random.nextDouble() < config.randomArmorChance)
            ArmorUtil.equipRandomArmor(zv, random, 0.5f);
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof ZombieVillager zv))
            return;

        ZombieVillagerConfig config = ModConfigs.getZombieVillager();

        if (!config.burnInDaylight && zv.isOnFire())
            zv.clearFire();

        if (config.fireImmune && zv.isOnFire())
            zv.clearFire();
    }
    @SubscribeEvent
    public void onZombieVillagerTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof ZombieVillager zv))
            return;

        if (!(zv.level() instanceof ServerLevel))
            return;

        ZombieVillagerConfig config = ModConfigs.getZombieVillager();

        if (!zv.isConverting())
            return;

        double multiplier = config.cureSpeedMultiplier;

        if (multiplier == 1.0D)
            return;

        if (multiplier > 1.0D) {

            int extraTicks = (int) Math.floor(multiplier - 1.0D);

            for (int i = 0; i < extraTicks; i++) {
                zv.aiStep(); // advance conversion logic
            }
        }

        else if (multiplier > 0.0D) {

            // Example: 0.5 = half speed
            int delay = (int) Math.floor(1.0D / multiplier);

            if (delay <= 1)
                return;

            // Only allow conversion every X ticks
            if (zv.tickCount % delay != 0) {
                // Cancel this tick's progress by skipping extra logic
                return;
            }
        }
    }
    @SubscribeEvent
    public void onZombieVillagerDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof ZombieVillager zv))
            return;

        if (!(zv.level() instanceof ServerLevel level))
            return;

        double multiplier = ModConfigs.getZombieVillager().lootMultiplier;

        LootUtil.applyLootMultiplier(event, level, zv, multiplier);
    }
}