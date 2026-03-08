package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.HoglinConfig;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.Zoglin;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

public class HoglinEvents {

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Hoglin hoglin))
            return;

        if (!(hoglin.level() instanceof ServerLevel level))
            return;

        HoglinConfig config = ModConfigs.getHoglin();

        if (NbtUtil.getBooleanSafe(hoglin.getPersistentData(),("mob_better_config_spawned")))
            return;

        applyConfig(hoglin, config);

        BossUtil.tryApplyBoss(
                hoglin,
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

            Hoglin extra = new Hoglin(EntityType.HOGLIN, level);

            extra.snapTo(
                    hoglin.getX(),
                    hoglin.getY(),
                    hoglin.getZ(),
                    hoglin.getYRot(),
                    hoglin.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);

            applyConfig(extra, config);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(Hoglin hoglin, HoglinConfig config) {

        if (config.customName) {
            MobNameUtil.applyRandomName(hoglin);
        }

        if (hoglin.getAttribute(Attributes.MAX_HEALTH) != null)
            hoglin.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (hoglin.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            hoglin.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage);

        if (hoglin.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            hoglin.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);

        if (hoglin.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
            hoglin.getAttribute(Attributes.KNOCKBACK_RESISTANCE)
                    .setBaseValue(config.knockbackResistance);

        if (hoglin.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            hoglin.getAttribute(Attributes.ATTACK_KNOCKBACK)
                    .setBaseValue(config.attackKnockback);

        if (hoglin.getAttribute(Attributes.ARMOR) != null)
            hoglin.getAttribute(Attributes.ARMOR)
                    .setBaseValue(config.armor);

        if (hoglin.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE) != null)
            hoglin.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(config.reinforcementChance);

        hoglin.setHealth((float) config.health);

        if (config.fireImmune)
            hoglin.setRemainingFireTicks(0);

        if (config.glowing)
            hoglin.setGlowingTag(true);

        if (config.disableZombification)
            hoglin.setImmuneToZombification(true);
    }

    @SubscribeEvent
    public void onDamage(LivingDamageEvent.Pre event) {

        if (!(event.getSource().getEntity() instanceof Hoglin hoglin))
            return;

        HoglinConfig config = ModConfigs.getHoglin();

        float scaledDamage = event.getNewDamage()
                * (float) config.attackDamageMultiplier;

        event.setNewDamage(scaledDamage);
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        HoglinConfig config = ModConfigs.getHoglin();

        XPUtil.applyXpIfInstance(
                event,
                Hoglin.class,
                config.xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Hoglin hoglin))
            return;

        if (!(hoglin.level() instanceof ServerLevel level))
            return;

        HoglinConfig config = ModConfigs.getHoglin();

        LootUtil.applyLootMultiplier(
                event,
                level,
                hoglin,
                config.lootMultiplier
        );
    }
}