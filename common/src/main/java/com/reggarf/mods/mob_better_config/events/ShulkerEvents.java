package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.ShulkerConfig;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.BossUtil;
import com.reggarf.mods.mob_better_config.util.LootUtil;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Shulker;

public class ShulkerEvents {


    public static void onSpawn(Shulker shulker, ServerLevel level) {
        if (CommonMobHandler.isInitialized(shulker))
            return;
        CommonMobHandler.markInitialized(shulker);

        ShulkerConfig config = ModConfigs.getShulker();

        CommonMobHandler.applyCommonAttributes(
                shulker,
                config.health,
                config.armor,
                0.0,
                0.0, // attack damage
                0.0, // attack speed
                0.0, // movement speed
                16.0, // follow range
                config.knockbackResistance,
                config.attackKnockback,
                0.6, // step height
                0.08, // gravity
                config.glowing,
                config.CustomName,
                false, // door break
                0,
                config.reinforcementChance
        );

        // Boss system
        BossUtil.tryApplyBoss(
                shulker,
                config.bossMode,
                config.forceAllBoss,
                config.bossChance,
                config.bossHealthMultiplier,
                config.bossDamageMultiplier,
                config.bossGlowing,
                config.bossCustomName,
                config.bossXpMultiplier,
                config.bossLootMultiplier
        );

        // Spawn multiplier
        CommonMobHandler.spawnMultiplier(shulker, level, config.spawnMultiplier);
    }

    public static void onTick(Shulker shulker) {

        ShulkerConfig config = ModConfigs.getShulker();

        CommonMobHandler.applyCommonTickBehaviors(
                shulker,
                false, // burnInDaylight
                config.fireImmune,
                false,
                false,
                false,
                false
        );

        // Armor logic (closed vs open)
        var armorAttr = shulker.getAttribute(Attributes.ARMOR);
        if (armorAttr == null)
            return;

        boolean closed = shulker.getTarget() == null;

        if (closed) {
            double baseNeeded = config.armorWhenClosed - 20.0D;
            if (baseNeeded < 0)
                baseNeeded = 0;

            armorAttr.setBaseValue(baseNeeded);
        } else {
            armorAttr.setBaseValue(0.0D);
        }
    }


    public static float modifyXP(float xp) {
        return xp * (float) ModConfigs.getShulker().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, Shulker shulker) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                shulker,
                ModConfigs.getShulker().lootMultiplier
        );
    }
}