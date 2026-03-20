package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.util.WaterDamageUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;

public class FabricWaterDamageEvents {

    public static void init() {

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {

            boolean allowWaterDamage = false;

            if (WaterDamageUtil.shouldBlockWaterDamage(
                    entity,
                    source,
                    allowWaterDamage
            )) {
                return false;
            }

            return true;
        });
    }
}