package net.emilsg.clutter.block.custom.plants;

import net.emilsg.clutter.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class GiantLilyPadBlock extends LilyPadBlock implements Fertilizable {
    public static final EnumProperty<GiantLilyPadBlock.LilyPadDirections> LILY_PAD_DIRECTIONS = EnumProperty.of("lily_pad_directions", GiantLilyPadBlock.LilyPadDirections.class);
    public static final BooleanProperty FLOWERING = ModProperties.FLOWERING;

    protected static final VoxelShape NORTH_WEST_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 16.0, 1.5, 16.0);
    protected static final VoxelShape NORTH_EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 1.0, 15.0, 1.5, 16.0);
    protected static final VoxelShape SOUTH_WEST_SHAPE = Block.createCuboidShape(1.0, 0.0, 0.0, 16.0, 1.5, 15.0);
    protected static final VoxelShape SOUTH_EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 15.0, 1.5, 15.0);



    public GiantLilyPadBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(LILY_PAD_DIRECTIONS, LilyPadDirections.SOUTH_WEST).with(FLOWERING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LILY_PAD_DIRECTIONS, FLOWERING);
        super.appendProperties(builder);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(LILY_PAD_DIRECTIONS)) {
            case NORTH_EAST -> NORTH_EAST_SHAPE;
            case NORTH_WEST -> NORTH_WEST_SHAPE;
            case SOUTH_WEST -> SOUTH_WEST_SHAPE;
            case SOUTH_EAST -> SOUTH_EAST_SHAPE;
        };
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getFluidState(pos.down()).isOf(Fluids.WATER);
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockPos blockPosNorth = pos.north();
        BlockPos blockPosEast = pos.east();
        BlockPos blockPosNorthEast = pos.north().east();
        world.setBlockState(blockPosNorth, this.getDefaultState().with(LILY_PAD_DIRECTIONS, LilyPadDirections.NORTH_WEST), 3);
        world.setBlockState(blockPosEast, this.getDefaultState().with(LILY_PAD_DIRECTIONS, LilyPadDirections.SOUTH_EAST), 3);
        world.setBlockState(blockPosNorthEast, this.getDefaultState().with(LILY_PAD_DIRECTIONS, LilyPadDirections.NORTH_EAST), 3);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
       return isValidPlacement(world, blockPos.north()) && isValidPlacement(world, blockPos.east()) && isValidPlacement(world, blockPos.north().east()) ? super.getPlacementState(ctx) : null;
    }

    public static boolean isValidPlacement(World world, BlockPos replacePos) {
        return world.getBlockState(replacePos).isAir() && world.getFluidState(replacePos.down()).isOf(Fluids.WATER);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(state.get(LILY_PAD_DIRECTIONS) == LilyPadDirections.SOUTH_WEST) {
            getAndBreakBlock(world, pos.north());
            getAndBreakBlock(world, pos.north().east());
            getAndBreakBlock(world, pos.east());
        } else if(state.get(LILY_PAD_DIRECTIONS) == LilyPadDirections.SOUTH_EAST) {
            getAndBreakBlock(world, pos.north());
            getAndBreakBlock(world, pos.north().west());
            getAndBreakBlock(world, pos.west());
        } else if(state.get(LILY_PAD_DIRECTIONS) == LilyPadDirections.NORTH_WEST) {
            getAndBreakBlock(world, pos.south());
            getAndBreakBlock(world, pos.south().east());
            getAndBreakBlock(world, pos.east());
        } else if(state.get(LILY_PAD_DIRECTIONS) == LilyPadDirections.NORTH_EAST) {
            getAndBreakBlock(world, pos.south());
            getAndBreakBlock(world, pos.south().west());
            getAndBreakBlock(world, pos.west());
        }
        super.onBreak(world, pos, state, player);
    }

    private void getAndBreakBlock(WorldAccess world, BlockPos pos) {
        if(world.getBlockState(pos).getBlock() instanceof GiantLilyPadBlock) world.breakBlock(pos, true);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return !state.get(FLOWERING);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return !state.get(FLOWERING);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if(!state.get(FLOWERING)) {
            Map<LilyPadDirections, BlockPos[]> directionPosMap = Map.of(
                    LilyPadDirections.SOUTH_WEST, new BlockPos[] {pos, pos.north(), pos.north().east(), pos.east()},
                    LilyPadDirections.SOUTH_EAST, new BlockPos[] {pos, pos.north(), pos.north().west(), pos.west()},
                    LilyPadDirections.NORTH_WEST, new BlockPos[] {pos, pos.south(), pos.south().east(), pos.east()},
                    LilyPadDirections.NORTH_EAST, new BlockPos[] {pos, pos.south(), pos.south().west(), pos.west()}
            );

            BlockPos[] positions = directionPosMap.get(state.get(LILY_PAD_DIRECTIONS));

            if (positions != null) {
                for (BlockPos position : positions) {
                    setFlowering(state, world, position);
                }
            }
        }
    }

    private void setFlowering(BlockState state, ServerWorld world, BlockPos pos) {
        world.setBlockState(pos, state.with(FLOWERING, true).with(LILY_PAD_DIRECTIONS, world.getBlockState(pos).get(LILY_PAD_DIRECTIONS)));
    }

    public enum LilyPadDirections implements StringIdentifiable {
        NORTH_EAST("north_east"),
        NORTH_WEST("north_west"),
        SOUTH_EAST("south_east"),
        SOUTH_WEST("south_west");

        private final String name;

        LilyPadDirections(String name) {
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

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world instanceof ServerWorld && entity instanceof BoatEntity) {
            if (state.get(LILY_PAD_DIRECTIONS) == LilyPadDirections.SOUTH_WEST) {
                getAndBreakBlock(world, pos.north());
                getAndBreakBlock(world, pos.north().east());
                getAndBreakBlock(world, pos.east());
            } else if (state.get(LILY_PAD_DIRECTIONS) == LilyPadDirections.SOUTH_EAST) {
                getAndBreakBlock(world, pos.north());
                getAndBreakBlock(world, pos.north().west());
                getAndBreakBlock(world, pos.west());
            } else if (state.get(LILY_PAD_DIRECTIONS) == LilyPadDirections.NORTH_WEST) {
                getAndBreakBlock(world, pos.south());
                getAndBreakBlock(world, pos.south().east());
                getAndBreakBlock(world, pos.east());
            } else if (state.get(LILY_PAD_DIRECTIONS) == LilyPadDirections.NORTH_EAST) {
                getAndBreakBlock(world, pos.south());
                getAndBreakBlock(world, pos.south().west());
                getAndBreakBlock(world, pos.west());
            }
        }
        super.onEntityCollision(state, world, pos, entity);
    }
}
