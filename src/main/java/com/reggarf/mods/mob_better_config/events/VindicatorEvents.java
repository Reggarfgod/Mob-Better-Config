package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.VindicatorConfig;
import com.reggarf.mods.mob_better_config.util.LootUtil;
import com.reggarf.mods.mob_better_config.util.MobNameUtil;
import com.reggarf.mods.mob_better_config.util.ReinforcementUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Vindicator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

public class VindicatorEvents {

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Vindicator vindicator))
            return;

        if (!(vindicator.level() instanceof ServerLevel level))
            return;

        VindicatorConfig config = ModConfigs.getVindicator();

        if (vindicator.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(vindicator);

        // Johnny Mode
        if (config.enableJohnnyMode) {
            vindicator.setCustomName(
                    net.minecraft.network.chat.Component.literal("Johnny"));
            vindicator.setPersistenceRequired();
        }

        for (int i = 1; i < config.spawnMultiplier; i++) {

            Vindicator extra = new Vindicator(EntityType.VINDICATOR, level);

            extra.moveTo(
                    vindicator.getX(),
                    vindicator.getY(),
                    vindicator.getZ(),
                    vindicator.getYRot(),
                    vindicator.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);

            level.addFreshEntity(extra);
        }
    }


    private void applyConfig(Vindicator vindicator) {

        VindicatorConfig config = ModConfigs.getVindicator();
        if (config.CustomName) {
            MobNameUtil.applyRandomName(vindicator);
        }
        if (vindicator.getAttribute(Attributes.MAX_HEALTH) != null)
            vindicator.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(config.health);

        if (vindicator.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            vindicator.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(config.attackDamage);

        if (vindicator.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            vindicator.getAttribute(Attributes.MOVEMENT_SPEED)
                    .setBaseValue(config.movementSpeed);

        if (vindicator.getAttribute(Attributes.FOLLOW_RANGE) != null)
            vindicator.getAttribute(Attributes.FOLLOW_RANGE)
                    .setBaseValue(config.followRange);

        vindicator.setHealth(config.health);

        if (config.fireImmune)
            vindicator.setRemainingFireTicks(0);

        if (config.glowing)
            vindicator.setGlowingTag(true);
    }

    @SubscribeEvent
    public void onDamaged(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Vindicator vindicator))
            return;

        if (!(vindicator.level() instanceof ServerLevel level))
            return;

        VindicatorConfig config = ModConfigs.getVindicator();

        ReinforcementUtil.trySpawnReinforcement(
                vindicator,
                level,
                config.reinforcementChance,
                5
        );
    }


    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Vindicator vindicator))
            return;

        if (!(vindicator.level() instanceof ServerLevel level))
            return;

        VindicatorConfig config = ModConfigs.getVindicator();

        LootUtil.applyLootMultiplier(
                event,
                level,
                vindicator,
                config.lootMultiplier
        );
    }
}