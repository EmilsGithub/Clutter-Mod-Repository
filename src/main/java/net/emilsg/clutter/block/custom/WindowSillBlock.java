package net.emilsg.clutter.block.custom;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class WindowSillBlock extends Block {
        protected static final VoxelShape NORTH = Block.createCuboidShape(0.0, 12.0, 0.0, 16.0, 16.0, 8.0);
        protected static final VoxelShape SOUTH = Block.createCuboidShape(0.0, 12.0, 8.0, 16.0, 16.0, 16.0);
        protected static final VoxelShape EAST = Block.createCuboidShape(8.0, 12.0, 0.0, 16.0, 16.0, 16.0);
        protected static final VoxelShape WEST = Block.createCuboidShape(0.0, 12.0, 0.0, 8.0, 16.0, 16.0);

        public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
        public static final int MAX_MODEL = 19;
        public static IntProperty CURRENT_MODEL = IntProperty.of("current_model", 0, 19);

        public WindowSillBlock(Settings settings) {
                super(settings);
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
                if (player.isSneaking() && hand.equals(Hand.MAIN_HAND) && !world.isClient()) {
                        if (i < MAX_MODEL) {
                                world.setBlockState(pos, state.with(CURRENT_MODEL, i + 1), Block.NOTIFY_ALL);
                                return ActionResult.success(world.isClient);
                        } else if (i >= MAX_MODEL) {
                                world.setBlockState(pos, state.with(CURRENT_MODEL, 0), Block.NOTIFY_ALL);
                                return ActionResult.success(world.isClient);
                        }
                }
                return ActionResult.PASS;
        }

        @Nullable
        @Override
        public BlockState getPlacementState(ItemPlacementContext ctx) {
                return this.getDefaultState().with(FACING, ctx.getPlayerFacing());
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
                builder.add(FACING, CURRENT_MODEL);
        }
}
