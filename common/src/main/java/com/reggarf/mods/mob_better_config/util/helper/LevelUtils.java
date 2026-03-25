package com.reggarf.mods.mob_better_config.util.helper;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class LevelUtils {

    /**
     * Safe check for mob griefing across versions
     */
    public static boolean canMobGrief(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            return serverLevel.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
        }
        return false;
    }

    /**
     * Safe destroy block (server only)
     */
    public static void destroyBlock(Level level, BlockPos pos, Mob mob) {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.destroyBlock(pos, true, mob);
        }
    }
}