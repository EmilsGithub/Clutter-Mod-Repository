package net.emilsg.clutter.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class CandelabraBlock extends WaterloggableLitBlock {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    private static final BooleanProperty LIT = Properties.LIT;

    protected static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 1.0, 10.0),
            Block.createCuboidShape(7.0, 1.0, 7.0, 9.0, 16.0, 9.0),
            Block.createCuboidShape(0.0, 9.0, 6.0, 4.0, 10.0, 10.0),
            Block.createCuboidShape(12.0, 9.0, 6.0, 16.0, 10.0, 10.0),
            Block.createCuboidShape(6.0, 10.0, 6.0, 10.0, 11.0, 10.0),
            Block.createCuboidShape(1.0, 10.0, 7.0, 3.0, 13.0, 9.0),
            Block.createCuboidShape(13.0, 10.0, 7.0, 15.0, 14.0, 9.0),
            Block.createCuboidShape(1.0, 8.0, 7.5, 4.0, 9.0, 8.5),
            Block.createCuboidShape(3.0, 6.0, 7.5, 6.0, 8.0, 8.5),
            Block.createCuboidShape(10.0, 6.0, 7.5, 13.0, 8.0, 8.5),
            Block.createCuboidShape(6.0, 5.0, 7.5, 10.0, 8.0, 8.5),
            Block.createCuboidShape(12.0, 8.0, 7.5, 15.0, 9.0, 8.5)
    );
    private static final Vec3d[] CANDLE_POSITIONS_NORTH = {
            new Vec3d(0.125, 0.90625, 0.5),
            new Vec3d(0.5, 1.09375, 0.5),
            new Vec3d(0.875, 0.96875, 0.5)
    };
    protected static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 1.0, 10.0),
            Block.createCuboidShape(7.0, 1.0, 7.0, 9.0, 16.0, 9.0),
            Block.createCuboidShape(0.0, 9.0, 6.0, 4.0, 10.0, 10.0),
            Block.createCuboidShape(12.0, 9.0, 6.0, 16.0, 10.0, 10.0),
            Block.createCuboidShape(6.0, 10.0, 6.0, 10.0, 11.0, 10.0),
            Block.createCuboidShape(1.0, 10.0, 7.0, 3.0, 14.0, 9.0),
            Block.createCuboidShape(13.0, 10.0, 7.0, 15.0, 13.0, 9.0),
            Block.createCuboidShape(1.0, 8.0, 7.5, 4.0, 9.0, 8.5),
            Block.createCuboidShape(3.0, 6.0, 7.5, 6.0, 8.0, 8.5),
            Block.createCuboidShape(10.0, 6.0, 7.5, 13.0, 8.0, 8.5),
            Block.createCuboidShape(6.0, 5.0, 7.5, 10.0, 8.0, 8.5),
            Block.createCuboidShape(12.0, 8.0, 7.5, 15.0, 9.0, 8.5)
    );
    private static final Vec3d[] CANDLE_POSITIONS_SOUTH= {
            new Vec3d(0.125, 0.96875, 0.5),
            new Vec3d(0.5, 1.09375, 0.5),
            new Vec3d(0.875, 0.90625, 0.5)
    };
    protected static final VoxelShape EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6.0, 9.0, 0.0, 10.0, 10.0, 4.0),
            Block.createCuboidShape(6.0, 9.0, 12.0, 10.0, 10.0, 16.0),
            Block.createCuboidShape(6.0, 10.0, 6.0, 10.0, 11.0, 10.0),
            Block.createCuboidShape(7.5, 8.0, 1.0, 8.5, 9.0, 4.0),
            Block.createCuboidShape(7.5, 6.0, 3.0, 8.5, 8.0, 6.0),
            Block.createCuboidShape(7.5, 6.0, 10.0, 8.5, 8.0, 13.0),
            Block.createCuboidShape(7.5, 5.0, 6.0, 8.5, 8.0, 10.0),
            Block.createCuboidShape(7.5, 8.0, 12.0, 8.5, 9.0, 15.0),
            Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 9.0),
            Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 1.0, 10.0),
            Block.createCuboidShape(7.0, 10.0, 1.0, 9.0, 13.0, 3.0),
            Block.createCuboidShape(7.0, 10.0, 13.0, 9.0, 14.0, 15.0)
    );
    private static final Vec3d[] CANDLE_POSITIONS_EAST = {
            new Vec3d(0.5, 0.90625, 0.125),
            new Vec3d(0.5, 1.09375, 0.5),
            new Vec3d(0.5, 0.96875, 0.875)
    };
    protected static final VoxelShape WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6.0, 9.0, 0.0, 10.0, 10.0, 4.0),
            Block.createCuboidShape(6.0, 9.0, 12.0, 10.0, 10.0, 16.0),
            Block.createCuboidShape(6.0, 10.0, 6.0, 10.0, 11.0, 10.0),
            Block.createCuboidShape(7.5, 8.0, 1.0, 8.5, 9.0, 4.0),
            Block.createCuboidShape(7.5, 6.0, 3.0, 8.5, 8.0, 6.0),
            Block.createCuboidShape(7.5, 6.0, 10.0, 8.5, 8.0, 13.0),
            Block.createCuboidShape(7.5, 5.0, 6.0, 8.5, 8.0, 10.0),
            Block.createCuboidShape(7.5, 8.0, 12.0, 8.5, 9.0, 15.0),
            Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 9.0),
            Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 1.0, 10.0),
            Block.createCuboidShape(7.0, 10.0, 1.0, 9.0, 14.0, 3.0),
            Block.createCuboidShape(7.0, 10.0, 13.0, 9.0, 13.0, 15.0)
    );
    private static final Vec3d[] CANDLE_POSITIONS_WEST = {
            new Vec3d(0.5, 0.96875, 0.125),
            new Vec3d(0.5, 1.09375, 0.5),
            new Vec3d(0.5, 0.90625, 0.875)
    };

    public CandelabraBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(LIT, false));
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
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, LIT);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(WATERLOGGED) || !state.get(LIT)) {
            return;
        }

        Vec3d[] candlePositions;
        Direction facing = state.get(FACING);

        switch (facing) {
            case NORTH:
                candlePositions = CANDLE_POSITIONS_NORTH;
                break;
            case EAST:
                candlePositions = CANDLE_POSITIONS_EAST;
                break;
            case SOUTH:
                candlePositions = CANDLE_POSITIONS_SOUTH;
                break;
            case WEST:
                candlePositions = CANDLE_POSITIONS_WEST;
                break;
            default:
                candlePositions = new Vec3d[0]; // default to empty array if facing is unknown
        }

        for (Vec3d candlePos : candlePositions) {
            Vec3d offset = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
            CandelabraBlock.spawnCandleParticles(world, offset.add(candlePos), random);
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
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = Direction.DOWN;
        return Block.sideCoversSmallSquare(world, pos.offset(direction), direction.getOpposite());
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (world.getBlockState(pos.down()).isAir()) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}
