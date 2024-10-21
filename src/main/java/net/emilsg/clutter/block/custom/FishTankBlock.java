package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.block.custom.cutout.ICutoutRenderable;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class FishTankBlock extends Block implements Waterloggable, ICutoutRenderable {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final EnumProperty<TankType> TYPE = EnumProperty.of("type", TankType.class);
    public static final EnumProperty<InteriorType> INTERIOR = EnumProperty.of("interior", InteriorType.class);
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 1, 16);

    private final VoxelShape SINGLE_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 0, 0, 1, 16, 16),
            Block.createCuboidShape(15, 0, 1, 16, 16, 15),
            Block.createCuboidShape(1, 0, 0, 16, 16, 1),
            Block.createCuboidShape(1, 0, 15, 16, 16, 16),
            BOTTOM_SHAPE
    );

    private final VoxelShape NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 0, 0, 16, 16, 1),
            Block.createCuboidShape(15, 0, 1, 16, 16, 16),
            Block.createCuboidShape(0, 0, 1, 1, 16, 16)
    );

    private final VoxelShape EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(15, 0, 0, 16, 16, 16),
            Block.createCuboidShape(0, 0, 15, 15, 16, 16),
            Block.createCuboidShape(0, 0, 0, 15, 16, 1)
    );

    private final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 0, 15, 16, 16, 16),
            Block.createCuboidShape(0, 0, 0, 1, 16, 15),
            Block.createCuboidShape(15, 0, 0, 16, 16, 15)
    );

    private final VoxelShape WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 0, 0, 1, 16, 16),
            Block.createCuboidShape(1, 0, 0, 16, 16, 1),
            Block.createCuboidShape(1, 0, 15, 16, 16, 16)
    );

    public FishTankBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(TYPE, TankType.SINGLE).with(WATERLOGGED, false).with(INTERIOR, InteriorType.NONE));

    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE, FACING, WATERLOGGED, INTERIOR);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        ItemStack stackInHand = player.getStackInHand(hand);
        InteriorType currentType = state.get(INTERIOR);
        InteriorType newType = getTypeFromItemStack(stackInHand);
        if (newType == null || currentType == newType) return ActionResult.PASS;

        if (world instanceof ServerWorld serverWorld) {
            if (!newType.isOf(InteriorType.NONE) && !player.getAbilities().creativeMode) stackInHand.decrement(1);
            serverWorld.setBlockState(pos, state.with(INTERIOR, newType), Block.NOTIFY_ALL);
        }

        return ActionResult.success(world.isClient);
    }

    private @Nullable InteriorType getTypeFromItemStack(ItemStack stackInHand) {
        if (stackInHand.isOf(Items.SAND)) return InteriorType.BEACH;
        else if (Registries.ITEM.getId(stackInHand.getItem()).getPath().contains("coral")) return InteriorType.CORAL;
        else if (stackInHand.isOf(Items.GRAVEL)) return InteriorType.GRAVELLY;
        else if (stackInHand.isOf(Items.AIR)) return InteriorType.NONE;
        else return null;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return true;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);
        TankType type = state.get(TYPE);

        if (type.isOf(TankType.SINGLE)) return SINGLE_SHAPE;

        return VoxelShapes.union(
                BOTTOM_SHAPE,
                switch (facing) {
                    default -> type.isOf(TankType.RIGHT) ? EAST_SHAPE : WEST_SHAPE;
                    case EAST -> type.isOf(TankType.RIGHT) ? SOUTH_SHAPE : NORTH_SHAPE;
                    case SOUTH -> type.isOf(TankType.RIGHT) ? WEST_SHAPE : EAST_SHAPE;
                    case WEST -> type.isOf(TankType.RIGHT) ? NORTH_SHAPE : SOUTH_SHAPE;
                }
        );
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        TankType tankType = TankType.SINGLE;
        Direction direction = ctx.getHorizontalPlayerFacing().getOpposite();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean bl = ctx.shouldCancelInteraction();
        Direction direction2 = ctx.getSide();
        if (direction2.getAxis().isHorizontal() && bl) {
            Direction direction3 = this.getNeighborTankDirection(ctx, direction2.getOpposite());
            if (direction3 != null && direction3.getAxis() != direction2.getAxis()) {
                direction = direction3;
                tankType = direction.rotateYCounterclockwise() == direction2.getOpposite() ? TankType.RIGHT : TankType.LEFT;
            }
        }

        if (tankType == TankType.SINGLE && !bl) {
            if (direction == this.getNeighborTankDirection(ctx, direction.rotateYClockwise())) {
                tankType = TankType.LEFT;
            } else if (direction == this.getNeighborTankDirection(ctx, direction.rotateYCounterclockwise())) {
                tankType = TankType.RIGHT;
            }
        }

        return this.getDefaultState().with(FACING, direction).with(TYPE, tankType).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Nullable
    private Direction getNeighborTankDirection(ItemPlacementContext ctx, Direction dir) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(dir));
        return blockState.isOf(this) && blockState.get(TYPE) == TankType.SINGLE ? blockState.get(FACING) : null;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if (neighborState.isOf(this) && direction.getAxis().isHorizontal()) {
            TankType tankType = neighborState.get(TYPE);
            if (state.get(TYPE) == TankType.SINGLE && tankType != TankType.SINGLE && state.get(FACING) == neighborState.get(FACING) && getFacing(neighborState) == direction.getOpposite()) {
                return state.with(TYPE, tankType.getOpposite());
            }
        } else if (getFacing(state) == direction) {
            return state.with(TYPE, TankType.SINGLE);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public static DoubleBlockProperties.Type getDoubleBlockType(BlockState state) {
        TankType tankType = state.get(TYPE);
        if (tankType == TankType.SINGLE) {
            return DoubleBlockProperties.Type.SINGLE;
        } else {
            return tankType == TankType.RIGHT ? DoubleBlockProperties.Type.FIRST : DoubleBlockProperties.Type.SECOND;
        }
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public static Direction getFacing(BlockState state) {
        Direction direction = state.get(FACING);
        return state.get(TYPE) == TankType.LEFT ? direction.rotateYClockwise() : direction.rotateYCounterclockwise();
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    public enum TankType implements StringIdentifiable {
        SINGLE("single"),
        LEFT("left"),
        RIGHT("right");

        private final String name;

        private TankType(String name) {
            this.name = name;
        }

        public String asString() {
            return this.name;
        }

        public boolean isOf(TankType type) {
            return type == this;
        }

        public TankType getOpposite() {
            TankType tankType;
            switch (this) {
                case SINGLE -> tankType = SINGLE;
                case LEFT -> tankType = RIGHT;
                case RIGHT -> tankType = LEFT;
                default -> throw new IncompatibleClassChangeError();
            }

            return tankType;
        }
    }

    public enum InteriorType implements StringIdentifiable {
        CORAL("coral"),
        BEACH("beach"),
        GRAVELLY("gravelly"),
        NONE("none");


        private final String name;

        private InteriorType(String name) {
            this.name = name;
        }

        public String asString() {
            return this.name;
        }

        public boolean isOf(InteriorType type) {
            return type == this;
        }
    }
}
