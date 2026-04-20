package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.HoglinEvents;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.hoglin.Hoglin;

public class FabricHoglinEvents {

    private static final String DAMAGE_TAG = "mbc_damage_processing";

    public static void register() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

            if (!(entity instanceof Hoglin hoglin))
                return;

            if (!(level instanceof ServerLevel serverLevel))
                return;

            HoglinEvents.onSpawn(hoglin, serverLevel);
        });


        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {

            if (entity.getTags().contains(DAMAGE_TAG)) {
                return true;
            }

            if (source.getEntity() instanceof Hoglin) {

                float newDamage = HoglinEvents.modifyAttackDamage(amount);

                // mark processing
                entity.addTag(DAMAGE_TAG);

                try {

                    float extra = newDamage - amount;

                    if (extra > 0) {
                        entity.setHealth(entity.getHealth() - extra);
                    }

                } finally {
                    entity.removeTag(DAMAGE_TAG);
                }
            }

            return true;
        });


        /* Death */
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof Hoglin hoglin))
                return;

            if (!(hoglin.level() instanceof ServerLevel level))
                return;

            HoglinEvents.onDrops(level, hoglin);
        });
    }
}