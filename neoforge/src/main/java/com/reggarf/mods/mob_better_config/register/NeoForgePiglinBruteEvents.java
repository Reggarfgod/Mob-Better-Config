package com.reggarf.mods.mob_better_config.register;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.PiglinBruteConfig;
import com.reggarf.mods.mob_better_config.events.PiglinBruteEvents;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingConversionEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

public class NeoForgePiglinBruteEvents {

    @SubscribeEvent
    public static void spawn(FinalizeSpawnEvent event) {

        if (event.getEntity() instanceof PiglinBrute brute &&
                brute.level() instanceof ServerLevel level) {

            PiglinBruteEvents.onSpawn(brute, level);
        }
    }

    @SubscribeEvent
    public static void xp(LivingExperienceDropEvent event) {

        if (event.getEntity() instanceof PiglinBrute) {

            float xp = event.getDroppedExperience();
            xp = PiglinBruteEvents.modifyXP(xp);
            event.setDroppedExperience((int) xp);
        }
    }

    @SubscribeEvent
    public static void drops(LivingDropsEvent event) {

        if (event.getEntity() instanceof PiglinBrute brute &&
                brute.level() instanceof ServerLevel level) {

            PiglinBruteEvents.onDrops(level, brute);
        }
    }

//    @SubscribeEvent
//    public static void onConvertPre(LivingConversionEvent.Pre event) {
//
//        if (!(event.getEntity() instanceof PiglinBrute brute))
//            return;
//
//        PiglinBruteConfig config = ModConfigs.getPiglinBrute();
//
//        if (!config.disableZombification)
//            return;
//
//        event.setCanceled(true);
//    }
//
//    @SubscribeEvent
//    public static void onConvert(LivingConversionEvent.Post event) {
//
//        if (!(event.getEntity() instanceof PiglinBrute brute))
//            return;
//
//        if (!(event.getOutcome() instanceof ZombifiedPiglin zombified))
//            return;
//
//        if (!(zombified.level() instanceof ServerLevel))
//            return;
//
//        copyAttribute(brute, zombified, Attributes.MAX_HEALTH);
//        copyAttribute(brute, zombified, Attributes.ATTACK_DAMAGE);
//        copyAttribute(brute, zombified, Attributes.MOVEMENT_SPEED);
//        copyAttribute(brute, zombified, Attributes.ARMOR);
//        copyAttribute(brute, zombified, Attributes.KNOCKBACK_RESISTANCE);
//        copyAttribute(brute, zombified, Attributes.SCALE);
//
//        zombified.setHealth(brute.getHealth());
//
//        if (brute.isCurrentlyGlowing())
//            zombified.setGlowingTag(true);
//
//        if (brute.hasCustomName()) {
//            zombified.setCustomName(brute.getCustomName());
//            zombified.setCustomNameVisible(brute.isCustomNameVisible());
//        }
//
//        zombified.getPersistentData().merge(brute.getPersistentData());
//    }
//
//    private static void copyAttribute(LivingEntity from, LivingEntity to, Holder<Attribute> attribute) {
//
//        var fromAttr = from.getAttribute(attribute);
//        var toAttr = to.getAttribute(attribute);
//
//        if (fromAttr == null || toAttr == null)
//            return;
//
//        toAttr.setBaseValue(fromAttr.getBaseValue());
//    }
}