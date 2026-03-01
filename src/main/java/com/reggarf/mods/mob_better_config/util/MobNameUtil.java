package com.reggarf.mods.mob_better_config.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.Random;

public class MobNameUtil {

    private static final Random RANDOM = new Random();
    private static final String NAME_TAG = "mob_better_config_named";

    private static final List<String> PREFIXES = List.of(
            "Abys","Aero","Aeth","Agon","Alda","Apex","Arca","Arid","Ashn","Astr",
            "Azur","Bane","Bask","Blad","Blaz","Blit","Bolt","Brim","Brut","Cald",
            "Calm","Carn","Chao","Cind","Claw","Colo","Core","Crag","Crys","Damn",
            "Dare","Dark","Dawn","Daze","Dead","Doom","Drax","Dusk","Ebon","Echo",
            "Eldr","Embe","Envy","Eter","Fang","Fate","Fell","Fera","Flam","Fogl",
            "Forn","Fren","Fros","Fury","Gale","Gild","Glim","Gore","Grav","Grim",
            "Haze","Hell","Hexx","Holl","Hunt","Husk","Ignis","Iris","Iron","Jade",
            "Jinx","Kaos","Keen","Khan","Kilo","King","Knox","Lava","Lich","Lore",
            "Luna","Lurk","Mage","Mara","Mist","Myth","Neth","Nigh","Nova","Obey",
            "Obsc","Omen","Onyx","Ooze","Orin","Pact","Pain","Peak","Phan","Plag",
            "Pyro","Quak","Rage","Raze","Reap","Rift","Rime","Ruin","Rune","Rust",
            "Sage","Scor","Shad","Skul","Slay","Smog","Snar","Soul","Spik","Star",
            "Storm","Tale","Temp","Thon","Thra","Thrn","Tide","Tomb","Torn","Toxx",
            "Umbr","Vain","Vapo","Vast","Veno","Vile","Void","Volt","Warp","Weep",
            "Wick","Wild","Wind","Wisp","Wrat","Yell","Yore","Zany","Zeal","Zest",
            "Zion","Zorn","Zyra","Aqua","Argo","Bask","Blod","Bran","Bryn","Cave",
            "Cerb","Cler","Drak","Drip","Eira","Falk","Grit","Hark","Icer","Jolt",
            "Karn","Lorn","Mire","Norn","Oath","Pale","Quil","Rook","Silt","Tarn",
            "Ulti","Vorn","Weld","Xeno","Ymir","Zeth","Auro","Brax","Cair","Dreg",
            "Eira","Fira","Gorn","Hext","Icar","Jora","Kora","Lira","Mord","Nira"
    );

    private static final List<String> SUFFIXES = List.of(
            "lord","bane","fang","doom","reap","slay","rend","gore","hunt","wrath",
            "claw","scar","fury","bolt","rift","hexs","roar","grip","tomb","veil",
            "rage","fall","burn","fist","snap","void","soul","maul","bane","grim",
            "tide","lash","drak","wolf","hawk","crow","wisp","mist","bark","husk",
            "blod","drip","spik","thorn","lash","fang","clot","tusk","mane","gaze",
            "seer","omen","fear","vile","rotc","rust","dust","mire","warp","rift",
            "gale","zeal","core","dusk","dawn","haze","smog","glow","flare","blaz",
            "flux","nova","void","fang","sire","king","arch","drag","skul","lich",
            "wyrm","beas","dred","harb","warl","sent","herd","grim","rune","magi",
            "goli","tita","khan","rava","fend","rend","maim","scre","shad","gore",
            "fang","snar","bark","roar","howl","wail","weep","keen","dire","wild",
            "storm","volt","quak","trem","carn","slag","slag","brut","rage","fury",
            "venm","toxi","pois","acid","fume","lava","melt","scor","ashn","coal",
            "fros","glac","hail","snow","icel","rime","mist","rain","wind","gale",
            "warp","void","rift","time","omen","fate","doom","bane","fall","rise",
            "lord","dame","fiend","ghos","wrat","zeal","bane","slay","rend","hunt",
            "mend","rend","bend","send","torn","shrd","crsh","smsh","gnaw","bite",
            "stng","pier","stab","hack","bash","slam","slam","lash","lash","lash",
            "rage","rage","rage","doom","doom","doom","bane","bane","bane","void"
    );

    public static void applyRandomName(LivingEntity entity) {

        // Prevent renaming on reload
        if (entity.getPersistentData().getBoolean(NAME_TAG))
            return;

        String prefix = PREFIXES.get(RANDOM.nextInt(PREFIXES.size()));
        String suffix = SUFFIXES.get(RANDOM.nextInt(SUFFIXES.size()));

        // Get vanilla mob name (Enderman, Zombie, etc.)
        String mobName = entity.getType().getDescription().getString();

        String finalName = prefix + " " + suffix + " " + mobName;

        entity.setCustomName(
                Component.literal(finalName)
                        .withStyle(ChatFormatting.GREEN)
        );

        entity.setCustomNameVisible(false);
        entity.getPersistentData().putBoolean(NAME_TAG, true);
    }

}