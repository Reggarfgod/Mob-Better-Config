package com.reggarf.mods.mob_better_config.api;

import com.reggarf.mods.better_lib.message.api.OnlineMessagePlugin;
import com.reggarf.mods.mob_better_config.Constants;


public class OnlineMessages implements OnlineMessagePlugin {
    @Override
    public String getModId() {
        return Constants.MOD_ID;
    }

    @Override
    public String getMessageUrl() {
        return "https://raw.githubusercontent.com/Reggarfgod/Mob-Better-Config/refs/heads/master/messages.txt?token=GHSAT0AAAAAADVZTHLS2FPZ5UAVYEV6U6AK2NMADIA";
    }

    @Override
    public String getClickableUrl() {
        return "https://raw.githubusercontent.com/Reggarfgod/Mob-Better-Config/refs/heads/master/url.txt?token=GHSAT0AAAAAADVZTHLT67D34RCI2FNHJ4F42NMAEUQ";
    }

    @Override
    public boolean isOnlineMessageEnabled() {
        return true;
    }
}