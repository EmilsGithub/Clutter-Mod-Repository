package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.util.ModBlockTags;
import net.emilsg.clutter.util.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class TableBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final EnumProperty<LegPosition> LEG_POSITIONS = EnumProperty.of("leg_positions", LegPosition.class);
    public static final BooleanProperty LEGS = ModProperties.LEGS;

    public TableBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(WATERLOGGED, false).with(LEGS,true).with(LEG_POSITIONS, LegPosition.ALL));
    }

    protected static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 14.0, 2.0, 14.0, 16.0),
            Block.createCuboidShape(14.0, 0.0, 14.0, 16.0, 14.0, 16.0)
    );
    protected static final VoxelShape EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 14.0, 2.0, 14.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 14.0, 2.0)

    );
    protected static final VoxelShape WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0),
            Block.createCuboidShape(14.0, 0.0, 14.0, 16.0, 14.0, 16.0),
            Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 14.0, 2.0)
    );
    protected static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 14.0, 2.0),
            Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 14.0, 2.0)
    );
    protected static final VoxelShape NORTH_WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 14.0, 2.0, 14.0, 16.0)
    );
    protected static final VoxelShape NORTH_EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0),
            Block.createCuboidShape(14.0, 0.0, 14.0, 16.0, 14.0, 16.0)
    );
    protected static final VoxelShape SOUTH_WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0),
            Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 14.0, 2.0)
    );
    protected static final VoxelShape SOUTH_EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 14.0, 2.0)
    );
    protected static final VoxelShape NONE_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0)
    );
    protected static final VoxelShape ALL_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 14.0, 2.0),
            Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 14.0, 2.0),
            Block.createCuboidShape(0.0, 0.0, 14.0, 2.0, 14.0, 16.0),
            Block.createCuboidShape(14.0, 0.0, 14.0, 16.0, 14.0, 16.0)
    );

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        LegPosition i = state.get(LEG_POSITIONS);
        return switch (i) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            case SOUTH_WEST -> SOUTH_WEST_SHAPE;
            case SOUTH_EAST -> SOUTH_EAST_SHAPE;
            case NORTH_EAST -> NORTH_WEST_SHAPE;
            case NORTH_WEST -> NORTH_EAST_SHAPE;
            case NONE -> NONE_SHAPE;
            case ALL -> ALL_SHAPE;
        };
    }

    public enum LegPosition implements StringIdentifiable {
        NONE("none"),
        NORTH("north"),
        SOUTH("south"),
        EAST("east"),
        WEST("west"),
        NORTH_WEST("north_west"),
        NORTH_EAST("north_east"),
        SOUTH_WEST("south_west"),
        SOUTH_EAST("south_east"),
        ALL("all");

        private final String name;

        LegPosition(String name) {
            this.name = name;
        }

        public String asString() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static LegPosition fromNeighborBlocks(boolean west, boolean north, boolean east, boolean south) {
            if (west && north) {
                return LegPosition.NORTH_WEST;
            } else if (west && south) {
                return LegPosition.SOUTH_WEST;
            } else if (east && north) {
                return LegPosition.NORTH_EAST;
            } else if (east && south) {
                return LegPosition.SOUTH_EAST;
            }  else if (west) {
                return LegPosition.WEST;
            } else if (east) {
                return LegPosition.EAST;
            } else if (north) {
                return LegPosition.NORTH;
            } else if (south) {
                return LegPosition.SOUTH;
            } else {
                return LegPosition.NONE;
            }
        }
    }


    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEG_POSITIONS, LEGS, WATERLOGGED);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        boolean eastTable = world.getBlockState(pos.east()).getBlock() instanceof TableBlock;
        boolean westTable = world.getBlockState(pos.west()).getBlock() instanceof TableBlock;
        boolean northTable = world.getBlockState(pos.north()).getBlock() instanceof TableBlock;
        boolean southTable = world.getBlockState(pos.south()).getBlock() instanceof TableBlock;
        boolean northWestTable = world.getBlockState(pos.north().west()).getBlock() instanceof TableBlock;
        boolean southWestTable = world.getBlockState(pos.south().west()).getBlock() instanceof TableBlock;
        boolean northEastTable = world.getBlockState(pos.north().east()).getBlock() instanceof TableBlock;
        boolean southEastTable = world.getBlockState(pos.south().east()).getBlock() instanceof TableBlock;

        if (!eastTable && !westTable && !northTable && !southTable) {
            return state.with(LEG_POSITIONS, LegPosition.ALL).with(LEGS, true);
        }
        if (eastTable && westTable && northTable && southTable) {
            return state.with(LEG_POSITIONS, LegPosition.NONE).with(LEGS, false);
        }
        if (eastTable && westTable) {
            return state.with(LEG_POSITIONS, LegPosition.NONE).with(LEGS, false);
        }
        if (northTable && southTable) {
            return state.with(LEG_POSITIONS, LegPosition.NONE).with(LEGS, false);
        }
        if (westTable && southTable && !northWestTable) {
            return state.with(LEG_POSITIONS, LegPosition.SOUTH_WEST).with(LEGS, true);
        }
        if (westTable && northTable && !southWestTable) {
            return state.with(LEG_POSITIONS, LegPosition.NORTH_WEST).with(LEGS, true);
        }
        if (eastTable && northTable && !southEastTable) {
            return state.with(LEG_POSITIONS, LegPosition.NORTH_EAST).with(LEGS, true);
        }
        if (eastTable && southTable && !northEastTable) {
            return state.with(LEG_POSITIONS, LegPosition.SOUTH_EAST).with(LEGS, true);
        }

        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        LegPosition legPositions = LegPosition.fromNeighborBlocks(westTable, northTable, eastTable, southTable);
        boolean legs = !legPositions.equals(LegPosition.NONE);

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos).with(LEG_POSITIONS, legPositions).with(LEGS, legs);
    }



    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        updateTableLegs(world, pos, state);
    }

    private void updateTableLegs(World world, BlockPos pos, BlockState state) {
        boolean eastTable = world.getBlockState(pos.east()).getBlock() instanceof TableBlock;
        boolean westTable = world.getBlockState(pos.west()).getBlock() instanceof TableBlock;
        boolean northTable = world.getBlockState(pos.north()).getBlock() instanceof TableBlock;
        boolean southTable = world.getBlockState(pos.south()).getBlock() instanceof TableBlock;

        if (eastTable && westTable && northTable && southTable) {
            world.setBlockState(pos, state.with(LEG_POSITIONS, LegPosition.NONE).with(LEGS, false));
        } else if (!eastTable && !westTable && !northTable && !southTable) {
            world.setBlockState(pos, state.with(LEG_POSITIONS, LegPosition.ALL).with(LEGS, true));
        } else if (eastTable && westTable) {
            world.setBlockState(pos, state.with(LEG_POSITIONS, LegPosition.NONE).with(LEGS, false));
        } else if (northTable && southTable) {
            world.setBlockState(pos, state.with(LEG_POSITIONS, LegPosition.NONE).with(LEGS, false));
        } else {
            LegPosition legPositions = LegPosition.fromNeighborBlocks(westTable, northTable, eastTable, southTable);
            boolean legs = !legPositions.equals(LegPosition.NONE);
            world.setBlockState(pos, state.with(LEG_POSITIONS, legPositions).with(LEGS, legs));
        }
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
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() instanceof AxeItem && state.isIn(ModBlockTags.STRIPPABLE_TABLES)) {
            BlockState strippedState = getStrippedState(state);
            world.setBlockState(pos, strippedState);
            world.playSound(null, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
                itemStack.damage(1, player, (p) -> p.sendToolBreakStatus(hand));
            }
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
    }

    private BlockState getStrippedState(BlockState state) {
        if (state.getBlock() == ModBlocks.OAK_TABLE) {
            return ModBlocks.STRIPPED_OAK_TABLE.getDefaultState().with(LEGS, state.get(LEGS)).with(LEG_POSITIONS, state.get(LEG_POSITIONS));
        } else if (state.getBlock() == ModBlocks.SPRUCE_TABLE) {
            return ModBlocks.STRIPPED_SPRUCE_TABLE.getDefaultState().with(LEGS, state.get(LEGS)).with(LEG_POSITIONS, state.get(LEG_POSITIONS));
        } else if (state.getBlock() == ModBlocks.BIRCH_TABLE) {
            return ModBlocks.STRIPPED_BIRCH_TABLE.getDefaultState().with(LEGS, state.get(LEGS)).with(LEG_POSITIONS, state.get(LEG_POSITIONS));
        } else if (state.getBlock() == ModBlocks.JUNGLE_TABLE) {
            return ModBlocks.STRIPPED_JUNGLE_TABLE.getDefaultState().with(LEGS, state.get(LEGS)).with(LEG_POSITIONS, state.get(LEG_POSITIONS));
        } else if (state.getBlock() == ModBlocks.ACACIA_TABLE) {
            return ModBlocks.STRIPPED_ACACIA_TABLE.getDefaultState().with(LEGS, state.get(LEGS)).with(LEG_POSITIONS, state.get(LEG_POSITIONS));
        } else if (state.getBlock() == ModBlocks.DARK_OAK_TABLE) {
            return ModBlocks.STRIPPED_DARK_OAK_TABLE.getDefaultState().with(LEGS, state.get(LEGS)).with(LEG_POSITIONS, state.get(LEG_POSITIONS));
        } else if (state.getBlock() == ModBlocks.MANGROVE_TABLE) {
            return ModBlocks.STRIPPED_MANGROVE_TABLE.getDefaultState().with(LEGS, state.get(LEGS)).with(LEG_POSITIONS, state.get(LEG_POSITIONS));
        } else if (state.getBlock() == ModBlocks.CRIMSON_TABLE) {
            return ModBlocks.STRIPPED_CRIMSON_TABLE.getDefaultState().with(LEGS, state.get(LEGS)).with(LEG_POSITIONS, state.get(LEG_POSITIONS));
        } else if (state.getBlock() == ModBlocks.WARPED_TABLE) {
            return ModBlocks.STRIPPED_WARPED_TABLE.getDefaultState().with(LEGS, state.get(LEGS)).with(LEG_POSITIONS, state.get(LEG_POSITIONS));
        } else if (state.getBlock() == ModBlocks.CHERRY_TABLE) {
            return ModBlocks.STRIPPED_CHERRY_TABLE.getDefaultState().with(LEGS, state.get(LEGS)).with(LEG_POSITIONS, state.get(LEG_POSITIONS));
        } else if (state.getBlock() == ModBlocks.REDWOOD_TABLE) {
            return ModBlocks.STRIPPED_REDWOOD_TABLE.getDefaultState().with(LEGS, state.get(LEGS)).with(LEG_POSITIONS, state.get(LEG_POSITIONS));
        } else {
            return state;
        }
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
