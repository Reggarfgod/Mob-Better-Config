package com.reggarf.mods.mob_better_config;


import com.reggarf.mods.better_lib.message.online.OnlineMessageLib;
import com.reggarf.mods.mob_better_config.api.BetterMessages;
import com.reggarf.mods.mob_better_config.events.NeoBossEvents;
import com.reggarf.mods.mob_better_config.events.NeoWaterDamageEvents;
import com.reggarf.mods.mob_better_config.register.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Constants.MOD_ID)
public class Mob_better_config {

    public Mob_better_config(IEventBus eventBus) {
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        NeoForge.EVENT_BUS.register(NeoBossEvents.class);
        NeoForge.EVENT_BUS.register(NeoWaterDamageEvents.class);
        //NeoForge.EVENT_BUS.register(NeoExplosionEvents.class);
        // Use NeoForge to bootstrap the Common mod.
        // OnlineMessageLib.registerPlugin(new OnlineMessages());
         BetterMessages.register();
        // Register events
        NeoForge.EVENT_BUS.register(NeoForgeBlazeEvents.class);
        NeoForge.EVENT_BUS.register(NeoForgeCaveSpiderEvents.class);
        NeoForge.EVENT_BUS.register(NeoForgeCreeperEvents.class);
        NeoForge.EVENT_BUS.register(new NeoForgeElderGuardianEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeEnderDragonEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeEndermanEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeEvokerEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeGhastEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeGuardianEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeHoglinEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeHuskEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeMagmaCubeEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeSlimeEvents());
        NeoForge.EVENT_BUS.register(NeoForgePhantomEvents.class);
        NeoForge.EVENT_BUS.register(new NeoForgeZombieEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeRavagerEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeShulkerEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeSilverfishEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeSkeletonEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeSpiderEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeVindicatorEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeStrayEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeWardenEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeWitchEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeWitherEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeWitherSkeletonEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeZoglinEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeZombifiedPiglinEvents());
        NeoForge.EVENT_BUS.register(new NeoForgeZombieVillagerEvents());

        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();
    }
}
