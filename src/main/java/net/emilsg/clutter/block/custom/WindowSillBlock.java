package net.emilsg.clutter.block.custom;

import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WindowSillBlock extends Block implements Waterloggable{
        protected static final VoxelShape NORTH = Block.createCuboidShape(0.0, 12.0, 0.0, 16.0, 16.0, 8.0);
        protected static final VoxelShape SOUTH = Block.createCuboidShape(0.0, 12.0, 8.0, 16.0, 16.0, 16.0);
        protected static final VoxelShape EAST = Block.createCuboidShape(8.0, 12.0, 0.0, 16.0, 16.0, 16.0);
        protected static final VoxelShape WEST = Block.createCuboidShape(0.0, 12.0, 0.0, 8.0, 16.0, 16.0);

        public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
        public static final int MAX_MODEL = 19;
        public static IntProperty CURRENT_MODEL = IntProperty.of("current_model", 0, 19);
        public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

        public WindowSillBlock(Settings settings) {
                super(settings);
                this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false));
        }

        @Override
        public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
                Direction i = state.get(FACING);
                return switch (i) {
                        case NORTH -> NORTH;
                        case SOUTH -> SOUTH;
                        case EAST -> EAST;
                        case WEST -> WEST;
                        default -> VoxelShapes.empty(); // return an empty shape if no matching direction is found
                };
        }

        @Override
        public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
                int i = state.get(CURRENT_MODEL);
                if (!world.isClient && player.isSneaking() && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isEmpty()) {
                        if (i < MAX_MODEL) {
                                world.setBlockState(pos, state.with(CURRENT_MODEL, i + 1), Block.NOTIFY_ALL);
                        } else {
                                world.setBlockState(pos, state.with(CURRENT_MODEL, 0), Block.NOTIFY_ALL);
                        }
                        return ActionResult.SUCCESS;
                }
                return ActionResult.PASS;
        }


        @Override
        @Nullable
        public BlockState getPlacementState(ItemPlacementContext ctx) {
                BlockPos blockPos;
                World worldAccess = ctx.getWorld();
                boolean bl = worldAccess.getFluidState(blockPos = ctx.getBlockPos()).getFluid() == Fluids.WATER;
                return (BlockState)this.getDefaultState().with(WATERLOGGED, bl).with(FACING, ctx.getPlayerFacing());
        }

        @Override
        public BlockState rotate(BlockState state, BlockRotation rotation) {
                return state.with(FACING, rotation.rotate(state.get(FACING)));
        }

        @Override
        public BlockState mirror(BlockState state, BlockMirror mirror) {
                return state.rotate(mirror.getRotation(state.get(FACING)));
        }

        @Override
        protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
                builder.add(FACING, CURRENT_MODEL, WATERLOGGED);
        }

        public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext context) {
                tooltip.add(Text.translatable("block.clutter.cycle_blockstate_tooltip.tooltip").formatted(Formatting.BLUE));
                super.appendTooltip(stack, world, tooltip, context);
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
