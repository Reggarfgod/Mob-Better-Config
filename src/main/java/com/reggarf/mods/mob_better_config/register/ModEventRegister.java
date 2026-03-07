package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.events.*;
import net.neoforged.neoforge.common.NeoForge;

public class ModEventRegister {

    public static void register() {

        // Register events
        NeoForge.EVENT_BUS.register(new ZombieEvents());
        NeoForge.EVENT_BUS.register(new SkeletonEvents());
        NeoForge.EVENT_BUS.register(new ZombieVillagerEvents());
        NeoForge.EVENT_BUS.register(new WitchEvents());
        NeoForge.EVENT_BUS.register(new SpiderEvents());
        NeoForge.EVENT_BUS.register(new CaveSpiderEvents());
        NeoForge.EVENT_BUS.register(new CreeperEvents());
        NeoForge.EVENT_BUS.register(new WardenEvents());
        NeoForge.EVENT_BUS.register(new VindicatorEvents());
        NeoForge.EVENT_BUS.register(new StrayEvents());
        NeoForge.EVENT_BUS.register(new SlimeEvents());
        NeoForge.EVENT_BUS.register(new HuskEvents());
        NeoForge.EVENT_BUS.register(new PillagerEvents());
        NeoForge.EVENT_BUS.register(new PhantomEvents());
        NeoForge.EVENT_BUS.register(new EndermanEvents());
        NeoForge.EVENT_BUS.register(new WitherSkeletonEvents());
        NeoForge.EVENT_BUS.register(new DrownedEvents());
        NeoForge.EVENT_BUS.register(new MagmaCubeEvents());
        NeoForge.EVENT_BUS.register(new BlazeEvents());
        NeoForge.EVENT_BUS.register(new GhastEvents());
        NeoForge.EVENT_BUS.register(new EvokerEvents());
        //NeoForge.EVENT_BUS.register(new VexEvents());
        NeoForge.EVENT_BUS.register(new RavagerEvents());
        NeoForge.EVENT_BUS.register(new ShulkerEvents());
        NeoForge.EVENT_BUS.register(new SilverfishEvents());
        NeoForge.EVENT_BUS.register(new GuardianEvents());
        NeoForge.EVENT_BUS.register(new WitherEvents());
        NeoForge.EVENT_BUS.register(new EnderDragonEvents());
        NeoForge.EVENT_BUS.register(new HoglinEvents());
        NeoForge.EVENT_BUS.register(new ZoglinEvents());
        // NeoForge.EVENT_BUS.register(new MobConversionEvents());
        NeoForge.EVENT_BUS.register(new PiglinBruteEvents());
        NeoForge.EVENT_BUS.register(new zombiefiedPiglinEvents());
    }
}