package com.reggarf.mods.mob_better_config.mixin.elderguardian;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.ElderGuardianConfig;

import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.ElderGuardian;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(ElderGuardian.class)
public class ElderGuardianMixin {

    /*
     * Redirect the vanilla MobEffectUtil.addEffectToPlayersAround call
     * This keeps super() and restriction logic intact.
     */

    @Redirect(
        method = "customServerAiStep",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/effect/MobEffectUtil;addEffectToPlayersAround(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;DLnet/minecraft/world/effect/MobEffectInstance;I)Ljava/util/List;"
        )
    )
    private List<ServerPlayer> mobBetterConfig$replaceMiningFatigue(
            ServerLevel level,
            net.minecraft.world.entity.Entity entity,
            net.minecraft.world.phys.Vec3 pos,
            double radius,
            MobEffectInstance originalEffect,
            int interval
    ) {

        ElderGuardian elder = (ElderGuardian) entity;
        ElderGuardianConfig config = ModConfigs.getElderGuardian();

        // If disabled → return empty list (no fatigue)
        if (!config.enableMiningFatigue) {
            return List.of();
        }

        // Replace effect with config values
        MobEffectInstance customEffect = new MobEffectInstance(
                MobEffects.DIG_SLOWDOWN,
                config.miningFatigueDuration,
                config.miningFatigueAmplifier
        );

        List<ServerPlayer> players =
                MobEffectUtil.addEffectToPlayersAround(
                        level,
                        elder,
                        elder.position(),
                        config.miningFatigueRadius,
                        customEffect,
                        config.miningFatigueInterval
                );

        players.forEach(player ->
                player.connection.send(
                        new ClientboundGameEventPacket(
                                ClientboundGameEventPacket.GUARDIAN_ELDER_EFFECT,
                                elder.isSilent() ? 0.0F : 1.0F
                        )
                )
        );

        return players;
    }
}