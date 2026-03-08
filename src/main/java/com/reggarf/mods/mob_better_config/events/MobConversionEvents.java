package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.util.NbtUtil;
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

        var oldData = oldEntity.getPersistentData();
        var newData = newEntity.getPersistentData();

        // Preserve boss tag
        if (NbtUtil.getBooleanSafe(oldData, BOSS_TAG)) {

            NbtUtil.putBooleanSafe(newData, BOSS_TAG, true);

            newData.putDouble(
                    BOSS_XP,
                    NbtUtil.getDoubleSafe(oldData, BOSS_XP)
            );

            newData.putDouble(
                    BOSS_LOOT,
                    NbtUtil.getDoubleSafe(oldData, BOSS_LOOT)
            );
        }

        // Copy health
        AttributeInstance oldHealth = oldEntity.getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance newHealth = newEntity.getAttribute(Attributes.MAX_HEALTH);

        if (oldHealth != null && newHealth != null) {
            double hp = oldHealth.getBaseValue();
            newHealth.setBaseValue(hp);
            newEntity.setHealth((float) hp);
        }

        // Copy attack damage
        AttributeInstance oldDamage = oldEntity.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance newDamage = newEntity.getAttribute(Attributes.ATTACK_DAMAGE);

        if (oldDamage != null && newDamage != null) {
            newDamage.setBaseValue(oldDamage.getBaseValue());
        }

        // Copy scale (1.20.5+ feature)
        AttributeInstance oldScale = oldEntity.getAttribute(Attributes.SCALE);
        AttributeInstance newScale = newEntity.getAttribute(Attributes.SCALE);

        if (oldScale != null && newScale != null) {
            newScale.setBaseValue(oldScale.getBaseValue());
        }

        // Copy glowing state
        if (oldEntity.isCurrentlyGlowing())
            newEntity.setGlowingTag(true);

        // Copy name
        newEntity.setCustomName(oldEntity.getCustomName());
        newEntity.setCustomNameVisible(oldEntity.isCustomNameVisible());
    }
}