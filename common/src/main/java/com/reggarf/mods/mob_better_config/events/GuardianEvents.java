package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ElderGuardianConfig;
import com.reggarf.mods.mob_better_config.config.GuardianConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;

public class GuardianEvents {

    public static void onSpawn(Guardian guardian, ServerLevel level) {
        if (CommonMobHandler.isInitialized(guardian))
            return;
        CommonMobHandler.markInitialized(guardian);
        if (guardian instanceof ElderGuardian)
            return;

        GuardianConfig config = ModConfigs.getGuardian();

        CommonMobHandler.applyCommonAttributes(
                guardian,
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
                config.CustomName,
                config.canBreakDoors,
                config.doorBreakMode,
                config.reinforcementChance
        );

        BossUtil.tryApplyBoss(
                guardian,
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
    }


    public static void onPreTick(Guardian guardian) {

        /* Elder Guardian */

        if (guardian instanceof ElderGuardian elder) {

            ElderGuardianConfig config = ModConfigs.getElderGuardian();

            if (!config.enableLaser) {

                elder.setTarget(null);
                elder.getNavigation().stop();
            }

            return;
        }

        /* Normal Guardian */

        GuardianConfig config = ModConfigs.getGuardian();

        if (!config.enableLaser) {

            guardian.setTarget(null);
            guardian.getNavigation().stop();
        }
    }


    public static void onPostTick(Guardian guardian) {

        if (guardian instanceof ElderGuardian)
            return;

        GuardianConfig config = ModConfigs.getGuardian();

        LivingEntity target = guardian.getTarget();

        if (target == null)
            return;

        if (!config.targetPlayers && target instanceof Player)
            guardian.setTarget(null);

        if (!config.targetSquid && target instanceof Squid)
            guardian.setTarget(null);

        if (!config.targetAxolotl && target instanceof Axolotl)
            guardian.setTarget(null);
    }


    public static float modifyLaserDamage(Guardian guardian, float damage) {

        /* Elder Guardian */

        if (guardian instanceof ElderGuardian elder) {

            ElderGuardianConfig config = ModConfigs.getElderGuardian();

            if (!config.enableLaser)
                return 0;

            return damage * (float) config.laserDamageMultiplier;
        }

        /* Normal Guardian */

        GuardianConfig config = ModConfigs.getGuardian();

        if (!config.enableLaser)
            return 0;

        return damage * (float) config.laserDamageMultiplier;
    }


    public static void handleThorns(Guardian guardian, LivingEntity attacker) {

        if (guardian.isMoving())
            return;


        if (guardian instanceof ElderGuardian elder) {

            ElderGuardianConfig config = ModConfigs.getElderGuardian();

            if (!config.enableThorns)
                return;

            attacker.hurt(
                    elder.damageSources().thorns(elder),
                    (float) config.thornsDamage
            );

            return;
        }

        GuardianConfig config = ModConfigs.getGuardian();

        if (!config.enableThorns)
            return;

        attacker.hurt(
                guardian.damageSources().thorns(guardian),
                (float) config.thornsDamage
        );
    }

    public static float onXP(float xp) {
        return xp * (float) ModConfigs.getGuardian().xpMultiplier;
    }

    public static void onDrops(ServerLevel level, Guardian guardian) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                guardian,
                ModConfigs.getGuardian().lootMultiplier
        );
    }
}