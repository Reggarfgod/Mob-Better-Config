package com.reggarf.mods.mob_better_config.mixin;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(RangedAttribute.class)
public abstract class RangedAttributeMixin {

    @Overwrite
    public double sanitizeValue(double value) {
        return value;
    }
}