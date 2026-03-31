package com.reggarf.mods.mob_better_config.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DaylightBurnUtil {

    private DaylightBurnUtil() {}

    public static void handleDaylightBurn(
            LivingEntity entity,
            boolean burnInDaylight,
            boolean fireImmune,
            int burnSeconds,
            boolean useBrightnessCheck
    ) {

        if (!(entity.level() instanceof ServerLevel level))
            return;

        if (!burnInDaylight) {
            entity.clearFire();
            entity.setRemainingFireTicks(0);
            return;
        }

        if (fireImmune) {
            entity.clearFire();
            entity.setRemainingFireTicks(0);
            return;
        }

        if (!level.isBrightOutside())
            return;

        if (!level.canSeeSky(entity.blockPosition()))
            return;

        if (entity.isInWaterOrRain() || entity.isInPowderSnow || entity.wasInPowderSnow)
            return;

        if (useBrightnessCheck) {
            float brightness = entity.getLightLevelDependentMagicValue();

            if (brightness <= 0.5F)
                return;

            if (entity.getRandom().nextFloat() * 30.0F >= (brightness - 0.4F) * 2.0F)
                return;
        }

        // Helmet protection (vanilla behavior)
        ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);

        if (!head.isEmpty()) {

            if (head.isDamageableItem()) {
                head.setDamageValue(head.getDamageValue() + entity.getRandom().nextInt(2));

                if (head.getDamageValue() >= head.getMaxDamage()) {
                    entity.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                }
            }

            return; // Helmet blocks sunlight
        }

        // Apply fire
        entity.setRemainingFireTicks(burnSeconds * 20);
    }
}