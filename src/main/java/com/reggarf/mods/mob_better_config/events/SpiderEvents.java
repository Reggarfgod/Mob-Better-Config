package com.reggarf.mods.mob_better_config.events;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.SpiderConfig;
import com.reggarf.mods.mob_better_config.util.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Spider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class SpiderEvents {

    @SubscribeEvent
    public void onJoin(FinalizeSpawnEvent event) {

        if (!(event.getEntity() instanceof Spider spider))
            return;

        if (!(spider.level() instanceof ServerLevel level))
            return;

        SpiderConfig config = ModConfigs.getSpider();

        if (spider.getPersistentData().getBoolean("mob_better_config_spawned"))
            return;

        applyConfig(spider);

        for (int i = 1; i < config.spawnMultiplier; i++) {

            Spider extra = new Spider(EntityType.SPIDER, level);

            extra.moveTo(
                    spider.getX(),
                    spider.getY(),
                    spider.getZ(),
                    spider.getYRot(),
                    spider.getXRot()
            );

            extra.getPersistentData().putBoolean("mob_better_config_spawned", true);

            level.addFreshEntity(extra);
        }
    }

    private void applyConfig(Spider spider) {

        SpiderConfig config = ModConfigs.getSpider();
        RandomSource random = spider.level().getRandom();

        DoorBreakUtil.handleDoorBreaking(
                spider,
                config.canBreakDoors,
                config.doorBreakMode
        );
        if (config.CustomName) {
            MobNameUtil.applyRandomName(spider);
        }
        if (spider.getAttribute(Attributes.MAX_HEALTH) != null)
            spider.getAttribute(Attributes.MAX_HEALTH).setBaseValue(config.health);

        if (spider.getAttribute(Attributes.ATTACK_DAMAGE) != null)
            spider.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(config.attackDamage);

        if (spider.getAttribute(Attributes.MOVEMENT_SPEED) != null)
            spider.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(config.movementSpeed);

        if (spider.getAttribute(Attributes.FOLLOW_RANGE) != null)
            spider.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(config.followRange);

        spider.setHealth(config.health);

        if (config.fireImmune)
            spider.setRemainingFireTicks(0);

        if (config.glowing)
            spider.setGlowingTag(true);

        if (random.nextDouble() < config.randomArmorChance)
            ArmorUtil.equipRandomArmor(spider, random, 0.4f);
    }

    @SubscribeEvent
    public void onDamaged(LivingDamageEvent.Post event) {

        if (!(event.getEntity() instanceof Spider spider))
            return;

        if (!(spider.level() instanceof ServerLevel level))
            return;

        SpiderConfig config = ModConfigs.getSpider();

        ReinforcementUtil.trySpawnReinforcement(
                spider,
                level,
                config.reinforcementChance,
                4
        );
    }

    @SubscribeEvent
    public void onTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Spider spider))
            return;

        SpiderConfig config = ModConfigs.getSpider();

        if (config.fireImmune && spider.isOnFire())
            spider.clearFire();
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof Spider spider))
            return;

        if (!(spider.level() instanceof ServerLevel level))
            return;

        SpiderConfig config = ModConfigs.getSpider();

        LootUtil.applyLootMultiplier(event, level, spider, config.lootMultiplier);
    }
}