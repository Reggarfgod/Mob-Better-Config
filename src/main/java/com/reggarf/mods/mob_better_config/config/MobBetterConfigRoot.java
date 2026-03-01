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

    @ConfigEntry.Gui.CollapsibleObject
    public CreeperConfig creeper = new CreeperConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public WardenConfig warden = new WardenConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public VindicatorConfig vindicator = new VindicatorConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public StrayConfig stray = new StrayConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public SlimeConfig slime = new SlimeConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public HuskConfig husk = new HuskConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public PillagerConfig pillager = new PillagerConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public PhantomConfig phantom= new PhantomConfig();

    @ConfigEntry.Gui.CollapsibleObject

    public EndermanConfig enderman= new EndermanConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public WitherSkeletonConfig wither_skeleton= new WitherSkeletonConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public DrownedConfig drowned= new DrownedConfig();


}