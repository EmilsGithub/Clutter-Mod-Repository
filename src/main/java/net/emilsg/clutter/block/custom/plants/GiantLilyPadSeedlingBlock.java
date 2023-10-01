package net.emilsg.clutter.block.custom.plants;

import net.emilsg.clutter.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import static net.emilsg.clutter.block.custom.plants.GiantLilyPadBlock.FLOWERING;
import static net.emilsg.clutter.block.custom.plants.GiantLilyPadBlock.LILY_PAD_DIRECTIONS;

public class GiantLilyPadSeedlingBlock extends LilyPadBlock implements Fertilizable {
    public static final IntProperty AGE = Properties.AGE_3;

    public GiantLilyPadSeedlingBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(AGE, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        super.appendProperties(builder);
    }

    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(16) == 0) {
            grow(world, random, pos, state);
        }
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return (state.get(AGE) != 3 || (GiantLilyPadBlock.isValidPlacement((World) world, pos.north()) && GiantLilyPadBlock.isValidPlacement((World) world, pos.north().east()) && GiantLilyPadBlock.isValidPlacement((World) world, pos.east())));
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int growthAmount = state.get(AGE) + 1;

        if (state.get(AGE) + 1 > 3 && GiantLilyPadBlock.isValidPlacement(world, pos.north()) && GiantLilyPadBlock.isValidPlacement(world, pos.north().east()) && GiantLilyPadBlock.isValidPlacement(world, pos.east())) {
            if (random.nextBoolean()) {
                world.setBlockState(pos, ModBlocks.GIANT_LILY_PAD.getDefaultState().with(LILY_PAD_DIRECTIONS, GiantLilyPadBlock.LilyPadDirections.SOUTH_WEST), 3);
                world.setBlockState(pos.north(), ModBlocks.GIANT_LILY_PAD.getDefaultState().with(LILY_PAD_DIRECTIONS, GiantLilyPadBlock.LilyPadDirections.NORTH_WEST), 3);
                world.setBlockState(pos.east(), ModBlocks.GIANT_LILY_PAD.getDefaultState().with(LILY_PAD_DIRECTIONS, GiantLilyPadBlock.LilyPadDirections.SOUTH_EAST), 3);
                world.setBlockState(pos.north().east(), ModBlocks.GIANT_LILY_PAD.getDefaultState().with(LILY_PAD_DIRECTIONS, GiantLilyPadBlock.LilyPadDirections.NORTH_EAST), 3);
            } else {
                world.setBlockState(pos, ModBlocks.GIANT_LILY_PAD.getDefaultState().with(LILY_PAD_DIRECTIONS, GiantLilyPadBlock.LilyPadDirections.SOUTH_WEST).with(FLOWERING, true), 3);
                world.setBlockState(pos.north(), ModBlocks.GIANT_LILY_PAD.getDefaultState().with(LILY_PAD_DIRECTIONS, GiantLilyPadBlock.LilyPadDirections.NORTH_WEST).with(FLOWERING, true), 3);
                world.setBlockState(pos.east(), ModBlocks.GIANT_LILY_PAD.getDefaultState().with(LILY_PAD_DIRECTIONS, GiantLilyPadBlock.LilyPadDirections.SOUTH_EAST).with(FLOWERING, true), 3);
                world.setBlockState(pos.north().east(), ModBlocks.GIANT_LILY_PAD.getDefaultState().with(LILY_PAD_DIRECTIONS, GiantLilyPadBlock.LilyPadDirections.NORTH_EAST).with(FLOWERING, true), 3);
            }

        } else {
            if (state.get(AGE) != 3) world.setBlockState(pos, state.with(AGE, growthAmount), 2);
        }

    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return this.canPlantOnTop(world.getBlockState(blockPos), world, blockPos);
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos);
        FluidState fluidState2 = world.getFluidState(pos.up());
        return (fluidState.getFluid() == Fluids.WATER || floor.getBlock() instanceof IceBlock) && fluidState2.getFluid() == Fluids.EMPTY;
    }
}

