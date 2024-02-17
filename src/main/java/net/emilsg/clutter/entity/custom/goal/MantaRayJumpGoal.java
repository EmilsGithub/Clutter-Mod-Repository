package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.MantaRayEntity;
import net.minecraft.entity.ai.goal.DiveJumpingGoal;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MantaRayJumpGoal extends DiveJumpingGoal {
    private static final int[] OFFSET_MULTIPLIERS = new int[]{0, 1, 4, 5, 6, 7};
    private final MantaRayEntity mantaRay;
    private final int chance;
    private boolean inWater;

    public MantaRayJumpGoal(MantaRayEntity mantaRay, int chance) {
        this.mantaRay = mantaRay;
        this.chance = toGoalTicks(chance);
    }

    public boolean canStart() {
        if (this.mantaRay.getRandom().nextInt(this.chance) != 0) {
            return false;
        } else {
            Direction direction = this.mantaRay.getMovementDirection();
            int i = direction.getOffsetX();
            int j = direction.getOffsetZ();
            BlockPos blockPos = this.mantaRay.getBlockPos();
            int[] var5 = OFFSET_MULTIPLIERS;
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                int k = var5[var7];
                if (!this.isWater(blockPos, i, j, k) || !this.isAirAbove(blockPos, i, j, k)) {
                    return false;
                }
            }

            return true;
        }
    }

    private boolean isWater(BlockPos pos, int offsetX, int offsetZ, int multiplier) {
        BlockPos blockPos = pos.add(offsetX * multiplier, 0, offsetZ * multiplier);
        return this.mantaRay.getWorld().getFluidState(blockPos).isIn(FluidTags.WATER) && !this.mantaRay.getWorld().getBlockState(blockPos).blocksMovement();
    }

    private boolean isAirAbove(BlockPos pos, int offsetX, int offsetZ, int multiplier) {
        return this.mantaRay.getWorld().getBlockState(pos.add(offsetX * multiplier, 1, offsetZ * multiplier)).isAir() && this.mantaRay.getWorld().getBlockState(pos.add(offsetX * multiplier, 2, offsetZ * multiplier)).isAir();
    }

    public boolean shouldContinue() {
        double d = this.mantaRay.getVelocity().y;
        return (!(d * d < 0.029999999329447746) || this.mantaRay.getPitch() == 0.0F || !(Math.abs(this.mantaRay.getPitch()) < 10.0F) || !this.mantaRay.isTouchingWater()) && !this.mantaRay.isOnGround();
    }

    public boolean canStop() {
        return false;
    }

    public void start() {
        Direction direction = this.mantaRay.getMovementDirection();
        this.mantaRay.setVelocity(this.mantaRay.getVelocity().add((double)direction.getOffsetX() * 0.6, 0.7, (double)direction.getOffsetZ() * 0.6));
        this.mantaRay.getNavigation().stop();
    }

    public void stop() {
        this.mantaRay.setPitch(0.0F);
    }

    public void tick() {
        boolean bl = this.inWater;
        if (!bl) {
            FluidState fluidState = this.mantaRay.getWorld().getFluidState(this.mantaRay.getBlockPos());
            this.inWater = fluidState.isIn(FluidTags.WATER);
        }

        if (this.inWater && !bl) {
            this.mantaRay.playSound(SoundEvents.ENTITY_DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        Vec3d vec3d = this.mantaRay.getVelocity();
        if (vec3d.y * vec3d.y < 0.029999999329447746 && this.mantaRay.getPitch() != 0.0F) {
            this.mantaRay.setPitch(MathHelper.lerpAngleDegrees(0.2F, this.mantaRay.getPitch(), 0.0F));
        } else if (vec3d.length() > 9.999999747378752E-6) {
            double d = vec3d.horizontalLength();
            double e = Math.atan2(-vec3d.y, d) * 57.2957763671875;
            this.mantaRay.setPitch((float)e);
        }

    }
}
