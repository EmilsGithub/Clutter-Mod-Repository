package net.emilsg.clutter.effect;

import net.emilsg.clutter.config.ModConfigs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.Objects;
import java.util.Random;

public class Meltdown extends StatusEffect {

    protected Meltdown(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        World world = entity.getWorld();
        Random random = new Random();
        int duration = Objects.requireNonNull(entity.getStatusEffect(ModEffects.MELTDOWN)).getDuration();

        if(world.isClient && random.nextInt(Math.abs(duration) + 20) <= 10) {
            world.addParticle(ParticleTypes.FLAME, true,entity.getX() + random.nextDouble() / 1.5 * (double) (random.nextBoolean() ? 1 : -1), entity.getY() + 1.0f + random.nextDouble() / 1.5 * (double) (random.nextBoolean() ? 1 : -1), entity.getZ() + random.nextDouble() / 1.5 * (double) (random.nextBoolean() ? 1 : -1), 0, 0, 0);

            if (random.nextInt(2) == 0) {
                world.addParticle(ParticleTypes.SMOKE, true, entity.getX() + random.nextDouble() / 1.5 * (double) (random.nextBoolean() ? 1 : -1), entity.getY() + 1.0f + random.nextDouble() / 1.5 * (double) (random.nextBoolean() ? 1 : -1), entity.getZ() + random.nextDouble() / 1.5 * (double) (random.nextBoolean() ? 1 : -1), 0, 0, 0);
                if (random.nextBoolean()) {
                    entity.playSound(SoundEvents.BLOCK_FIRE_AMBIENT, 1.0f, 1.0f);
                }
            }
        }

        if(!world.isClient) {
            if(entity.isTouchingWater()) {
                entity.removeStatusEffect(ModEffects.MELTDOWN);
                entity.getWorld().playSound(null, entity.getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.AMBIENT);
            } else if (!entity.isTouchingWater() && entity.isOnFire()) {
                if (ModConfigs.MELTDOWN_DESTROY_BLOCKS) {
                    world.createExplosion(entity, entity.getX(), entity.getBodyY(0.0625f), entity.getZ(), amplifier + 1.0f, World.ExplosionSourceType.TNT);
                } else {
                    world.createExplosion(entity, world.getDamageSources().explosion(entity, entity), new ExplosionBehavior(), entity.getX(), entity.getBodyY(0.0625f), entity.getZ(), amplifier + 1.0f, false, World.ExplosionSourceType.NONE);
                }
                entity.damage(world.getDamageSources().explosion(entity, entity), 4.0f + (amplifier * 2.0f));
                entity.removeStatusEffect(ModEffects.MELTDOWN);
                entity.setFireTicks(1);
            }
        }

        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
