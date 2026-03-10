package com.reggarf.mods.mob_better_config.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "mob_better_config")
public class MobBetterConfigRoot implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public Overworld overworld = new Overworld();

    @ConfigEntry.Gui.CollapsibleObject
    public Nether nether = new Nether();

    @ConfigEntry.Gui.CollapsibleObject
    public End end = new End();

    @ConfigEntry.Gui.CollapsibleObject
    public Boss bosses = new Boss();


    public static class Overworld {

        @ConfigEntry.Gui.CollapsibleObject
        public MobConfig zombie = new MobConfig();

        @ConfigEntry.Gui.CollapsibleObject
        public MobConfig skeleton = new MobConfig();

        @ConfigEntry.Gui.CollapsibleObject
        public MobConfig creeper = new MobConfig();

        @ConfigEntry.Gui.CollapsibleObject
        public MobConfig spider = new MobConfig();

        @ConfigEntry.Gui.CollapsibleObject
        public MobConfig caveSpider = new MobConfig();

        @ConfigEntry.Gui.CollapsibleObject
        public MobConfig enderman = new MobConfig();

        @ConfigEntry.Gui.CollapsibleObject
        public MobConfig witch = new MobConfig();
    }

    public static class Nether {

        @ConfigEntry.Gui.CollapsibleObject
        public MobConfig blaze = new MobConfig();

        @ConfigEntry.Gui.CollapsibleObject
        public MobConfig ghast = new MobConfig();

        @ConfigEntry.Gui.CollapsibleObject
        public MobConfig magmaCube = new MobConfig();
    }

    public static class End {

        @ConfigEntry.Gui.CollapsibleObject
        public MobConfig shulker = new MobConfig();
    }

    public static class Boss {

        @ConfigEntry.Gui.CollapsibleObject
        public MobConfig warden = new MobConfig();

        @ConfigEntry.Gui.CollapsibleObject
        public MobConfig wither = new MobConfig();

        @ConfigEntry.Gui.CollapsibleObject
        public MobConfig enderDragon = new MobConfig();
    }
}