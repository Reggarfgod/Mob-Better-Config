package com.reggarf.mods.mob_better_config.mixin.blaze;

import com.reggarf.mods.mob_better_config.events.BlazeEvents;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.SmallFireball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SmallFireball.class)
public class SmallFireballMixin {

    @ModifyArg(
        method = "onHitEntity",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
        ),
        index = 1
    )
    private float modifyBlazeFireballDamage(float damage) {

        SmallFireball fireball = (SmallFireball)(Object)this;

        if (fireball.getOwner() instanceof Blaze) {

            float[] holder = new float[]{damage};

            BlazeEvents.onFireballDamage(holder);

            return holder[0];
        }

        return damage;
    }
}