package com.reggarf.mods.mob_better_config.api;

import com.reggarf.mods.better_lib.message.api.JoinMessagePlugin;
import com.reggarf.mods.better_lib.message.api.JoinMessagePlugins;
import com.reggarf.mods.better_lib.message.api.JoinMessageSet;
import com.reggarf.mods.mob_better_config.Constants;


import java.util.List;

public class BetterMessages implements JoinMessagePlugin {

    @Override
    public String getModId() {
        return Constants.MOD_ID;
    }

    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public List<JoinMessageSet> getMessageSets() {
        return List.of(
            new JoinMessageSet()
                .addText("Hostile Mob Better Configuration is currently in beta development.\n" +
                        "If you find any bugs or configurations that are not working properly, please report them on GitHub Issues or on our Discord server.", 0xFFFFFF)
                .addLink("Discord", "https://discord.gg/JBZxyKNNmY", 0x00AAFF, "(Community)")
                .addLink("GitHub", "https://github.com/Reggarfgod/Mob-Better-Config", 0xAAAAAA, "(Source / Issues)")
                    .addLink("ZAP-Hosting", "https://zap-hosting.com/reggarf", 0x00FFFF, "20% off game servers with code Reggarf-1047")
                    .addText("This is a one-time message.", 0xFF0000)
        );
    }

    public static void register() {
        JoinMessagePlugins.register(new BetterMessages());
    }
}