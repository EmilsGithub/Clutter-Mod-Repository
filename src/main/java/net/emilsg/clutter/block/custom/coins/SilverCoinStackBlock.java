package net.emilsg.clutter.block.custom.coins;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.sound.ModSounds;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class SilverCoinStackBlock extends Block implements Waterloggable {
    public static final IntProperty COIN_LAYERS = IntProperty.of("coin_layers", 1, 8);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public SilverCoinStackBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(COIN_LAYERS, 1).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0,0,0,16,2 * state.get(COIN_LAYERS),16);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos;
        World worldAccess = ctx.getWorld();
        boolean bl = worldAccess.getFluidState(blockPos = ctx.getBlockPos()).getFluid() == Fluids.WATER;
        return (BlockState)this.getDefaultState().with(WATERLOGGED, bl);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos posDown = pos.down();
        BlockState downState = world.getBlockState(posDown);
        return downState.isFullCube(world, posDown);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient && player.getStackInHand(hand).isOf(ModBlocks.SILVER_COIN_STACK.asItem()) && hand.equals(Hand.MAIN_HAND) && state.get(COIN_LAYERS) < 8 && !player.isSneaking()) {
            return ActionResult.SUCCESS;
        }
        if (!world.isClient && player.getStackInHand(hand).isOf(ModBlocks.SILVER_COIN_STACK.asItem()) && hand.equals(Hand.MAIN_HAND) && state.get(COIN_LAYERS) < 8 && !player.isSneaking()) {
            world.setBlockState(pos, state.with(COIN_LAYERS, state.get(COIN_LAYERS) + 1), Block.NOTIFY_ALL);
            world.playSound(null, pos, ModSounds.COIN_PILE_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (!player.getAbilities().creativeMode) {
                player.getStackInHand(hand).decrement(1);
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COIN_LAYERS, WATERLOGGED);
    }

    @Override
    @Deprecated
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (world.getBlockState(pos.down()).getBlock() == Blocks.AIR) {
            return Blocks.AIR.getDefaultState();
        }
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
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
