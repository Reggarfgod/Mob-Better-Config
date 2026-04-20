package com.reggarf.mods.mob_better_config.util;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
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
            double vexAttackDamage,
            double targetPlayerDistance
    ) {

        if (!enabled)
            return;

        // Prevent repeat spawning
        if (summoner.getTags().contains(REINFORCED_TAG))
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

            Vex vex = EntityType.VEX.create(level, EntitySpawnReason.NATURAL);

            if (vex == null)
                continue;

            double offsetX = random.nextInt(spawnRadius * 2 + 1) - spawnRadius;
            double offsetY = random.nextInt(3);
            double offsetZ = random.nextInt(spawnRadius * 2 + 1) - spawnRadius;

            vex.snapTo(
                    summoner.getX() + offsetX,
                    summoner.getY() + offsetY,
                    summoner.getZ() + offsetZ,
                    summoner.getYRot(),
                    summoner.getXRot()
            );

            vex.setOwner(summoner);

            // Custom attributes
            if (vex.getAttribute(Attributes.MAX_HEALTH) != null) {
                vex.getAttribute(Attributes.MAX_HEALTH).setBaseValue(vexHealth);
                vex.setHealth((float) vexHealth);
            }

            if (vex.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                vex.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(vexSpeed);
            }

            if (vex.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                vex.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(vexAttackDamage);
            }

            if (lifeTimeSeconds > 0)
                vex.setLimitedLife(lifeTimeSeconds * 20);

            Player target = level.getNearestPlayer(vex, targetPlayerDistance);

            if (target != null) {
                vex.setTarget(target);
            }
            vex.addTag(REINFORCED_TAG);

            level.addFreshEntity(vex);
        }

        // mark summoner so it cannot spawn again
        summoner.addTag(REINFORCED_TAG);
    }

    public static boolean isReinforced(Mob mob) {
        return mob.getTags().contains(REINFORCED_TAG);
    }
}