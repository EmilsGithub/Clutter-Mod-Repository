package net.emilsg.clutter.mixin;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.effect.ModEffects;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Final @Shadow private static TrackedData<Integer> POTION_SWIRLS_COLOR;

    @Final @Shadow private static TrackedData<Boolean> POTION_SWIRLS_AMBIENT;

    @Shadow public abstract Map<StatusEffect, StatusEffectInstance> getActiveStatusEffects();

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow public abstract @Nullable StatusEffectInstance getStatusEffect(StatusEffect effect);

    @Shadow public abstract @Nullable LivingEntity getPrimeAdversary();

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (source.getSource() instanceof ProjectileEntity projectile && self.hasStatusEffect(ModEffects.SHIMMER)) {
            projectile.setVelocity(projectile.getVelocity().multiply(-1));
            cir.setReturnValue(false);
        }
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
    private float multiplyDamage(float amount) {
        if (this.hasStatusEffect(ModEffects.VULNERABILITY)) {
            return amount + (amount * ((Objects.requireNonNull(this.getStatusEffect(ModEffects.VULNERABILITY)).getAmplifier() + 1) * 0.15f));
        }
        return amount;
    }

    @Inject(method = "updatePotionVisibility", at = @At("HEAD"), cancellable = true)
    protected void onUpdatePotionVisibility(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (!this.getActiveStatusEffects().isEmpty() && this.hasStatusEffect(ModEffects.SHIMMER) && livingEntity instanceof PlayerEntity) {
            livingEntity.getDataTracker().set(POTION_SWIRLS_AMBIENT, false);
            livingEntity.getDataTracker().set(POTION_SWIRLS_COLOR, 0);
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "shouldDropLoot", cancellable = true)
    private void necrosisDrops(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if(livingEntity instanceof HoglinEntity || livingEntity instanceof PiglinEntity || livingEntity instanceof VillagerEntity) {
            int necrosisLevel = EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(new Identifier(Clutter.MOD_ID, "necrosis")), Objects.requireNonNull(this.getPrimeAdversary()).getEquippedStack(EquipmentSlot.MAINHAND));
            if (necrosisLevel > 0) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "dropXp", cancellable = true)
    private void necrosisXPDrops(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if(livingEntity instanceof HoglinEntity || livingEntity instanceof PiglinEntity || livingEntity instanceof VillagerEntity) {
            int necrosisLevel = EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(new Identifier(Clutter.MOD_ID, "necrosis")), Objects.requireNonNull(this.getPrimeAdversary()).getEquippedStack(EquipmentSlot.MAINHAND));
            if (necrosisLevel > 0) {
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        World world = livingEntity.getWorld();
        Random random = world.getRandom();

        if(!world.isClient) return;
        if (livingEntity instanceof PlayerEntity player && player.isSpectator()) return;
        if (livingEntity.getActiveStatusEffects().isEmpty() || !livingEntity.hasStatusEffect(ModEffects.SHIMMER)) return;

        if (random.nextFloat() < 0.5f) {
            Vec3d entityPos = livingEntity.getPos();
            double x = entityPos.x + (random.nextDouble() - 0.5) * 2.0 * livingEntity.getWidth();
            double y = entityPos.y + random.nextDouble() * livingEntity.getHeight();
            double z = entityPos.z + (random.nextDouble() - 0.5) * 2.0 * livingEntity.getWidth();

            world.addParticle(ParticleTypes.GLOW, x, y, z, 0, 0, 0);
        }
    }
}
