package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.MagmaCubeConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.util.BossUtil;
import com.reggarf.mods.mob_better_config.util.LootUtil;
import com.reggarf.mods.mob_better_config.util.MobNameUtil;
import com.reggarf.mods.mob_better_config.util.ReinforcementUtil;
import com.reggarf.mods.mob_better_config.util.XPUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.MagmaCube;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.MobSplitEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class MagmaCubeEvents {

    private void applyConfig(MagmaCube cube, MagmaCubeConfig config) {

        int size = cube.getSize();

        if (config.fixedSize > 0)
            size = Mth.clamp(config.fixedSize, config.minSize, config.maxSize);

        size = (int) Mth.clamp(size * config.sizeMultiplier, config.minSize, config.maxSize);

        cube.setSize(size, true);

        // Health
        if (cube.getAttribute(Attributes.MAX_HEALTH) != null) {
            double health = cube.getMaxHealth() * config.healthMultiplier;
            cube.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
            cube.setHealth((float) health);
        }

        // Attack Damage
        if (cube.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
            double damage = cube.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
            damage *= config.attackDamageMultiplier;
            cube.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);
        }

        // Movement Speed
        if (cube.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
            cube.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.baseMovementSpeed + (size * config.speedPerSize));
        }

        // Knockback
        if (cube.getAttribute(Attributes.ATTACK_KNOCKBACK) != null)
            cube.getAttribute(Attributes.ATTACK_KNOCKBACK)
                    .setBaseValue(config.knockbackStrength);

        if (config.glowing)
            cube.setGlowingTag(true);

        if (config.CustomName)
            MobNameUtil.applyRandomName(cube);
    }


    @SubscribeEvent
    public void onJoin(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof MagmaCube cube))
            return;

        if (!(cube.level() instanceof ServerLevel level))
            return;

        MagmaCubeConfig config = ModConfigs.getMagmaCube();

        if (cube.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(cube, config);

        BossUtil.tryApplyBoss(
                cube,
                config.bossMode,
                config.forceAllBoss,
                config.bossChance,
                config.bossHealthMultiplier,
                config.bossDamageMultiplier,
                config.bossGlowing,
                config.bossCustomName
        );

        // Spawn multiplier
        for (int i = 1; i < config.spawnMultiplier; i++) {

            MagmaCube extra = new MagmaCube(EntityType.MAGMA_CUBE, level);

            extra.moveTo(
                    cube.getX(),
                    cube.getY(),
                    cube.getZ(),
                    cube.getYRot(),
                    cube.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);
            applyConfig(extra, config);
            level.addFreshEntity(extra);
        }
    }

    @SubscribeEvent
    public void onSplit(MobSplitEvent event) {

        if (!(event.getParent() instanceof MagmaCube parent))
            return;

        MagmaCubeConfig config = ModConfigs.getMagmaCube();

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

            MagmaCube child = new MagmaCube(EntityType.MAGMA_CUBE, parent.level());

            int newSize = (int) (parent.getSize() * config.splitSizeMultiplier);
            newSize = Math.max(1, newSize);

            child.setSize(newSize, true);

            child.moveTo(
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
    public void onDamage(LivingDamageEvent.Pre event) {

        if (!(event.getSource().getEntity() instanceof MagmaCube cube))
            return;

        MagmaCubeConfig config = ModConfigs.getMagmaCube();

        if (!config.damageScalesWithSize)
            event.setNewDamage((float) config.attackDamageMultiplier);

        ReinforcementUtil.trySpawnReinforcement(
                cube,
                (ServerLevel) cube.level(),
                config.reinforcementChance,
                4
        );
    }

    @SubscribeEvent
    public void onXP(LivingExperienceDropEvent event) {

        XPUtil.applyXpIfInstance(
                event,
                MagmaCube.class,
                ModConfigs.getMagmaCube().xpMultiplier
        );
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof MagmaCube cube))
            return;

        if (!(cube.level() instanceof ServerLevel level))
            return;

        LootUtil.applyLootMultiplier(
                event,
                level,
                cube,
                ModConfigs.getMagmaCube().lootMultiplier
        );
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof MagmaCube cube))
            return;

        if (cube.level().getDifficulty() == Difficulty.PEACEFUL &&
                ModConfigs.getMagmaCube().despawnInPeaceful) {

            cube.discard();
        }
    }
}