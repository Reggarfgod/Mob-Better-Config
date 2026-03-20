package com.reggarf.mods.mob_better_config.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;

public class ReinforcementUtil {

    private static final String REINFORCED_TAG = "mob_better_config_reinforced";

    public static <T extends Mob> void trySpawnReinforcement(
            T original,
            ServerLevel level,
            double chance,
            int radius
    ) {

        if (chance <= 0.0D)
            return;

        // If this mob is already a reinforcement → do nothing
        if (original.getTags().contains(REINFORCED_TAG))
            return;

        RandomSource random = level.getRandom();

        if (random.nextDouble() > chance)
            return;

        // Limit nearby same mobs (hard cap)
        int nearby = level.getEntitiesOfClass(
                original.getClass(),
                new AABB(original.blockPosition()).inflate(10)
        ).size();

        if (nearby >= 8)
            return;

        EntityType<?> type = original.getType();

        @SuppressWarnings("unchecked")
        T reinforcement = (T) type.create(level);

        if (reinforcement == null)
            return;

        double offsetX = random.nextInt(radius * 2 + 1) - radius;
        double offsetZ = random.nextInt(radius * 2 + 1) - radius;

        reinforcement.moveTo(
                original.getX() + offsetX,
                original.getY(),
                original.getZ() + offsetZ,
                original.getYRot(),
                original.getXRot()
        );

        // Mark reinforcement so it cannot call again
        reinforcement.addTag(REINFORCED_TAG);

        level.addFreshEntity(reinforcement);
    }

    public static boolean isReinforcement(Mob mob) {
        return mob.getTags().contains(REINFORCED_TAG);
    }
}