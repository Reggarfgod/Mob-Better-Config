package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.util.BossUtil;
import com.reggarf.mods.mob_better_config.util.LootUtil;
import com.reggarf.mods.mob_better_config.util.XPUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

public class NeoBossEvents {

    @SubscribeEvent
    public static void onXP(LivingExperienceDropEvent event) {

        LivingEntity entity = event.getEntity();

        if (!BossUtil.isBoss(entity))
            return;

        double multiplier = BossUtil.getXpMultiplier(entity);

        int xp = XPUtil.multiplyXP(event.getDroppedExperience(), multiplier);

        event.setDroppedExperience(xp);
    }

    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {

        LivingEntity entity = event.getEntity();

        if (!(entity.level() instanceof ServerLevel level))
            return;

        if (!BossUtil.isBoss(entity))
            return;

        double multiplier = BossUtil.getLootMultiplier(entity);

        LootUtil.applyLootMultiplier(event.getDrops(), level, entity, multiplier);
    }
}