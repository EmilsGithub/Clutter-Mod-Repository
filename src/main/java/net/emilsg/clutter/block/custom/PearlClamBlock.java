package net.emilsg.clutter.block.custom;

import com.mojang.serialization.MapCodec;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class PearlClamBlock extends HorizontalFacingBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty HAS_PEARL = ModProperties.HAS_PEARL;

    private final VoxelShape SHAPE = Block.createCuboidShape(4.5, 0, 4.5, 11.5, 2.5, 11.5);

    public static final MapCodec<PearlClamBlock> CODEC = createCodec(PearlClamBlock::new);

    public PearlClamBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(HAS_PEARL, false));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, HAS_PEARL);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean bl = fluidState.getFluid() == Fluids.WATER;
        return super.getPlacementState(ctx).with(WATERLOGGED, bl).with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!(Boolean) state.get(WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            BlockState blockState = state.with(WATERLOGGED, true);
            world.setBlockState(pos, blockState, 3);
            world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        if (world.isClient) {
            return ActionResult.PASS;
        }

        if (state.get(HAS_PEARL) && hand == Hand.MAIN_HAND) {
            dropStack(world, pos, new ItemStack(ModItems.PEARL));
            world.setBlockState(pos, state.with(HAS_PEARL, false), Block.NOTIFY_ALL);
            world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
        }

        return ActionResult.PASS;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(WATERLOGGED);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient && !state.get(HAS_PEARL) && random.nextInt(128) == 0) {
            world.setBlockState(pos, state.with(HAS_PEARL, true), Block.NOTIFY_ALL);
        }
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }
}
