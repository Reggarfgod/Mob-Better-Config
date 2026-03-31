package com.reggarf.mods.mob_better_config.mixin.shulker;

import com.reggarf.mods.mob_better_config.config.ModConfigs;
import com.reggarf.mods.mob_better_config.config.ShulkerConfig;
import net.minecraft.world.entity.monster.Shulker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Shulker.class)
public class ShulkerTeleportMixin {

    @Inject(method = "teleportSomewhere", at = @At("HEAD"), cancellable = true)
    private void mbc$teleportControl(CallbackInfoReturnable<Boolean> cir) {

        Shulker self = (Shulker) (Object) this;
        ShulkerConfig config = ModConfigs.getShulker();

        if (!config.enableTeleport) {
            cir.setReturnValue(false);
            return;
        }

        if (self.getRandom().nextDouble() > config.teleportChance) {
            cir.setReturnValue(false);
        }
    }
}