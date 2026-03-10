package com.reggarf.mods.mob_better_config.handle;

import com.reggarf.mods.mob_better_config.config.MobConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.util.BossUtil;
import com.reggarf.mods.mob_better_config.util.LootUtil;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.Mob;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class HostileMobEvents {

    @SubscribeEvent
    public void onMobSpawn(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Monster mob))
            return;

        MobConfig config = ModConfigs.get(mob.getType());

        CommonMobHandler.applyCommonAttributes(
                mob,
                config.health,
                config.armor,
                config.armorToughness,
                config.attackDamage,
                config.attackSpeed,
                config.movementSpeed,
                config.followRange,
                config.knockbackResistance,
                config.attackKnockback,
                config.stepHeight,
                config.gravity,
                config.glowing,
                config.customName,
                config.canBreakDoors,
                config.doorBreakMode
        );

        /*
         * BOSS SYSTEM
         */

        BossUtil.tryApplyBoss(
                mob,
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

        /*
         * SPAWN MULTIPLIER
         */

        if (mob.level() instanceof ServerLevel level) {

            CommonMobHandler.spawnMultiplier(
                    mob,
                    level,
                    config.spawnMultiplier
            );
        }
    }


     // MOB TICK
    @SubscribeEvent
    public void onMobTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Monster mob))
            return;

        MobConfig config = ModConfigs.get(mob.getType());

        CommonMobHandler.applyCommonTickBehaviors(
                mob,
                config.burnInDaylight,
                config.fireImmune,
                config.sprintAbility,
                config.rageMode,
                config.jumpBoost,
                config.nightBuff
        );
    }

    /*
     * =========================
     * MOB DROPS
     * =========================
     */

    @SubscribeEvent
    public void onMobDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Monster mob))
            return;

        if (!(mob.level() instanceof ServerLevel level))
            return;

        MobConfig config = ModConfigs.get(mob.getType());
        LootUtil.applyLootMultiplier(
                event,
                level,
                mob,
                config.lootMultiplier
        );
    }
}