package com.reggarf.mods.mob_better_config.events;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingConversionEvent;

public class MobConversionEvents {

    private static final String BOSS_TAG = "mob_better_config_boss";
    private static final String BOSS_XP = "mob_better_config_boss_xp";
    private static final String BOSS_LOOT = "mob_better_config_boss_loot";

    @SubscribeEvent
    public void onMobConvert(LivingConversionEvent.Post event) {

        LivingEntity oldEntity = event.getEntity();
        LivingEntity newEntity = event.getOutcome();

        if (oldEntity == null || newEntity == null)
            return;

        if (oldEntity.getPersistentData().getBoolean(BOSS_TAG)) {

            newEntity.getPersistentData().putBoolean(BOSS_TAG, true);

            newEntity.getPersistentData().putDouble(
                    BOSS_XP,
                    oldEntity.getPersistentData().getDouble(BOSS_XP)
            );

            newEntity.getPersistentData().putDouble(
                    BOSS_LOOT,
                    oldEntity.getPersistentData().getDouble(BOSS_LOOT)
            );
        }

        AttributeInstance oldHealth = oldEntity.getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance newHealth = newEntity.getAttribute(Attributes.MAX_HEALTH);

        if (oldHealth != null && newHealth != null) {
            double hp = oldHealth.getBaseValue();
            newHealth.setBaseValue(hp);
            newEntity.setHealth((float) hp);
        }

        AttributeInstance oldDamage = oldEntity.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance newDamage = newEntity.getAttribute(Attributes.ATTACK_DAMAGE);

        if (oldDamage != null && newDamage != null) {
            newDamage.setBaseValue(oldDamage.getBaseValue());
        }

        AttributeInstance oldScale = oldEntity.getAttribute(Attributes.SCALE);
        AttributeInstance newScale = newEntity.getAttribute(Attributes.SCALE);

        if (oldScale != null && newScale != null) {
            newScale.setBaseValue(oldScale.getBaseValue());
        }

        if (oldEntity.isCurrentlyGlowing())
            newEntity.setGlowingTag(true);

        newEntity.setCustomName(oldEntity.getCustomName());
        newEntity.setCustomNameVisible(oldEntity.isCustomNameVisible());
    }
}