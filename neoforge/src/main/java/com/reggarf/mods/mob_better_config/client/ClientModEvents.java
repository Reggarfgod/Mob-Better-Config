package com.reggarf.mods.mob_better_config.client;

import com.reggarf.mods.mob_better_config.Constants;
import com.reggarf.mods.mob_better_config.config.MobBetterConfigRoot;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.AutoConfigClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(
        modid = Constants.MOD_ID,
        value = Dist.CLIENT
)
public class ClientModEvents {

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