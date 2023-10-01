package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.entity.PlateInventoryBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class PlateBlock extends BlockWithEntity implements Waterloggable {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final EnumProperty<PlateBlock.Glass> GLASS_TYPE = EnumProperty.of("glass_type", PlateBlock.Glass.class);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    protected static final VoxelShape PLATE_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 1.5, 13.0);
    protected static final VoxelShape GLASS_SHAPE_N = VoxelShapes.union(
            Block.createCuboidShape(11, 4, 0, 14, 8, 3),
            Block.createCuboidShape(12, 1, 1, 13, 4, 2),
            Block.createCuboidShape(11, 0, 0, 14, 1, 3)
    );
    protected static final VoxelShape GLASS_SHAPE_E = VoxelShapes.union(
            Block.createCuboidShape(13, 4, 11, 16, 8, 14),
            Block.createCuboidShape(14, 1, 12, 15, 4, 13),
            Block.createCuboidShape(13, 0, 11, 16, 1, 14)
    );
    protected static final VoxelShape GLASS_SHAPE_S = VoxelShapes.union(
            Block.createCuboidShape(2, 4, 13, 5, 8, 16),
            Block.createCuboidShape(3, 1, 14, 4, 4, 15),
            Block.createCuboidShape(2, 0, 13, 5, 1, 16)
    );
    protected static final VoxelShape GLASS_SHAPE_W = VoxelShapes.union(
            Block.createCuboidShape(0, 4, 2, 3, 8, 5),
            Block.createCuboidShape(1, 1, 3, 2, 4, 4),
            Block.createCuboidShape(0, 0, 2, 3, 1, 5)
    );

    public PlateBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.NORTH).with(GLASS_TYPE, Glass.NONE));

    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape finalShape = PLATE_SHAPE;

        if(state.get(GLASS_TYPE) == Glass.WINE) {
            switch (state.get(FACING)) {
                default -> finalShape = VoxelShapes.union(PLATE_SHAPE, GLASS_SHAPE_N);
                case EAST -> finalShape = VoxelShapes.union(PLATE_SHAPE, GLASS_SHAPE_E);
                case SOUTH -> finalShape = VoxelShapes.union(PLATE_SHAPE, GLASS_SHAPE_S);
                case WEST -> finalShape = VoxelShapes.union(PLATE_SHAPE, GLASS_SHAPE_W);
            }
        }

        return finalShape;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        ItemStack stackInHand = player.getStackInHand(hand);
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if(PLATE_SHAPE.raycast(Vec3d.ZERO, hit.getPos(), pos) != null) {
            if ((Block.getBlockFromItem(stackInHand.getItem()) instanceof WineGlassBlock) && state.get(GLASS_TYPE).equals(Glass.NONE)) {
                if(!player.getAbilities().creativeMode) {
                    stackInHand.decrement(1);
                }
                world.setBlockState(pos, state.with(GLASS_TYPE, Glass.WINE), 3);
                return ActionResult.SUCCESS;
            } else if (blockEntity instanceof PlateInventoryBlockEntity plateEntity) {
                if (!stackInHand.isEmpty() && stackInHand.isFood()) {
                    for (int i = 0; i < plateEntity.size(); i++) {
                        if (plateEntity.getStack(i).isEmpty()) {
                            plateEntity.setStack(i, stackInHand.copyWithCount(1));
                            if(!player.getAbilities().creativeMode) {
                                stackInHand.decrement(1);
                            }
                            return ActionResult.SUCCESS;
                        }
                    }
                } else if (stackInHand.isEmpty()) {
                    for (int i = plateEntity.size() - 1; i >= 0; i--) {
                        ItemStack stackInPlate = plateEntity.getStack(i);
                        if (!stackInPlate.isEmpty()) {
                            if(stackInHand.isEmpty() || (stackInHand.isOf(stackInPlate.getItem()) && stackInHand.getCount() != stackInHand.getMaxCount())) {
                                player.giveItemStack(new ItemStack(stackInPlate.copy().getItem()));
                            } else {
                                dropStack(world, pos, new ItemStack(stackInPlate.copy().getItem()));
                            }
                            plateEntity.setStack(i, ItemStack.EMPTY);
                            return ActionResult.SUCCESS;
                        }
                    }
                }
            }
        } else {
            if(player.getStackInHand(hand).isEmpty() || ((Block.getBlockFromItem(stackInHand.getItem()) instanceof WineGlassBlock) && stackInHand.getCount() != stackInHand.getMaxCount())) {
                player.giveItemStack(new ItemStack(ModBlocks.WINE_GLASS));
            } else {
                dropStack(world, pos, new ItemStack(ModBlocks.WINE_GLASS));
            }

            world.setBlockState(pos, state.with(GLASS_TYPE, Glass.NONE), 3);
            return ActionResult.SUCCESS;

        }
        return ActionResult.CONSUME;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, GLASS_TYPE);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos;
        World worldAccess = ctx.getWorld();
        boolean bl = worldAccess.getFluidState(blockPos = ctx.getBlockPos()).getFluid() == Fluids.WATER;
        return (BlockState)this.getDefaultState().with(WATERLOGGED, bl).with(FACING, ctx.getHorizontalPlayerFacing());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }


    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
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

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PlateInventoryBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof Inventory) {
            ItemScatterer.spawn(world, pos, (Inventory)((Object)blockEntity));
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    public enum Glass implements StringIdentifiable {
        NONE("none"),
        WINE("wine");

        private final String name;

        Glass(String name) {
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
