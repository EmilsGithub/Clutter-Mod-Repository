package net.emilsg.clutter.block.custom;

import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.ToIntFunction;

public class WallBookshelfBlock extends Block implements Waterloggable{
        protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 8.0);
        protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 12.0, 16.0);
        protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 12.0, 16.0);
        protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 12.0, 16.0);
        public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
        private static final BooleanProperty LIT = Properties.LIT;
        public static final int MAX_MODEL = 6;
        public static IntProperty CURRENT_MODEL = IntProperty.of("current_model", 0, MAX_MODEL);
        public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;


        public WallBookshelfBlock(Settings settings) {
                super(settings.luminance(state -> state.get(LIT) ? 8 : 0));
                this.setDefaultState(this.getDefaultState().with(CURRENT_MODEL, 0).with(LIT, false).with(WATERLOGGED, false));
        }

        @Override
        public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
                if (state.get(WATERLOGGED) || state.get(CURRENT_MODEL) != 4) {
                        return;
                }

                Direction i = state.get(FACING);
                Vec3d[] candlePositions;
                switch (i) {
                        case NORTH:
                                candlePositions = new Vec3d[] {
                                        new Vec3d(pos.getX() + 0.875, pos.getY() + 0.75, pos.getZ() + 0.125),
                                        new Vec3d(pos.getX() + 0.6875, pos.getY() + 0.6875, pos.getZ() + 0.1875),
                                        new Vec3d(pos.getX() + 0.8125, pos.getY() + 0.5625, pos.getZ() + 0.3125)
                                };
                                break;
                        case EAST:
                                candlePositions = new Vec3d[] {
                                        new Vec3d(pos.getX() + 0.875, pos.getY() + 0.75, pos.getZ() + 0.875),
                                        new Vec3d(pos.getX() + 0.8125, pos.getY() + 0.6875, pos.getZ() + 0.6875),
                                        new Vec3d(pos.getX() + 0.6875, pos.getY() + 0.5625, pos.getZ() + 0.8125)
                                };
                                break;
                        case SOUTH:
                                candlePositions = new Vec3d[] {
                                        new Vec3d(pos.getX() + 0.125, pos.getY() + 0.75, pos.getZ() + 0.875),
                                        new Vec3d(pos.getX() + 0.3125, pos.getY() + 0.6875, pos.getZ() + 0.8125),
                                        new Vec3d(pos.getX() + 0.1875, pos.getY() + 0.5625, pos.getZ() + 0.6875)
                                };
                                break;
                        case WEST:
                                candlePositions = new Vec3d[] {
                                        new Vec3d(pos.getX() + 0.125, pos.getY() + 0.75, pos.getZ() + 0.125),
                                        new Vec3d(pos.getX() + 0.1875, pos.getY() + 0.6875, pos.getZ() + 0.3125),
                                        new Vec3d(pos.getX() + 0.3125, pos.getY() + 0.5625, pos.getZ() + 0.1875)
                                };
                                break;
                        default:
                                return;
                }

                for (Vec3d candlePos : candlePositions) {
                        WallBookshelfBlock.spawnCandleParticles(world, candlePos, random);
                }
        }


        private static void spawnCandleParticles(World world, Vec3d vec3d, Random random) {
                float f = random.nextFloat();
                if (f < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
                        if (f < 0.17f) {
                                world.playSound(vec3d.x + 0.5, vec3d.y + 0.5, vec3d.z + 0.5, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                }
                world.addParticle(ParticleTypes.SMALL_FLAME, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
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
        public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
                int i = state.get(CURRENT_MODEL);
                        if (!world.isClient && player.isSneaking() && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isEmpty()) {
                                if (i < MAX_MODEL) {
                                        world.setBlockState(pos, state.with(CURRENT_MODEL, i + 1), Block.NOTIFY_ALL);
                                        updateLit(world, pos, state);
                                } else {
                                        world.setBlockState(pos, state.with(CURRENT_MODEL, 0), Block.NOTIFY_ALL);
                                }
                                return ActionResult.SUCCESS;
                        }
                return ActionResult.PASS;
        }

        public void updateLit(World world, BlockPos pos, BlockState state) {
                int i = state.get(CURRENT_MODEL);
                        if (i == 1 || i == 3) {
                                world.setBlockState(pos, state.with(LIT, true).with(CURRENT_MODEL, i + 1), Block.NOTIFY_ALL);
                        } else {
                                world.setBlockState(pos, state.with(LIT, false).with(CURRENT_MODEL, i + 1), Block.NOTIFY_ALL);
                        }
        }


        @Nullable
        @Override
        public BlockState getPlacementState(ItemPlacementContext ctx) {
                return this.getDefaultState().with(CURRENT_MODEL, 0).with(LIT, false).with(FACING, ctx.getPlayerFacing());
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
                builder.add(FACING, CURRENT_MODEL, LIT, WATERLOGGED);

        }

        public static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
                return state -> state.get(LIT) ? litLevel : 0;
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
