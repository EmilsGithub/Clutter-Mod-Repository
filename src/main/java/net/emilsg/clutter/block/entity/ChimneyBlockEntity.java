package net.emilsg.clutter.block.entity;

import net.emilsg.clutter.block.custom.ChimneyBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ChimneyBlockEntity extends BlockEntity {

    public ChimneyBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHIMNEY, pos, state);
    }

    public static <T extends BlockEntity> void clientTick(World world, BlockPos blockPos, BlockState state, T t) {
        int i;
        Random random = world.random;
        if (random.nextFloat() < 0.11f) {
            for (i = 0; i < random.nextInt(2) + 2; ++i) {
                ChimneyBlock.spawnSmokeParticles(world, blockPos, true, state);
            }
        }
    }
}
