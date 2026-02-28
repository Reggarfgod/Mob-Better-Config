package com.reggarf.mods.mob_better_config;

import com.mojang.logging.LogUtils;
import com.reggarf.mods.mob_better_config.config.MobBetterConfigRoot;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.ZombieConfig;
import com.reggarf.mods.mob_better_config.events.*;
import me.shedaniel.autoconfig.AutoConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(Mob_better_config.MODID)
public class Mob_better_config {
    public static final String MODID = "mob_better_config";
    private static final Logger LOGGER = LogUtils.getLogger();
    public Mob_better_config(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
        // Register config
        ModConfigs.register();
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
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            ModLoadingContext.get().registerExtensionPoint(
                    IConfigScreenFactory.class,
                    () -> (container, parent) ->
                            AutoConfig.getConfigScreen(
                                   MobBetterConfigRoot.class, parent).get()
            );
        }
    }
}
