package net.emilsg.clutter.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class TubeTvBlock extends HorizontalFacingBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    protected static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(1, 0, 1, 15, 4, 3),
            Block.createCuboidShape(11, 1, 0.5, 12, 2, 1),
            Block.createCuboidShape(7, 1, 0.5, 9, 2, 1),
            Block.createCuboidShape(4, 1, 0.5, 6, 2, 1),
            Block.createCuboidShape(1, 0, 3, 15, 14, 11),
            Block.createCuboidShape(3, 4, 2, 13, 12, 2),
            Block.createCuboidShape(2, 0, 11, 14, 13, 15),
            Block.createCuboidShape(13, 4, 1, 15, 12, 3),
            Block.createCuboidShape(1, 4, 1, 3, 12, 3),
            Block.createCuboidShape(1, 12, 1, 15, 14, 3),
            Block.createCuboidShape(9, 14, 3, 13, 15, 6)
    );
    protected static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(1, 0, 13, 15, 4, 15),
            Block.createCuboidShape(4, 1, 15, 5, 2, 15.5),
            Block.createCuboidShape(7, 1, 15, 9, 2, 15.5),
            Block.createCuboidShape(10, 1, 15, 12, 2, 15.5),
            Block.createCuboidShape(1, 0, 5, 15, 14, 13),
            Block.createCuboidShape(3, 4, 14, 13, 12, 14),
            Block.createCuboidShape(2, 0, 1, 14, 13, 5),
            Block.createCuboidShape(1, 4, 13, 3, 12, 15),
            Block.createCuboidShape(13, 4, 13, 15, 12, 15),
            Block.createCuboidShape(1, 12, 13, 15, 14, 15),
            Block.createCuboidShape(3, 14, 10, 7, 15, 13)
    );
    protected static final VoxelShape EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(13, 0, 1, 15, 4, 15),
            Block.createCuboidShape(15, 1, 11, 15.5, 2, 12),
            Block.createCuboidShape(15, 1, 7, 15.5, 2, 9),
            Block.createCuboidShape(15, 1, 4, 15.5, 2, 6),
            Block.createCuboidShape(5, 0, 1, 13, 14, 15),
            Block.createCuboidShape(14, 4, 3, 14, 12, 13),
            Block.createCuboidShape(1, 0, 2, 5, 13, 14),
            Block.createCuboidShape(13, 4, 13, 15, 12, 15),
            Block.createCuboidShape(13, 4, 1, 15, 12, 3),
            Block.createCuboidShape(13, 12, 1, 15, 14, 15),
            Block.createCuboidShape(10, 14, 9, 13, 15, 13)
    );
    protected static final VoxelShape WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(1, 0, 1, 3, 4, 15),
            Block.createCuboidShape(0.5, 1, 4, 1, 2, 5),
            Block.createCuboidShape(0.5, 1, 7, 1, 2, 9),
            Block.createCuboidShape(0.5, 1, 10, 1, 2, 12),
            Block.createCuboidShape(3, 0, 1, 11, 14, 15),
            Block.createCuboidShape(2, 4, 3, 2, 12, 13),
            Block.createCuboidShape(11, 0, 2, 15, 13, 14),
            Block.createCuboidShape(1, 4, 1, 3, 12, 3),
            Block.createCuboidShape(1, 4, 13, 3, 12, 15),
            Block.createCuboidShape(1, 12, 1, 3, 14, 15),
            Block.createCuboidShape(3, 14, 3, 6, 15, 7)
    );
    private static final BooleanProperty LIT = Properties.LIT;

    public static final MapCodec<TubeTvBlock> CODEC = createCodec(TubeTvBlock::new);


    public TubeTvBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(LIT, false));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    public static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(LIT) ? litLevel : 0;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        boolean i = state.get(LIT);
        if (!world.isClient && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isEmpty()) {
            if (!i) {
                world.setBlockState(pos, state.with(LIT, true), Block.NOTIFY_ALL);
                world.playSound(null, pos, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 1.0f, 1.25f);
            } else {
                world.setBlockState(pos, state.with(LIT, false), Block.NOTIFY_ALL);
                world.playSound(null, pos, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 1.0f, 1.25f);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
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
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos;
        World worldAccess = ctx.getWorld();
        boolean bl = worldAccess.getFluidState(blockPos = ctx.getBlockPos()).getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, bl).with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.get(Properties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {

            world.setBlockState(pos, (state.with(WATERLOGGED, true)), Block.NOTIFY_ALL);
            world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            return true;
        }
        return false;
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, LIT);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }
}
