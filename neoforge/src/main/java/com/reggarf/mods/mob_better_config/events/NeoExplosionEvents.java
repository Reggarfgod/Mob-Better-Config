//package com.reggarf.mods.mob_better_config.events;
//
//import com.reggarf.mods.mob_better_config.util.ExplosionUtil;
//import net.minecraft.world.entity.Entity;
//import net.neoforged.bus.api.SubscribeEvent;
//import net.neoforged.neoforge.event.level.ExplosionEvent;
//
//public class NeoExplosionEvents {
//
//    private static final double multiplier = 2.0;
//
//    @SubscribeEvent
//    public static void onExplosion(ExplosionEvent.Start event) {
//
//        Entity source = event.getExplosion().getIndirectSourceEntity();
//
//        ExplosionUtil.applyExplosionMultiplier(
//                event.getExplosion(),
//                source,
//                multiplier
//        );
//    }
//}