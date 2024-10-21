package net.emilsg.clutter.block.custom;

import com.mojang.serialization.MapCodec;
import net.emilsg.clutter.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class TallCurtainBlock extends HorizontalFacingBlock implements Waterloggable {
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty OPEN = ModProperties.OPEN;
    public static final EnumProperty<DirectionShape> DIRECTION_SHAPE = EnumProperty.of("direction_shape", TallCurtainBlock.DirectionShape.class);
    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 2.0);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 14.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 16.0, 16.0);

    public TallCurtainBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.NORTH).with(HALF, DoubleBlockHalf.UPPER).with(OPEN, false).with(DIRECTION_SHAPE, DirectionShape.ALONE));
    }

    public static final MapCodec<TallCurtainBlock> CODEC = createCodec(TallCurtainBlock::new);

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    protected static void onBreakInCreative(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
            BlockPos blockPos = pos.down();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isOf(state.getBlock()) && blockState.get(HALF) == DoubleBlockHalf.LOWER) {
                BlockState blockState2 = blockState.getFluidState().isOf(Fluids.WATER) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
                world.setBlockState(blockPos, blockState2, 35);
                world.syncWorldEvent(player, 2001, blockPos, Block.getRawIdFromState(blockState));
            }
        }

    }

    public static BlockState withWaterloggedState(WorldView world, BlockPos pos, BlockState state) {
        if (state.contains(Properties.WATERLOGGED)) {
            return state.with(Properties.WATERLOGGED, world.isWater(pos));
        }
        return state;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isEmpty()) {
            world.playSound(null, pos, SoundEvents.BLOCK_WOOL_STEP, SoundCategory.BLOCKS, 1.0f, 1.0f);

            Deque<BlockPos> queue = new ArrayDeque<>();
            Set<BlockPos> visited = new HashSet<>();
            queue.add(pos);
            visited.add(pos);
            while (!queue.isEmpty()) {
                BlockPos currPos = queue.poll();
                BlockState currState = world.getBlockState(currPos);

                if (currState.isOf(state.getBlock()) && currState.get(FACING) == state.get(FACING) && currState.get(OPEN) == state.get(OPEN)) {

                    world.setBlockState(currPos, currState.cycle(OPEN), 3);
                    for (Direction dir : Direction.values()) {
                        BlockPos nextPos = currPos.offset(dir);
                        if (!visited.contains(nextPos)) {
                            queue.add(nextPos);
                            visited.add(nextPos);
                        }
                    }
                }
            }
            return ActionResult.SUCCESS;
        } else if (hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isEmpty()) {
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (state.get(FACING) == Direction.NORTH) {
            if (world.getBlockState(pos.west()).getBlock() instanceof TallCurtainBlock && world.getBlockState(pos.east()).getBlock() instanceof TallCurtainBlock) {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION_SHAPE, DirectionShape.MIDDLE), Block.NOTIFY_ALL);
            } else if (world.getBlockState(pos.west()).getBlock() instanceof TallCurtainBlock && !(world.getBlockState(pos.east()).getBlock() instanceof TallCurtainBlock)) {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION_SHAPE, DirectionShape.RIGHT), Block.NOTIFY_ALL);
            } else if (!(world.getBlockState(pos.west()).getBlock() instanceof TallCurtainBlock) && world.getBlockState(pos.east()).getBlock() instanceof TallCurtainBlock) {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION_SHAPE, DirectionShape.LEFT), Block.NOTIFY_ALL);
            } else {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION_SHAPE, DirectionShape.ALONE), Block.NOTIFY_ALL);
            }
        } else if (state.get(FACING) == Direction.SOUTH) {
            if (world.getBlockState(pos.east()).getBlock() instanceof TallCurtainBlock && world.getBlockState(pos.west()).getBlock() instanceof TallCurtainBlock) {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION_SHAPE, DirectionShape.MIDDLE), Block.NOTIFY_ALL);
            } else if (world.getBlockState(pos.east()).getBlock() instanceof TallCurtainBlock && !(world.getBlockState(pos.west()).getBlock() instanceof TallCurtainBlock)) {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION_SHAPE, DirectionShape.RIGHT), Block.NOTIFY_ALL);
            } else if (!(world.getBlockState(pos.east()).getBlock() instanceof TallCurtainBlock) && world.getBlockState(pos.west()).getBlock() instanceof TallCurtainBlock) {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION_SHAPE, DirectionShape.LEFT), Block.NOTIFY_ALL);
            } else {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION_SHAPE, DirectionShape.ALONE), Block.NOTIFY_ALL);
            }
        } else if (state.get(FACING) == Direction.WEST) {
            if (world.getBlockState(pos.north()).getBlock() instanceof TallCurtainBlock && world.getBlockState(pos.south()).getBlock() instanceof TallCurtainBlock) {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION_SHAPE, DirectionShape.MIDDLE), Block.NOTIFY_ALL);
            } else if (world.getBlockState(pos.north()).getBlock() instanceof TallCurtainBlock && !(world.getBlockState(pos.south()).getBlock() instanceof TallCurtainBlock)) {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION_SHAPE, DirectionShape.LEFT), Block.NOTIFY_ALL);
            } else if (!(world.getBlockState(pos.north()).getBlock() instanceof TallCurtainBlock) && world.getBlockState(pos.south()).getBlock() instanceof TallCurtainBlock) {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION_SHAPE, DirectionShape.RIGHT), Block.NOTIFY_ALL);
            } else {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION_SHAPE, DirectionShape.ALONE), Block.NOTIFY_ALL);
            }
        } else if (state.get(FACING) == Direction.EAST) {
            if (world.getBlockState(pos.south()).getBlock() instanceof TallCurtainBlock && world.getBlockState(pos.north()).getBlock() instanceof TallCurtainBlock) {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION_SHAPE, DirectionShape.MIDDLE), Block.NOTIFY_ALL);
            } else if (world.getBlockState(pos.south()).getBlock() instanceof TallCurtainBlock && !(world.getBlockState(pos.north()).getBlock() instanceof TallCurtainBlock)) {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION_SHAPE, DirectionShape.LEFT), Block.NOTIFY_ALL);
            } else if (!(world.getBlockState(pos.south()).getBlock() instanceof TallCurtainBlock) && world.getBlockState(pos.north()).getBlock() instanceof TallCurtainBlock) {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION_SHAPE, DirectionShape.RIGHT), Block.NOTIFY_ALL);
            } else {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION_SHAPE, DirectionShape.ALONE), Block.NOTIFY_ALL);
            }
        }
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(HALF).equals(DoubleBlockHalf.UPPER)) {
            return true;
        } else
            return state.get(HALF).equals(DoubleBlockHalf.LOWER) && world.getBlockState(pos.up()).getBlock() instanceof TallCurtainBlock;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockPos blockPos = pos.down();
        assert placer != null;
        world.setBlockState(blockPos, TallCurtainBlock.withWaterloggedState(world, blockPos, this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(FACING, placer.getHorizontalFacing())), Block.NOTIFY_ALL);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, HALF, FACING, OPEN, DIRECTION_SHAPE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction i = state.get(FACING);
        return switch (i) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> VoxelShapes.empty();
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

    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            if (player.isCreative()) {
                onBreakInCreative(world, pos, state, player);
            } else {
                dropStacks(state, world, pos, null, player, player.getMainHandStack());
            }
        }

        return super.onBreak(world, pos, state, player);
    }

    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, Blocks.AIR.getDefaultState(), blockEntity, tool);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.get(Properties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            world.setBlockState(pos, state.with(WATERLOGGED, true), Block.NOTIFY_ALL);
            world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            return true;
        }
        return false;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos;
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        World worldAccess = ctx.getWorld();
        boolean bl = worldAccess.getFluidState(blockPos = ctx.getBlockPos()).getFluid() == Fluids.WATER;
        return world.getBlockState(pos.down()).canReplace(ctx) ? this.getDefaultState().with(WATERLOGGED, bl).with(FACING, ctx.getHorizontalPlayerFacing()) : null;
    }


    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    public enum DirectionShape implements StringIdentifiable {
        LEFT("left"),
        MIDDLE("middle"),
        RIGHT("right"),
        ALONE("alone");

        private final String name;

        DirectionShape(String name) {
            this.name = name;
        }

        public String asString() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
