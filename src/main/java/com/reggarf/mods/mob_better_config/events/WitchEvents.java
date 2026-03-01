package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.WitchConfig;
import com.reggarf.mods.mob_better_config.util.ArmorUtil;
import com.reggarf.mods.mob_better_config.util.LootUtil;
import com.reggarf.mods.mob_better_config.util.MobNameUtil;
import com.reggarf.mods.mob_better_config.util.ReinforcementUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class WitchEvents {

    @SubscribeEvent
    public void onJoin(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof Witch witch))
            return;

        if (!(witch.level() instanceof ServerLevel level))
            return;

        WitchConfig config = ModConfigs.getWitch();

        // Prevent recursive multiplier
        if (witch.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(witch);

        for (int i = 1; i < config.spawnMultiplier; i++) {

            Witch extra = new Witch(EntityType.WITCH, level);

            extra.moveTo(
                    witch.getX(),
                    witch.getY(),
                    witch.getZ(),
                    witch.getYRot(),
                    witch.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(Witch witch) {

        WitchConfig config = ModConfigs.getWitch();
        RandomSource random = witch.level().getRandom();
        if (config.CustomName) {
            MobNameUtil.applyRandomName(witch);
        }
        if (witch.getAttribute(Attributes.MAX_HEALTH) != null)
            witch.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.health);

        if (witch.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            witch.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(config.attackDamage);

        if (witch.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            witch.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(config.movementSpeed);

        if (witch.getAttribute(Attributes.FOLLOW_RANGE) != null)
            witch.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(config.followRange);

        witch.setHealth(config.health);

        if (config.fireImmune)
            witch.setRemainingFireTicks(0);

        if (config.glowing)
            witch.setGlowingTag(true);

        if (random.nextDouble() < config.randomArmorChance)
            ArmorUtil.equipRandomArmor(witch, random, 0.4f);
    }

    @SubscribeEvent
    public void onDamaged(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Witch witch))
            return;

        if (!(witch.level() instanceof ServerLevel level))
            return;

        WitchConfig config = ModConfigs.getWitch();

        ReinforcementUtil.trySpawnReinforcement(
                witch,
                level,
                config.reinforcementChance,
                4
        );
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Witch witch))
            return;

        WitchConfig config = ModConfigs.getWitch();

        if (config.fireImmune && witch.isOnFire())
            witch.clearFire();

        // Faster potion throwing
        if (config.fasterPotionThrow) {
            if (witch.getTarget() != null) {

                // Reset attack cooldown more often
                if (witch.tickCount % 20 == 0) {
                    witch.setAggressive(true);
                }
            }
        }
    }
    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Witch witch))
            return;

        if (!(witch.level() instanceof ServerLevel level))
            return;

        WitchConfig config = ModConfigs.getWitch();

        LootUtil.applyLootMultiplier(
                event,
                level,
                witch,
                config.lootMultiplier
        );
    }
    @SubscribeEvent
    public void onPotionImpact(net.neoforged.neoforge.event.entity.ProjectileImpactEvent event) {

        if (!(event.getProjectile() instanceof ThrownPotion potion))
            return;

        if (!(potion.getOwner() instanceof Witch witch))
            return;

        if (!(witch.level() instanceof ServerLevel level))
            return;

        WitchConfig config = ModConfigs.getWitch();

        if (config.potionDamageMultiplier == 1.0D)
            return;

        // Affect nearby living entities
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
}