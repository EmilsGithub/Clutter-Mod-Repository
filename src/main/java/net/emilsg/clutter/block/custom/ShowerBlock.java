package net.emilsg.clutter.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.BedPart;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
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

public class ShowerBlock extends HorizontalFacingBlock implements Waterloggable {
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final BooleanProperty ON = BooleanProperty.of("on");

    protected static final VoxelShape NORTH_TOP_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3, 9, 3, 13, 11, 13),
            Block.createCuboidShape(7, 12, 2, 9, 14, 9),
            Block.createCuboidShape(7, 11, 7, 9, 12, 9),
            Block.createCuboidShape(7, 0, 0, 9, 14, 2)
    );
    protected static final VoxelShape NORTH_BOTTOM_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(4.5, 3, 0.5, 5.5, 4, 1.5),
            Block.createCuboidShape(7, 3, 0, 9, 16, 2),
            Block.createCuboidShape(10.5, 3, 0.5, 11.5, 4, 1.5),
            Block.createCuboidShape(4, 1, 0, 12, 3, 2),
            Block.createCuboidShape(10, 4, 0, 12, 4.5, 2),
            Block.createCuboidShape(4, 4, 0, 6, 4.5, 2)
    );
    protected static final VoxelShape EAST_TOP_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3, 9, 3, 13, 11, 13),
            Block.createCuboidShape(7, 12, 7, 14, 14, 9),
            Block.createCuboidShape(7, 11, 7, 9, 12, 9),
            Block.createCuboidShape(14, 0, 7, 16, 14, 9)
    );
    protected static final VoxelShape EAST_BOTTOM_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(14.5, 3, 4.5, 15.5, 4, 5.5),
            Block.createCuboidShape(14, 3, 7, 16, 16, 9),
            Block.createCuboidShape(14.5, 3, 10.5, 15.5, 4, 11.5),
            Block.createCuboidShape(14, 1, 4, 16, 3, 12),
            Block.createCuboidShape(14, 4, 10, 16, 4.5, 12),
            Block.createCuboidShape(14, 4, 4, 16, 4.5, 6)
    );
    protected static final VoxelShape SOUTH_TOP_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3, 9, 3, 13, 11, 13),
            Block.createCuboidShape(7, 12, 7, 9, 14, 14),
            Block.createCuboidShape(7, 11, 7, 9, 12, 9),
            Block.createCuboidShape(7, 0, 14, 9, 14, 16)
    );
    protected static final VoxelShape SOUTH_BOTTOM_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(10.5, 3, 14.5, 11.5, 4, 15.5),
            Block.createCuboidShape(7, 3, 14, 9, 16, 16),
            Block.createCuboidShape(4.5, 3, 14.5, 5.5, 4, 15.5),
            Block.createCuboidShape(4, 1, 14, 12, 3, 16),
            Block.createCuboidShape(4, 4, 14, 6, 4.5, 16),
            Block.createCuboidShape(10, 4, 14, 12, 4.5, 16)
    );
    protected static final VoxelShape WEST_TOP_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3, 9, 3, 13, 11, 13),
            Block.createCuboidShape(2, 12, 7, 9, 14, 9),
            Block.createCuboidShape(7, 11, 7, 9, 12, 9),
            Block.createCuboidShape(0, 0, 7, 2, 14, 9)
    );
    protected static final VoxelShape WEST_BOTTOM_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.5, 3, 10.5, 1.5, 4, 11.5),
            Block.createCuboidShape(0, 3, 7, 2, 16, 9),
            Block.createCuboidShape(0.5, 3, 4.5, 1.5, 4, 5.5),
            Block.createCuboidShape(0, 1, 4, 2, 3, 12),
            Block.createCuboidShape(0, 4, 4, 2, 4.5, 6),
            Block.createCuboidShape(0, 4, 10, 2, 4.5, 12)
    );

    public ShowerBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(HALF, DoubleBlockHalf.LOWER).with(ON, false).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        if (state.get(FACING) == Direction.NORTH) {
            return switch (state.get(HALF)) {
                case UPPER -> NORTH_TOP_SHAPE;
                case LOWER -> NORTH_BOTTOM_SHAPE;
            };
        } else if (state.get(FACING) == Direction.EAST) {
            return switch (state.get(HALF)) {
                case UPPER -> EAST_TOP_SHAPE;
                case LOWER -> EAST_BOTTOM_SHAPE;
            };
        } else if (state.get(FACING) == Direction.SOUTH) {
            return switch (state.get(HALF)) {
                case UPPER -> SOUTH_TOP_SHAPE;
                case LOWER -> SOUTH_BOTTOM_SHAPE;
            };
        } else if (state.get(FACING) == Direction.WEST) {
            return switch (state.get(HALF)) {
                case UPPER -> WEST_TOP_SHAPE;
                case LOWER -> WEST_BOTTOM_SHAPE;
            };
        }

        return VoxelShapes.empty();
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

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockPos blockPos2 = blockPos.up();
        World world = ctx.getWorld();
        if (blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos2).canReplace(ctx)) {
            boolean bl = world.getFluidState(blockPos = ctx.getBlockPos()).getFluid() == Fluids.WATER;
            return (BlockState)this.getDefaultState().with(WATERLOGGED, bl).with(FACING, ctx.getPlayerFacing());
        }
        return null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockPos blockPos = pos.up();
        assert placer != null;
        world.setBlockState(blockPos, ShowerBlock.withWaterloggedState(world, blockPos, (BlockState)this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(FACING, placer.getHorizontalFacing())), Block.NOTIFY_ALL);
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
                ShowerBlock.onBreakInCreative(world, pos, state, player);
            } else {
                ShowerBlock.dropStacks(state, world, pos, null, player, player.getMainHandStack());
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, Blocks.AIR.getDefaultState(), blockEntity, stack);
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
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        boolean i = state.get(ON);
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            return ActionResult.PASS;
        }

        if (world.isClient && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isEmpty()) {
            return ActionResult.SUCCESS;
        } else if (hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isEmpty() && state.get(HALF) == DoubleBlockHalf.LOWER) {
            world.setBlockState(pos, state.with(ON, !i).with(HALF, DoubleBlockHalf.LOWER), Block.NOTIFY_ALL);
            world.setBlockState(pos.up(), state.with(ON, !i).with(HALF, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ON, HALF, WATERLOGGED, FACING);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(ON) && state.get(HALF) == DoubleBlockHalf.UPPER && !state.get(WATERLOGGED)) {
            spawnParticles(world, pos, ParticleTypes.FALLING_WATER);
         } else if (state.get(ON) && state.get(HALF) == DoubleBlockHalf.UPPER && state.get(WATERLOGGED)) {
            spawnParticles(world, pos, ParticleTypes.BUBBLE);
        }
    }

    public static void spawnParticles(World world, BlockPos pos, ParticleEffect particle) {
        Random random = world.getRandom();
        for (int i = 0; i < 1; i++) {
            world.addImportantParticle(particle, true, (double) pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double) (random.nextBoolean() ? 1 : -1), pos.getY() + 0.4 + random.nextDouble() / 24.0, (double) pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double) (random.nextBoolean() ? 1 : -1), 0.0, 0.0, 0.0);
            world.addParticle(particle, true, (double) pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double) (random.nextBoolean() ? 1 : -1), pos.getY() + 0.4 + random.nextDouble() / 24.0, (double) pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double) (random.nextBoolean() ? 1 : -1), 0.0, 0.0, 0.0);
        }
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
