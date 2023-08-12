package net.emilsg.clutter.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class TallLampBlock extends Block implements Waterloggable {
    public static final BooleanProperty LIT = Properties.LIT;
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final VoxelShape TOP_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3.0, 7.0, 3.0, 13.0, 16.0, 13.0),
            Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 7.0, 9.0)
    );
    private static final VoxelShape BOTTOM_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 2.0, 11.0),
            Block.createCuboidShape(7.0, 2.0, 7.0, 9.0, 16.0, 9.0)
    );

    public TallLampBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(HALF, DoubleBlockHalf.LOWER).with(WATERLOGGED, false).with(LIT, false));
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            BlockState litState = state.cycle(LIT);
            world.setBlockState(pos, litState.with(HALF, state.get(HALF)), 10);
            if (state.get(HALF) == DoubleBlockHalf.LOWER) {
                world.setBlockState(pos.up(), litState.with(HALF, world.getBlockState(pos.up()).get(HALF)).with(WATERLOGGED, world.getBlockState(pos.up()).get(WATERLOGGED)), 10);
            } else {
                world.setBlockState(pos.down(), litState.with(HALF, world.getBlockState(pos.down()).get(HALF)).with(WATERLOGGED, world.getBlockState(pos.down()).get(WATERLOGGED)), 10);
            }
            playSound(state.get(LIT) ? SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF : SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, pos, world);
            return ActionResult.success(world.isClient);
    }

    private static void playSound(SoundEvent soundEvent, BlockPos pos, World world) {
        world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.25f);
    }

    public static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(LIT) ? litLevel : 0;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT, WATERLOGGED, HALF);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(HALF)) {
            case UPPER -> TOP_SHAPE;
            case LOWER -> BOTTOM_SHAPE;
        };
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (!(direction.getAxis() != Direction.Axis.Y || doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || neighborState.isOf(this) && neighborState.get(HALF) != doubleBlockHalf)) {
            return Blocks.AIR.getDefaultState();
        }
        if (doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    protected static void onBreakInCreative(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos blockPos;
        BlockState blockState;
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.UPPER && (blockState = world.getBlockState(blockPos = pos.down())).isOf(state.getBlock()) && blockState.get(HALF) == DoubleBlockHalf.LOWER) {
            BlockState blockState2 = blockState.getFluidState().isOf(Fluids.WATER) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
            world.setBlockState(blockPos, blockState2, Block.NOTIFY_ALL | Block.SKIP_DROPS);
            world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, blockPos, Block.getRawIdFromState(blockState));
        }
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockPos blockPos2 = blockPos.up();
        World world = ctx.getWorld();
        if (blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos2).canReplace(ctx)) {
            boolean bl = world.getFluidState(blockPos = ctx.getBlockPos()).getFluid() == Fluids.WATER;
            return (BlockState)this.getDefaultState().with(WATERLOGGED, bl).with(LIT, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
        }
        return null;
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            boolean bl = (Boolean)state.get(LIT);
            if (bl != world.isReceivingRedstonePower(pos)) {
                if (bl) {
                    world.scheduleBlockTick(pos, this, 4);
                } else {
                    BlockState litState = state.cycle(LIT);
                    world.setBlockState(pos, litState.with(HALF, state.get(HALF)), 2);
                    if (state.get(HALF) == DoubleBlockHalf.LOWER) {
                        world.setBlockState(pos.up(), litState.with(HALF, world.getBlockState(pos.up()).get(HALF)).with(WATERLOGGED, world.getBlockState(pos.up()).get(WATERLOGGED)), 2);
                    } else {
                        world.setBlockState(pos.down(), litState.with(HALF, world.getBlockState(pos.down()).get(HALF)).with(WATERLOGGED, world.getBlockState(pos.down()).get(WATERLOGGED)), 2);
                    }
                }
            }

        }
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if ((Boolean)state.get(LIT) && !world.isReceivingRedstonePower(pos)) {
            BlockState litState = state.cycle(LIT);
            world.setBlockState(pos, litState.with(HALF, state.get(HALF)), 2);
            if (state.get(HALF) == DoubleBlockHalf.LOWER) {
                world.setBlockState(pos.up(), litState.with(HALF, world.getBlockState(pos.up()).get(HALF)).with(WATERLOGGED, world.getBlockState(pos.up()).get(WATERLOGGED)), 2);
            } else {
                world.setBlockState(pos.down(), litState.with(HALF, world.getBlockState(pos.down()).get(HALF)).with(WATERLOGGED, world.getBlockState(pos.down()).get(WATERLOGGED)), 2);
            }
        }

    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockPos blockPos = pos.up();
        world.setBlockState(blockPos, TallLampBlock.withWaterloggedState(world, blockPos, (BlockState)this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER)), Block.NOTIFY_ALL);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            BlockState blockState = world.getBlockState(pos.down());
            return blockState.isOf(this) && blockState.get(HALF) == DoubleBlockHalf.LOWER;
        }
        return super.canPlaceAt(state, world, pos);
    }

    public static BlockState withWaterloggedState(WorldView world, BlockPos pos, BlockState state) {
        if (state.contains(Properties.WATERLOGGED)) {
            return (BlockState)state.with(Properties.WATERLOGGED, world.isWater(pos));
        }
        return state;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            if (player.isCreative()) {
                TallLampBlock.onBreakInCreative(world, pos, state, player);
            } else {
                TallLampBlock.dropStacks(state, world, pos, null, player, player.getMainHandStack());
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, Blocks.AIR.getDefaultState(), blockEntity, stack);
    }

    @Override
    public long getRenderingSeed(BlockState state, BlockPos pos) {
        return MathHelper.hashCode(pos.getX(), pos.down(state.get(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.get(Properties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {

            world.setBlockState(pos, (BlockState)((BlockState)state.with(WATERLOGGED, true)), Block.NOTIFY_ALL);
            world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            return true;
        }
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

}
