package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.EvokerConfig;
import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.handle.CommonMobHandler;
import com.reggarf.mods.mob_better_config.util.BossUtil;
import com.reggarf.mods.mob_better_config.util.LootUtil;

import com.reggarf.mods.mob_better_config.util.helper.EntitySpawnUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.sheep.Sheep;

import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.illager.Evoker;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.item.DyeColor;

public class EvokerEvents {


    public static void onJoin(Evoker evoker, ServerLevel level) {

        if (CommonMobHandler.isInitialized(evoker))
            return;
        CommonMobHandler.markInitialized(evoker);

        EvokerConfig config = ModConfigs.getEvoker();

        applyConfig(evoker, config);

        BossUtil.tryApplyBoss(
                evoker,
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

        CommonMobHandler.spawnMultiplier(evoker, level, config.spawnMultiplier);
    }

    private static void applyConfig(Evoker evoker, EvokerConfig config) {

        CommonMobHandler.applyCommonAttributes(
                evoker,
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

        if (config.fireImmune)
            evoker.setRemainingFireTicks(0);

        if (config.strongerInRaid && evoker.hasActiveRaid()) {
            evoker.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH)
                    .setBaseValue(config.health * config.raidHealthBonus);

            evoker.setHealth((float) (config.health * config.raidHealthBonus));
        }
    }

    public static boolean allowFangs(EvokerFangs fangs) {

        if (!(fangs.getOwner() instanceof Evoker))
            return true;

        return ModConfigs.getEvoker().enableFangsSpell;
    }

    public static float modifyFangDamage(Evoker evoker, float damage) {

        EvokerConfig config = ModConfigs.getEvoker();

        damage *= config.fangsDamageMultiplier;

        if (config.strongerInRaid && evoker.hasActiveRaid())
            damage *= config.raidDamageBonus;

        return damage;
    }
    public static float onFangDamage(EvokerFangs fangs, float damage) {

        if (!(fangs.getOwner() instanceof Evoker evoker))
            return damage;

        return modifyFangDamage(evoker, damage);
    }
    public static boolean handleVexSpawn(Vex vex, ServerLevel level) {

        if (!(vex.getOwner() instanceof Evoker evoker))
            return true;

        EvokerConfig config = ModConfigs.getEvoker();

        if (vex.getTags().contains("mob_better_config_spawned"))
            return true;

        if (!config.enableVexSummon)
            return false;

        int existing = level.getEntitiesOfClass(
                Vex.class,
                evoker.getBoundingBox().inflate(32),
                v -> v.getOwner() == evoker
        ).size();

        if (existing >= config.summonVexCount)
            return false;

        vex.setLimitedLife(config.vexLifeTicks);

        if (config.summonVexCount > 3 && existing == 1) {

            int extra = config.summonVexCount - 3;

            for (int i = 0; i < extra; i++) {

                Vex newVex = EntitySpawnUtil.createVex(level);
                if (newVex == null) continue;

                newVex.snapTo(
                        vex.getX(),
                        vex.getY(),
                        vex.getZ(),
                        0F,
                        0F
                );

                EntitySpawnUtil.finalizeSpawn(newVex, level);

                newVex.setOwner(evoker);
                newVex.setBoundOrigin(evoker.blockPosition());
                newVex.setLimitedLife(config.vexLifeTicks);

                newVex.addTag("mob_better_config_spawned");

                level.addFreshEntity(newVex);
            }
        }

        return true;
    }

    public static void onSheepTick(Sheep sheep) {

        EvokerConfig config = ModConfigs.getEvoker();

        if (config.allowWololo)
            return;

        if (sheep.getColor() == DyeColor.RED && sheep.tickCount < 5)
            sheep.setColor(DyeColor.BLUE);
    }


    public static void onDrops(ServerLevel level, Evoker evoker) {

        LootUtil.applyLootMultiplier(
                null,
                level,
                evoker,
                ModConfigs.getEvoker().lootMultiplier
        );
    }

}