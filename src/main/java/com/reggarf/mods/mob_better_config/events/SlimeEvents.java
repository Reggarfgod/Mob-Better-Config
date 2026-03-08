package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.SlimeConfig;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class SlimeEvents {

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Slime slime))
            return;

        if (!(slime.level() instanceof ServerLevel level))
            return;

        SlimeConfig config = ModConfigs.getSlime();

        if (NbtUtil.getBooleanSafe(slime.getPersistentData(),"mob_better_config_spawned"))
            return;

        applyConfig(slime, config);

        BossUtil.tryApplyBoss(
                slime,
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

            Slime extra = new Slime(EntityType.SLIME, level);

            extra.snapTo(
                    slime.getX(),
                    slime.getY(),
                    slime.getZ(),
                    slime.getYRot(),
                    slime.getXRot()
            );


            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);
            applyConfig(extra, config);
            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(Slime slime, SlimeConfig config) {

        int size = slime.getSize();

        if (config.fixedSize > 0)
            size = Mth.clamp(config.fixedSize, config.minSize, config.maxSize);

        size = (int) Mth.clamp(size * config.sizeMultiplier, config.minSize, config.maxSize);

        slime.setSize(size, true);

        // Health
        double health = slime.getMaxHealth() * config.healthMultiplier;

        if (slime.getAttribute(Attributes.MAX_HEALTH) != null)
            slime.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);

        slime.setHealth((float) health);

        // Damage
        if (slime.getAttribute(Attributes.ATTACK_DAMAGE) != null) {

            double damage = slime.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
            damage *= config.attackDamageMultiplier;

            slime.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);
        }

        // Movement speed
        if (slime.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
            double speed = config.baseMovementSpeed + (size * config.speedPerSize);
            slime.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(speed);
        }

        // Knockback
        if (slime.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            slime.getAttribute(Attributes.ATTACK_KNOCKBACK)
                    .setBaseValue(config.knockbackStrength);

        if (config.fireImmune)
            slime.setRemainingFireTicks(0);

        if (config.glowing)
            slime.setGlowingTag(true);
        if (config.CustomName) {
            MobNameUtil.applyRandomName(slime);
        }
    }

    @SubscribeEvent
    public void onDamage(LivingDamageEvent.Pre event) {

        if (!(event.getSource().getEntity() instanceof Slime slime))
            return;

        SlimeConfig config = ModConfigs.getSlime();

        if (!config.damageScalesWithSize)
            event.setNewDamage((float) config.attackDamageMultiplier);
    }

    @SubscribeEvent
    public void onExperience(LivingExperienceDropEvent event) {

        SlimeConfig config = ModConfigs.getSlime();

        XPUtil.applyXpIfInstance(
                event,
                Slime.class,
                config.xpMultiplier
        );
    }

    @SubscribeEvent
    public void onSplit(net.neoforged.neoforge.event.entity.living.MobSplitEvent event) {

        if (!(event.getParent() instanceof Slime parent))
            return;

        SlimeConfig config = ModConfigs.getSlime();

        //Prevent split if boss
        if (!config.allowSplit || 
            (config.preventSplitWhenBoss && BossUtil.isBoss(parent))) {

            event.setCanceled(true);
            return;
        }

        int splitCount = parent.getRandom()
                .nextInt(config.maxSplitCount - config.minSplitCount + 1)
                + config.minSplitCount;

        event.getChildren().clear();

        for (int i = 0; i < splitCount; i++) {

            Slime child = new Slime(EntityType.SLIME, parent.level());

            int newSize = (int) (parent.getSize() * config.splitSizeMultiplier);
            newSize = Math.max(1, newSize);

            child.setSize(newSize, true);

            child.snapTo(
                    parent.getX(),
                    parent.getY(),
                    parent.getZ(),
                    parent.getYRot(),
                    parent.getXRot()
            );

            event.getChildren().add(child);
        }
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Slime slime))
            return;

        if (!(slime.level() instanceof ServerLevel level))
            return;

        SlimeConfig config = ModConfigs.getSlime();

        LootUtil.applyLootMultiplier(
                event,
                level,
                slime,
                config.lootMultiplier
        );
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Slime slime))
            return;

        SlimeConfig config = ModConfigs.getSlime();

        if (!config.despawnInPeaceful)
            return;

        if (slime.level().getDifficulty() == Difficulty.PEACEFUL)
            slime.discard();
    }
}