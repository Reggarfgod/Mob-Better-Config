package com.reggarf.mods.mob_better_config.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class LootUtil {

    public static void applyLootMultiplier(
            Collection<ItemEntity> drops,
            ServerLevel level,
            LivingEntity entity,
            double multiplier
    ) {

        if (multiplier <= 1.0)
            return;

        Collection<ItemEntity> newDrops = new ArrayList<>();

        for (ItemEntity drop : drops) {

            ItemStack stack = drop.getItem();

            int newCount = (int) Math.floor(stack.getCount() * multiplier);

            ItemStack newStack = stack.copy();
            newStack.setCount(Math.max(1, newCount));

            newDrops.add(new ItemEntity(
                    level,
                    entity.getX(),
                    entity.getY(),
                    entity.getZ(),
                    newStack
            ));
        }

        drops.clear();
        drops.addAll(newDrops);
    }
}