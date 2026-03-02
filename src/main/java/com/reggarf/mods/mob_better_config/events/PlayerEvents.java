package com.reggarf.mods.mob_better_config.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
public class PlayerEvents {

    private static final double SPEED_MULTIPLIER = 3D; // change this value

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

        if (!(event.getEntity() instanceof ServerPlayer player))
            return;

        applySpeed(player);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {

        if (!(event.getEntity() instanceof ServerPlayer player))
            return;

        applySpeed(player);
    }

    private static void applySpeed(ServerPlayer player) {

        AttributeInstance movement = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (movement == null)
            return;

        // Vanilla base speed = 0.1
        movement.setBaseValue(0.1D * SPEED_MULTIPLIER);
    }
}