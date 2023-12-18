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
    private static final Random random = new Random();

    protected Meltdown(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        World world = entity.getWorld();
        int duration = Objects.requireNonNull(entity.getStatusEffect(ModEffects.MELTDOWN)).getDuration();

        if(world.isClient && duration <= 20) {
            double offsetX = random.nextDouble() / 1.5 * (random.nextBoolean() ? 1 : -1);
            double offsetY = 1.0f + random.nextDouble() / 1.5 * (random.nextBoolean() ? 1 : -1);
            double offsetZ = random.nextDouble() / 1.5 * (random.nextBoolean() ? 1 : -1);

            world.addParticle(ParticleTypes.FLAME, true, entity.getX() + offsetX, entity.getY() + offsetY, entity.getZ() + offsetZ, 0, 0, 0);

            if (random.nextInt(2) == 0) {
                world.addParticle(ParticleTypes.SMOKE, true, entity.getX() + offsetX, entity.getY() + offsetY, entity.getZ() + offsetZ, 0, 0, 0);
                if (random.nextBoolean()) {
                    entity.playSound(SoundEvents.BLOCK_FIRE_AMBIENT, 1.0f, 1.0f);
                }
            }
        }

        if(!world.isClient) {
            if(entity.isTouchingWaterOrRain()) {
                entity.removeStatusEffect(ModEffects.MELTDOWN);
                world.playSound(null, entity.getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS);
            }
            else if(duration <= 1 && !entity.isTouchingWater()) {
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
