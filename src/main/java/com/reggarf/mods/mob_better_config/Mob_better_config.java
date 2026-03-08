package com.reggarf.mods.mob_better_config;

import com.mojang.logging.LogUtils;
import com.reggarf.mods.better_lib.message.online.OnlineMessageLib;
import com.reggarf.mods.mob_better_config.api.BetterMessages;
import com.reggarf.mods.mob_better_config.api.OnlineMessages;
import com.reggarf.mods.mob_better_config.config.MobBetterConfigRoot;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.register.ModEventRegister;
import me.shedaniel.autoconfig.AutoConfigClient;
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
        ModEventRegister.register();
        OnlineMessageLib.registerPlugin(new OnlineMessages());
        BetterMessages.register();


    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModLoadingContext.get().registerExtensionPoint(
                    IConfigScreenFactory.class,
                    () -> (container, parent) ->
                            AutoConfigClient.getConfigScreen(
                                   MobBetterConfigRoot.class, parent).get()
            );
        }
    }
}
