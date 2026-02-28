package com.reggarf.mods.mob_better_config.util;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ArmorUtil {

    /**
     * Equip random armor pieces on any Mob.
     *
     * @param mob Target mob
     * @param random Random source
     * @param pieceChance Chance per armor piece (0.0 - 1.0)
     */
    public static void equipRandomArmor(Mob mob, RandomSource random, float pieceChance) {

        int tier = random.nextInt(4);

        ItemStack helmet = getHelmet(tier);
        ItemStack chest = getChestplate(tier);
        ItemStack legs = getLeggings(tier);
        ItemStack boots = getBoots(tier);

        if (random.nextFloat() < pieceChance)
            mob.setItemSlot(EquipmentSlot.HEAD, helmet);

        if (random.nextFloat() < pieceChance)
            mob.setItemSlot(EquipmentSlot.CHEST, chest);

        if (random.nextFloat() < pieceChance)
            mob.setItemSlot(EquipmentSlot.LEGS, legs);

        if (random.nextFloat() < pieceChance)
            mob.setItemSlot(EquipmentSlot.FEET, boots);
    }

    private static ItemStack getHelmet(int tier) {
        return switch (tier) {
            case 0 -> new ItemStack(Items.LEATHER_HELMET);
            case 1 -> new ItemStack(Items.IRON_HELMET);
            case 2 -> new ItemStack(Items.GOLDEN_HELMET);
            default -> new ItemStack(Items.DIAMOND_HELMET);
        };
    }

    private static ItemStack getChestplate(int tier) {
        return switch (tier) {
            case 0 -> new ItemStack(Items.LEATHER_CHESTPLATE);
            case 1 -> new ItemStack(Items.IRON_CHESTPLATE);
            case 2 -> new ItemStack(Items.GOLDEN_CHESTPLATE);
            default -> new ItemStack(Items.DIAMOND_CHESTPLATE);
        };
    }

    private static ItemStack getLeggings(int tier) {
        return switch (tier) {
            case 0 -> new ItemStack(Items.LEATHER_LEGGINGS);
            case 1 -> new ItemStack(Items.IRON_LEGGINGS);
            case 2 -> new ItemStack(Items.GOLDEN_LEGGINGS);
            default -> new ItemStack(Items.DIAMOND_LEGGINGS);
        };
    }

    private static ItemStack getBoots(int tier) {
        return switch (tier) {
            case 0 -> new ItemStack(Items.LEATHER_BOOTS);
            case 1 -> new ItemStack(Items.IRON_BOOTS);
            case 2 -> new ItemStack(Items.GOLDEN_BOOTS);
            default -> new ItemStack(Items.DIAMOND_BOOTS);
        };
    }
}