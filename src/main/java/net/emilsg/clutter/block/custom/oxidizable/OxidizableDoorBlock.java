package net.emilsg.clutter.block.custom.oxidizable;

import net.emilsg.clutter.util.ModBlockTags;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class OxidizableDoorBlock extends DoorBlock implements Oxidizable {

    private final OxidationLevel oxidationLevel;

    public OxidizableDoorBlock(OxidationLevel oxidationLevel, Settings settings, BlockSetType blockSetType) {
        super(settings, blockSetType);
        this.oxidationLevel = oxidationLevel;
    }


    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            if (neighborState.isIn(ModBlockTags.COPPER_DOORS) && neighborState.get(HALF) != doubleBlockHalf) {
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(FACING, neighborState.get(FACING))).with(OPEN, neighborState.get(OPEN))).with(HINGE, neighborState.get(HINGE))).with(POWERED, neighborState.get(POWERED));
            }
            return Blocks.AIR.getDefaultState();
        }
        if (doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.testLowerOxidation(state, world, pos);
        this.testUpperOxidation(state, world, pos);
        this.tickDegradation(state, world, pos, random);
    }

    public boolean hasRandomTicks(BlockState state) {
        return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
    }

    public OxidationLevel getDegradationLevel() {
        return this.oxidationLevel;
    }

    private void testUpperOxidation(BlockState upperState, World world, BlockPos upperPos) {
        if(upperState.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER && world.getBlockState(upperPos.down()).getBlock() instanceof OxidizableDoorBlock) {
            BlockState lowerState = world.getBlockState(upperPos.down());
            BlockPos lowerPos = upperPos.down();

            if(getOxidationValue(world, lowerPos) > getOxidationValue(world, upperPos)) {
                world.setBlockState(upperPos, lowerState.with(Properties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
            } else if(getOxidationValue(world, upperPos) > getOxidationValue(world, lowerPos)) {
                world.setBlockState(upperPos.down(), upperState.with(Properties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER), Block.NOTIFY_ALL);
            }
        }
    }

    private void testLowerOxidation(BlockState lowerState, World world, BlockPos lowerPos) {
        if(lowerState.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER && world.getBlockState(lowerPos.up()).getBlock() instanceof OxidizableDoorBlock) {
            BlockState upperState = world.getBlockState(lowerPos.up());
            BlockPos upperPos = lowerPos.up();

            if(getOxidationValue(world, lowerPos) > getOxidationValue(world, upperPos)) {
                world.setBlockState(lowerPos.up(), lowerState.with(Properties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
            } else if(getOxidationValue(world, upperPos) > getOxidationValue(world, lowerPos)) {
                world.setBlockState(lowerPos, upperState.with(Properties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER), Block.NOTIFY_ALL);
            }
        }
    }

    private int getOxidationValue(World world, BlockPos pos) {
        OxidationLevel level = getOxidationLevel(world, pos);
        if(level == OxidationLevel.UNAFFECTED) return 1;
        if(level == OxidationLevel.EXPOSED) return 2;
        if(level == OxidationLevel.WEATHERED) return 3;
        return 4;
    }

    public static OxidationLevel getOxidationLevel(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof Oxidizable oxidizable) {
            return oxidizable.getDegradationLevel();
        }
        return null;
    }
}
