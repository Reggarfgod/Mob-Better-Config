package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "mob_better_config")
public class MobBetterConfigRoot implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public ZombieConfig zombie = new ZombieConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public SkeletonConfig skeleton = new SkeletonConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public ZombieVillagerConfig zombieVillager = new ZombieVillagerConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public WitchConfig witch = new WitchConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public SpiderConfig spider = new SpiderConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public CaveSpiderConfig caveSpider = new CaveSpiderConfig();

}