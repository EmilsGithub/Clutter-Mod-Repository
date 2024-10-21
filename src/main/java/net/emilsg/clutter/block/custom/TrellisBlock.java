package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.block.custom.cutout.ICutoutRenderable;
import net.emilsg.clutter.util.ModItemTags;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

import java.util.function.ToIntFunction;

public class TrellisBlock extends Block implements Waterloggable, ICutoutRenderable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty UNDER_BLOCK = BooleanProperty.of("under_block");
    public static final EnumProperty<TrellisBlock.Plant> PLANT = EnumProperty.of("plant", TrellisBlock.Plant.class);
    public static final BooleanProperty LIT = Properties.LIT;

    protected static final VoxelShape UNDER_BLOCK_SHAPE = Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 2.0);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 14.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 16.0, 16.0);

    public TrellisBlock(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(PLANT, TrellisBlock.Plant.NONE).with(LIT, false));
    }

    public static ToIntFunction<BlockState> createLightLevelFromLitBlockState() {
        return state -> {
            if (state.get(LIT)) {
                TrellisBlock.Plant plant = state.get(TrellisBlock.PLANT);
                if (plant == TrellisBlock.Plant.GLOW_LICHEN) {
                    return 7;
                } else if (plant == TrellisBlock.Plant.CAVE_VINES) {
                    return 14;
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        };
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        TrellisBlock.Plant i = state.get(PLANT);
        ItemStack stack = player.getStackInHand(hand);

        if (!stack.isIn(ModItemTags.TRELLIS_ITEMS) && !stack.isOf(Items.AIR)) {
            return ActionResult.PASS;
        }

        if (!world.isClient && hand.equals(Hand.MAIN_HAND)) {
            if (i != Plant.NONE) {
                if (i == TrellisBlock.Plant.VINES) {
                    world.setBlockState(pos, state.with(PLANT, TrellisBlock.Plant.NONE).with(LIT, false), Block.NOTIFY_ALL);
                    player.giveItemStack(new ItemStack(Blocks.VINE));
                    world.playSound(null, pos, SoundEvents.BLOCK_VINE_BREAK, SoundCategory.BLOCKS);
                    return ActionResult.SUCCESS;
                } else if (i == TrellisBlock.Plant.ROSE_BUSH) {
                    world.setBlockState(pos, state.with(PLANT, TrellisBlock.Plant.NONE).with(LIT, false), Block.NOTIFY_ALL);
                    player.giveItemStack(new ItemStack(Blocks.ROSE_BUSH));
                    world.playSound(null, pos, SoundEvents.BLOCK_VINE_BREAK, SoundCategory.BLOCKS);
                    return ActionResult.SUCCESS;
                } else if (i == TrellisBlock.Plant.PEONY) {
                    world.setBlockState(pos, state.with(PLANT, TrellisBlock.Plant.NONE).with(LIT, false), Block.NOTIFY_ALL);
                    player.giveItemStack(new ItemStack(Blocks.PEONY));
                    world.playSound(null, pos, SoundEvents.BLOCK_VINE_BREAK, SoundCategory.BLOCKS);
                    return ActionResult.SUCCESS;
                } else if (i == TrellisBlock.Plant.LILAC) {
                    world.setBlockState(pos, state.with(PLANT, TrellisBlock.Plant.NONE).with(LIT, false), Block.NOTIFY_ALL);
                    player.giveItemStack(new ItemStack(Blocks.LILAC));
                    world.playSound(null, pos, SoundEvents.BLOCK_VINE_BREAK, SoundCategory.BLOCKS);
                    return ActionResult.SUCCESS;
                } else if (i == TrellisBlock.Plant.CAVE_VINES) {
                    world.setBlockState(pos, state.with(PLANT, TrellisBlock.Plant.NONE).with(LIT, false), Block.NOTIFY_ALL);
                    player.giveItemStack(new ItemStack(Items.GLOW_BERRIES));
                    world.playSound(null, pos, SoundEvents.BLOCK_VINE_BREAK, SoundCategory.BLOCKS);
                    return ActionResult.SUCCESS;
                } else if (i == TrellisBlock.Plant.WEEPING_VINES) {
                    world.setBlockState(pos, state.with(PLANT, TrellisBlock.Plant.NONE).with(LIT, false), Block.NOTIFY_ALL);
                    player.giveItemStack(new ItemStack(Blocks.WEEPING_VINES));
                    world.playSound(null, pos, SoundEvents.BLOCK_VINE_BREAK, SoundCategory.BLOCKS);
                    return ActionResult.SUCCESS;
                } else if (i == TrellisBlock.Plant.TWISTING_VINES) {
                    world.setBlockState(pos, state.with(PLANT, TrellisBlock.Plant.NONE).with(LIT, false), Block.NOTIFY_ALL);
                    player.giveItemStack(new ItemStack(Blocks.TWISTING_VINES));
                    world.playSound(null, pos, SoundEvents.BLOCK_VINE_BREAK, SoundCategory.BLOCKS);
                    return ActionResult.SUCCESS;
                } else if (i == TrellisBlock.Plant.GLOW_LICHEN) {
                    world.setBlockState(pos, state.with(PLANT, TrellisBlock.Plant.NONE).with(LIT, false), Block.NOTIFY_ALL);
                    player.giveItemStack(new ItemStack(Blocks.GLOW_LICHEN));
                    world.playSound(null, pos, SoundEvents.BLOCK_VINE_BREAK, SoundCategory.BLOCKS);
                    return ActionResult.SUCCESS;
                }
                return ActionResult.CONSUME_PARTIAL;
            } else if (stack.isIn(ModItemTags.TRELLIS_ITEMS) && state.get(PLANT) == TrellisBlock.Plant.NONE) {
                Item item = stack.getItem();
                if (item == Items.VINE) {
                    world.setBlockState(pos, state.with(PLANT, TrellisBlock.Plant.VINES).with(LIT, false), Block.NOTIFY_ALL);
                } else if (item == Items.ROSE_BUSH) {
                    world.setBlockState(pos, state.with(PLANT, TrellisBlock.Plant.ROSE_BUSH).with(LIT, false), Block.NOTIFY_ALL);
                } else if (item == Items.PEONY) {
                    world.setBlockState(pos, state.with(PLANT, TrellisBlock.Plant.PEONY).with(LIT, false), Block.NOTIFY_ALL);
                } else if (item == Items.LILAC) {
                    world.setBlockState(pos, state.with(PLANT, TrellisBlock.Plant.LILAC).with(LIT, false), Block.NOTIFY_ALL);
                } else if (item == Items.GLOW_BERRIES) {
                    world.setBlockState(pos, state.with(PLANT, TrellisBlock.Plant.CAVE_VINES).with(LIT, true), Block.NOTIFY_ALL);
                } else if (item == Items.WEEPING_VINES) {
                    world.setBlockState(pos, state.with(PLANT, TrellisBlock.Plant.WEEPING_VINES).with(LIT, false), Block.NOTIFY_ALL);
                } else if (item == Items.TWISTING_VINES) {
                    world.setBlockState(pos, state.with(PLANT, TrellisBlock.Plant.TWISTING_VINES).with(LIT, false), Block.NOTIFY_ALL);
                } else if (item == Items.GLOW_LICHEN) {
                    world.setBlockState(pos, state.with(PLANT, TrellisBlock.Plant.GLOW_LICHEN).with(LIT, true), Block.NOTIFY_ALL);
                }
                if (!player.getAbilities().creativeMode) {
                    stack.decrement(1);
                }
                world.playSound(null, pos, SoundEvents.BLOCK_VINE_PLACE, SoundCategory.BLOCKS);
                world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.CONSUME;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public void updatePlantState(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);

        if (state.get(PLANT) != TrellisBlock.Plant.NONE) {
            return; // Already has a plant
        }

        // Check if any neighboring block has a plant
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(neighborPos);

            if (neighborState.getBlock() instanceof TrellisBlock && neighborState.get(PLANT) != TrellisBlock.Plant.NONE) {
                // If there is a neighboring block with a plant, set the block's plant state randomly
                if (world.random.nextFloat() < 0.1f) {
                    world.setBlockState(pos, state.with(PLANT, neighborState.get(PLANT)).with(LIT, neighborState.get(LIT)), Block.NOTIFY_ALL);
                }
                break; // Stop checking neighbors
            }
        }
    }


    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(TrellisBlock.PLANT) == TrellisBlock.Plant.NONE;
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(TrellisBlock.PLANT) == TrellisBlock.Plant.NONE) {
            updatePlantState(world, pos);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        boolean bl = world.getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER;
        if (ctx.getSide() == Direction.DOWN) {
            return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing()).with(WATERLOGGED, bl).with(UNDER_BLOCK, true);
        }
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing()).with(WATERLOGGED, bl).with(UNDER_BLOCK, false);
    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(UNDER_BLOCK)) {
            return UNDER_BLOCK_SHAPE;
        } else {
            Direction i = state.get(FACING);
            return switch (i) {
                case NORTH -> NORTH_SHAPE;
                case SOUTH -> SOUTH_SHAPE;
                case EAST -> EAST_SHAPE;
                case WEST -> WEST_SHAPE;
                default -> VoxelShapes.empty();
            };
        }
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
        builder.add(PLANT, WATERLOGGED, FACING, LIT, UNDER_BLOCK);
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

            world.setBlockState(pos, state.with(WATERLOGGED, true), Block.NOTIFY_ALL);
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

    public enum Plant implements StringIdentifiable {
        NONE("none"),
        VINES("vines"),
        ROSE_BUSH("rose_bush"),
        PEONY("peony"),
        LILAC("lilac"),
        CAVE_VINES("cave_vines"),
        WEEPING_VINES("weeping_vines"),
        TWISTING_VINES("twisting_vines"),
        GLOW_LICHEN("glow_lichen");

        private final String name;

        Plant(String name) {
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