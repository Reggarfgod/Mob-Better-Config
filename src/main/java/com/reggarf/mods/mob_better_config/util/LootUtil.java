package com.reggarf.mods.mob_better_config.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.ArrayList;
import java.util.Collection;

public class LootUtil {

    public static void applyLootMultiplier(
            LivingDropsEvent event,
            ServerLevel level,
            LivingEntity entity,
            double multiplier
    ) {

        if (multiplier <= 1.0D)
            return;

        Collection<ItemEntity> originalDrops = event.getDrops();
        Collection<ItemEntity> newDrops = new ArrayList<>();

        for (ItemEntity drop : originalDrops) {

            ItemStack originalStack = drop.getItem();

            int newCount = (int) Math.floor(originalStack.getCount() * multiplier);

            ItemStack newStack = originalStack.copy();
            newStack.setCount(Math.max(1, newCount));

            newDrops.add(new ItemEntity(
                    level,
                    entity.getX(),
                    entity.getY(),
                    entity.getZ(),
                    newStack
            ));
        }

        originalDrops.clear();
        originalDrops.addAll(newDrops);
    }
}