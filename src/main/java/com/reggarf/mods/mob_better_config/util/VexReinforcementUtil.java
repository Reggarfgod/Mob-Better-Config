package com.reggarf.mods.mob_better_config.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.phys.AABB;

public class VexReinforcementUtil {

    private static final String REINFORCED_TAG = "mob_better_config_vex_reinforced";

    public static void trySpawnVexReinforcement(
            Mob summoner,
            ServerLevel level,
            boolean enabled,
            boolean requireBelowHalfHealth,
            double spawnChance,
            int vexCount,
            int spawnRadius,
            int lifeTimeSeconds,
            int maxNearbyVex,
            double vexHealth,
            double vexSpeed,
            double vexAttackDamage
    ) {

        if (!enabled)
            return;

        if (level.isClientSide())
            return;

        if (summoner.getPersistentData().getBoolean(REINFORCED_TAG))
            return;

        if (requireBelowHalfHealth &&
                summoner.getHealth() > summoner.getMaxHealth() / 2.0F)
            return;

        RandomSource random = level.getRandom();

        if (random.nextDouble() > spawnChance)
            return;

        int nearby = level.getEntitiesOfClass(
                Vex.class,
                new AABB(summoner.blockPosition()).inflate(16)
        ).size();

        if (nearby >= maxNearbyVex)
            return;

        for (int i = 0; i < vexCount; i++) {

            Vex vex = EntityType.VEX.create(level);
            if (vex == null)
                continue;

            double offsetX = random.nextInt(spawnRadius * 2 + 1) - spawnRadius;
            double offsetY = random.nextInt(3);
            double offsetZ = random.nextInt(spawnRadius * 2 + 1) - spawnRadius;

            vex.moveTo(
                    summoner.getX() + offsetX,
                    summoner.getY() + offsetY,
                    summoner.getZ() + offsetZ,
                    0.0F,
                    0.0F
            );

            vex.setOwner(summoner);

            // Apply custom attributes
            if (vex.getAttribute(Attributes.MAX_HEALTH) != null) {
                vex.getAttribute(Attributes.MAX_HEALTH)
                        .setBaseValue(vexHealth);
                vex.setHealth((float) vexHealth);
            }

            if (vex.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                vex.getAttribute(Attributes.MOVEMENT_SPEED)
                        .setBaseValue(vexSpeed);
            }

            if (vex.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                vex.getAttribute(Attributes.ATTACK_DAMAGE)
                        .setBaseValue(vexAttackDamage);
            }

            if (lifeTimeSeconds > 0)
                vex.setLimitedLife(lifeTimeSeconds * 20);

            vex.getPersistentData().putBoolean(REINFORCED_TAG, true);

            level.addFreshEntity(vex);
        }
    }
}