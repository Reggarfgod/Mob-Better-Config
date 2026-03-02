package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.RavagerConfig;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class RavagerEvents {

    @SubscribeEvent
    public void onJoin(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof Ravager ravager))
            return;

        if (!(ravager.level() instanceof ServerLevel level))
            return;

        RavagerConfig config = ModConfigs.getRavager();

        if (ravager.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(ravager, config);

        BossUtil.tryApplyBoss(
                ravager,
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

        // Spawn Multiplier
        for (int i = 1; i < config.spawnMultiplier; i++) {

            Ravager extra = new Ravager(EntityType.RAVAGER, level);

            extra.moveTo(
                    ravager.getX(),
                    ravager.getY(),
                    ravager.getZ(),
                    ravager.getYRot(),
                    ravager.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);

            applyConfig(extra, config);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(Ravager ravager, RavagerConfig config) {

        if (config.CustomName)
            MobNameUtil.applyRandomName(ravager);

        if (ravager.getAttribute(Attributes.MAX_HEALTH) != null)
            ravager.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (ravager.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            ravager.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage);

        if (ravager.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            ravager.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);

        if (ravager.getAttribute(Attributes.FOLLOW_RANGE) != null)
            ravager.getAttribute(Attributes.FOLLOW_RANGE)
                    .setBaseValue(config.followRange);

        if (ravager.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            ravager.getAttribute(Attributes.KNOCKBACK_RESISTANCE)
                    .setBaseValue(config.knockbackResistance);

        ravager.setHealth((float) config.health);

        if (config.fireImmune)
            ravager.setRemainingFireTicks(0);

        if (config.glowing)
            ravager.setGlowingTag(true);

        // Raid bonus
        if (config.strongerInRaid && ravager.hasActiveRaid()) {

            ravager.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health * config.raidHealthBonus);

            ravager.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage * config.raidDamageBonus);

            ravager.setHealth((float) (config.health * config.raidHealthBonus));
        }
    }
    @SubscribeEvent
    public void onDamage(LivingIncomingDamageEvent event) {

        if (!(event.getSource().getDirectEntity() instanceof Ravager ravager))
            return;

        RavagerConfig config = ModConfigs.getRavager();

        float damage = event.getAmount();

        if (config.enableRoar) {
            damage *= (float) config.roarDamageMultiplier;
        }

        event.setAmount(damage);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        RavagerConfig config = ModConfigs.getRavager();

        XPUtil.applyXpIfInstance(
                event,
                Ravager.class,
                config.xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Ravager ravager))
            return;

        if (!(ravager.level() instanceof ServerLevel level))
            return;

        RavagerConfig config = ModConfigs.getRavager();

        LootUtil.applyLootMultiplier(
                event,
                level,
                ravager,
                config.lootMultiplier
        );
    }

    @SubscribeEvent
    public void onDamaged(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Ravager ravager))
            return;

        if (!(ravager.level() instanceof ServerLevel level))
            return;

        RavagerConfig config = ModConfigs.getRavager();

        ReinforcementUtil.trySpawnReinforcement(
                ravager,
                level,
                config.reinforcementChance,
                4
        );
    }
}