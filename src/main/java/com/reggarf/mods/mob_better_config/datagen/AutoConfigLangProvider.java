package com.reggarf.mods.mob_better_config.datagen;

import com.reggarf.mods.mob_better_config.config.MobBetterConfigRoot;
import com.reggarf.mods.mob_better_config.config.MobConfig;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.lang.reflect.Field;

public class AutoConfigLangProvider extends LanguageProvider {

    private static final String MOD_ID = "mob_better_config";

    public AutoConfigLangProvider(PackOutput output) {
        super(output, MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        add("text.autoconfig." + MOD_ID + ".title", "Mob Better Config");

        generateFromRoot();
    }

    private void generateFromRoot() {

        Field[] categories = MobBetterConfigRoot.class.getDeclaredFields();

        for (Field categoryField : categories) {

            String categoryName = categoryField.getName();

            add(
                    "text.autoconfig." + MOD_ID + ".option." + categoryName,
                    format(categoryName)
            );

            Class<?> categoryClass = categoryField.getType();

            for (Field mobField : categoryClass.getDeclaredFields()) {

                String mobName = mobField.getName();

                add(
                        "text.autoconfig." + MOD_ID + ".option." + categoryName + "." + mobName,
                        format(mobName)
                );

                generateMobOptions(categoryName, mobName);
            }
        }
    }

    private void generateMobOptions(String category, String mob) {

        for (Field option : MobConfig.class.getDeclaredFields()) {

            String optionName = option.getName();

            String key =
                    "text.autoconfig." +
                    MOD_ID +
                    ".option." +
                    category +
                    "." +
                    mob +
                    "." +
                    optionName;

            add(key, format(optionName));

            add(
                    key + ".@Tooltip",
                    "Configure " + format(optionName)
            );
        }
    }

    private String format(String name) {

        StringBuilder result = new StringBuilder();

        char[] chars = name.toCharArray();

        for (int i = 0; i < chars.length; i++) {

            if (i == 0) {
                result.append(Character.toUpperCase(chars[i]));
                continue;
            }

            if (Character.isUpperCase(chars[i])) {
                result.append(" ");
            }

            result.append(chars[i]);
        }

        return result.toString();
    }
}