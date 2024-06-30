package net.emilsg.clutter.block.custom;

import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class FoodBoxWithParticles extends FoodBoxBlock {

    public FoodBoxWithParticles(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        for (int i = 0; i < 3; ++i) {
            int randomX = random.nextInt(2) * 2 - 1;
            int randomZ = random.nextInt(2) * 2 - 1;
            double xPos = (double) pos.getX() + 0.5 + 0.125 * (double) randomX;
            double yPos = (float) pos.getY() + random.nextFloat();
            double zPos = (double) pos.getZ() + 0.5 + 0.125 * (double) randomZ;
            double xVel = random.nextFloat() * (float) randomX;
            double yVel = ((double) random.nextFloat() - 0.5) * 0.125;
            double zVel = random.nextFloat() * (float) randomZ;
            world.addParticle(ParticleTypes.PORTAL, xPos, yPos, zPos, xVel, yVel, zVel);
        }
    }
}
