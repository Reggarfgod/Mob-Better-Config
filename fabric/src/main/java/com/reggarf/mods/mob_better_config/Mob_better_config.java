package com.reggarf.mods.mob_better_config;

import com.reggarf.mods.mob_better_config.events.FabricBossEvents;
import com.reggarf.mods.mob_better_config.events.FabricWaterDamageEvents;
import com.reggarf.mods.mob_better_config.register.*;
import net.fabricmc.api.ModInitializer;

public class Mob_better_config implements ModInitializer {

    @Override
    public void onInitialize() {

        FabricBossEvents.register();
        FabricWaterDamageEvents.init();
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        FabricBlazeEvents.register();
        FabricCaveSpiderEvents.register();
        FabricCreeperEvents.register();
        FabricDrownedEvents.register();
        FabricElderGuardianEvents.register();
        FabricEnderDragonEvents.register();
        FabricEndermanEvents.register();
        FabricEvokerEvents.register();
        FabricGhastEvents.register();
        FabricGuardianEvents.register();
        FabricHoglinEvents.register();
        FabricHuskEvents.register();
        FabricPhantomEvents.register();
        FabricSlimeEvents.register();
        FabricZombieEvents.register();
        FabricPiglinBruteEvents.register();
        FabricPillagerEvents.register();
        FabricRavagerEvents.register();
        FabricShulkerEvents.register();
        FabricSilverfishEvents.register();
        FabricSkeletonEvents.register();
        FabricSpiderEvents.register();
        FabricVindicatorEvents.register();
        FabricStrayEvents.register();
        FabricWardenEvents.register();
        FabricWitchEvents.register();
        FabricWitherEvents.register();
        FabricWitherSkeletonEvents.register();
        FabricZoglinEvents.register();
        FabricZombifiedPiglinEvents.register();
        FabricZombieVillagerEvents.register();
    }
}
