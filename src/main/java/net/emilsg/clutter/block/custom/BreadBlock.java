package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.util.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class BreadBlock extends HorizontalFacingBlock {
    public static final IntProperty SLICES = ModProperties.SLICES;
    private final int food;
    private final float saturation;

    public BreadBlock(Settings settings, int food, float saturation) {
        super(settings);
        this.getDefaultState().with(SLICES, 7).with(FACING, Direction.NORTH);
        this.food = food;
        this.saturation = saturation;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int remainingSlices = state.get(SLICES);

        if (world.isClient && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isEmpty() && player.getHungerManager().isNotFull()) {
            return ActionResult.SUCCESS;
        }

        if (remainingSlices >= 1 && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isEmpty() && player.getHungerManager().isNotFull()) {
            if (remainingSlices - 1 < 1) {
                player.getHungerManager().add(food, saturation);
                world.removeBlock(pos, false);
            }
            world.setBlockState(pos, state.with(SLICES, remainingSlices - 1), Block.NOTIFY_ALL);
            player.getHungerManager().add(food, saturation);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape SHAPE = VoxelShapes.empty();

        int remainingSlices = state.get(SLICES);
        final VoxelShape NORTH_SHAPE = Block.createCuboidShape(1.0, 0.0, 5.0, 2 * remainingSlices + 1.0, 7.0, 11.0);
        final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(15 - (2 * remainingSlices), 0.0, 5.0, 15.0, 7.0, 11.0);
        final VoxelShape EAST_SHAPE = Block.createCuboidShape(5.0, 0.0, 1.0, 11.0, 7.0, 2 * remainingSlices + 1.0);
        final VoxelShape WEST_SHAPE = Block.createCuboidShape(5.0, 0.0, 15 - (2 * remainingSlices), 11.0, 7.0, 15.0);

        final VoxelShape FOUR_NORTH = VoxelShapes.union(
                Block.createCuboidShape(1, 4, 4, 7, 6, 12),
                Block.createCuboidShape(1, 0, 5, 7, 4, 11),
                Block.createCuboidShape(1, 6, 5, 7, 7, 11),
                Block.createCuboidShape(14, 0, 5, 15, 2, 11),
                Block.createCuboidShape(12, 0, 4, 14, 2, 12),
                Block.createCuboidShape(8, 0, 5, 12, 2, 11));

        final VoxelShape FOUR_EAST = VoxelShapes.union(
                Block.createCuboidShape(4, 4, 1, 12, 6, 7),
                Block.createCuboidShape(5, 0, 1, 11, 4, 7),
                Block.createCuboidShape(5, 6, 1, 11, 7, 7),
                Block.createCuboidShape(5, 0, 14, 11, 2, 15),
                Block.createCuboidShape(4, 0, 12, 12, 2, 14),
                Block.createCuboidShape(5, 0, 8, 11, 2, 12));

        final VoxelShape FOUR_SOUTH = VoxelShapes.union(
                Block.createCuboidShape(9, 4, 4, 15, 6, 12),
                Block.createCuboidShape(9, 0, 5, 15, 4, 11),
                Block.createCuboidShape(9, 6, 5, 15, 7, 11),
                Block.createCuboidShape(1, 0, 5, 2, 2, 11),
                Block.createCuboidShape(2, 0, 4, 4, 2, 12),
                Block.createCuboidShape(4, 0, 5, 8, 2, 11));

        final VoxelShape FOUR_WEST = VoxelShapes.union(
                Block.createCuboidShape(4, 4, 9, 12, 6, 15),
                Block.createCuboidShape(5, 0, 9, 11, 4, 15),
                Block.createCuboidShape(5, 6, 9, 11, 7, 15),
                Block.createCuboidShape(5, 0, 1, 11, 2, 2),
                Block.createCuboidShape(4, 0, 2, 12, 2, 4),
                Block.createCuboidShape(5, 0, 4, 11, 2, 8));

        Direction i = state.get(FACING);

        if (state.get(SLICES) == 4) {
            switch (i) {
                case NORTH -> SHAPE = FOUR_NORTH;
                case SOUTH -> SHAPE = FOUR_SOUTH;
                case EAST -> SHAPE = FOUR_EAST;
                case WEST -> SHAPE = FOUR_WEST;
            }
        } else {
            switch (i) {
                case NORTH -> SHAPE = NORTH_SHAPE;
                case SOUTH -> SHAPE = SOUTH_SHAPE;
                case EAST -> SHAPE = EAST_SHAPE;
                case WEST -> SHAPE = WEST_SHAPE;
            }
        }
        return SHAPE;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing()).with(SLICES, 7);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSolid();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, SLICES);
    }
}
