package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.ZombieVillagerConfig;
import com.reggarf.mods.mob_better_config.util.ArmorUtil;
import com.reggarf.mods.mob_better_config.util.LootUtil;
import com.reggarf.mods.mob_better_config.util.ReinforcementUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class ZombieVillagerEvents {

    @SubscribeEvent
    public void onJoin(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof ZombieVillager zv))
            return;

        if (!(zv.level() instanceof ServerLevel level))
            return;

        ZombieVillagerConfig config = ModConfigs.getZombieVillager();

        //Prevent multiplied mobs from multiplying again
        if (zv.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(zv);

        for (int i = 1; i < config.spawnMultiplier; i++) {

            ZombieVillager extra = new ZombieVillager(EntityType.ZOMBIE_VILLAGER, level);

            extra.moveTo(
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

        if (zv.getAttribute(Attributes.MAX_HEALTH) != null)
            zv.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.health);

        if (zv.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            zv.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(config.attackDamage);

        if (zv.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            zv.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(config.movementSpeed);

        if (zv.getAttribute(Attributes.FOLLOW_RANGE) != null)
            zv.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(config.followRange);

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
    public void onDamaged(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof ZombieVillager zv))
            return;

        if (!(zv.level() instanceof ServerLevel level))
            return;

        ZombieVillagerConfig config = ModConfigs.getZombieVillager();

        ReinforcementUtil.trySpawnReinforcement(
                zv,
                level,
                config.reinforcementChance,
                4
        );
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