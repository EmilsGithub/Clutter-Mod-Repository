package net.emilsg.clutter.block.entity;

import net.emilsg.clutter.block.ModBlockEntities;
import net.emilsg.clutter.block.custom.BonfireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import static net.emilsg.clutter.block.custom.BonfireBlock.BLOCK_SHAPE;
import static net.minecraft.state.property.Properties.LIT;

public class BonfireBlockEntity extends BlockEntity {

    public BonfireBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BONFIRE, pos, state);
    }

    public static <T extends BlockEntity> void clientTick(World world, BlockPos blockPos, BlockState state, T t) {
        if (state.get(LIT)) {
            if (state.get(BLOCK_SHAPE).asString().startsWith("up_") && !state.get(BLOCK_SHAPE).asString().contains("center_")) {
                int i;
                Random random = world.random;
                if (random.nextFloat() < 0.08f) {
                    for (i = 0; i < random.nextInt(2) + 2; ++i) {
                        BonfireBlock.spawnThickSmokeParticles(world, blockPos, state, 0.3);
                    }
                }
            } else if (state.get(BLOCK_SHAPE).asString().contains("up_center")) {
                int i;
                Random random = world.random;
                if (random.nextFloat() < 0.08f) {
                    for (i = 0; i < random.nextInt(2) + 2; ++i) {
                        BonfireBlock.spawnThickSmokeParticles(world, blockPos, state, 1);
                    }
                }
            }
        }
    }
}
