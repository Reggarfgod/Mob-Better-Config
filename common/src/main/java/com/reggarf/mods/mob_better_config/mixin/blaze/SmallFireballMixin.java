package com.reggarf.mods.mob_better_config.mixin.blaze;


import com.reggarf.mods.mob_better_config.events.BlazeEvents;
import com.reggarf.mods.mob_better_config.util.helper.DamageCompatUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.SmallFireball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmallFireball.class)
public class SmallFireballMixin {

    @Inject(
            method = "onHitEntity",
            at = @At("HEAD"),
            cancellable = true
    )
    private void modifyBlazeFireballDamage(net.minecraft.world.phys.EntityHitResult result, CallbackInfo ci) {

        SmallFireball fireball = (SmallFireball) (Object) this;

        if (!(fireball.getOwner() instanceof Blaze blaze))
            return;

        if (!(fireball.level() instanceof ServerLevel level))
            return;

        Entity target = result.getEntity();

        float damage = 5.0F;

        float[] holder = new float[]{damage};
        BlazeEvents.onFireballDamage(holder);
        damage = holder[0];

        int prevFire = target.getRemainingFireTicks();
        target.igniteForSeconds(5.0F);

        DamageSource source = fireball.damageSources().fireball(fireball, blaze);

        boolean success = DamageCompatUtil
                .hurt(target, level, source, damage);

        if (!success) {
            target.setRemainingFireTicks(prevFire);
        } else {
            net.minecraft.world.item.enchantment.EnchantmentHelper
                    .doPostAttackEffects(level, target, source);
        }

        ci.cancel(); // stop vanilla damage
    }
}