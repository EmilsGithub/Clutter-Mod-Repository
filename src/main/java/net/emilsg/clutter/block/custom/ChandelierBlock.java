package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.util.ModBlockTags;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
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

public class ChandelierBlock extends WaterloggableLitBlock {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty LIT = Properties.LIT;
    public static final BooleanProperty ON_CHAIN = BooleanProperty.of("on_chain");

    private static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(1.5, 1.0, 1.0, 14.5, 8.0, 15.0);
    private static final VoxelShape CHAIN_SHAPE = Block.createCuboidShape(6.5, 1.0, 6.5, 9.5, 16.0, 9.5);
    private static final VoxelShape CEILING_MOUNT_SHAPE = Block.createCuboidShape(5.5, 15.0, 5.5, 10.5, 16.0, 10.5);

    private static final Vec3d[] CANDLE_POSITIONS = {
            new Vec3d(0.1875, 0.59375, 0.5),
            new Vec3d(0.5, 0.59375, 0.8125),
            new Vec3d(0.5, 0.59375, 0.1875),
            new Vec3d(0.8125, 0.59375, 0.5)
    };


    public ChandelierBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(ON_CHAIN, false).with(LIT, false));
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
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        updateChainState(world, pos);
    }

    private void updateChainState(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (world.getBlockState(pos.up()).isIn(ModBlockTags.CHAINS)) {
            state = state.with(ON_CHAIN, true);
        } else {
            state = state.with(ON_CHAIN, false);
        }
        world.setBlockState(pos, state, Block.NOTIFY_ALL);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(ON_CHAIN)) {
            return VoxelShapes.union(BOTTOM_SHAPE, CHAIN_SHAPE);
        } else {
            return VoxelShapes.union(BOTTOM_SHAPE, CHAIN_SHAPE, CEILING_MOUNT_SHAPE);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(WATERLOGGED) || !state.get(LIT)) {
            return;
        }
        for (Vec3d candlePos : CANDLE_POSITIONS) {
            Vec3d offset = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
            ChandelierBlock.spawnCandleParticles(world, offset.add(candlePos), random);
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
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT, ON_CHAIN, WATERLOGGED);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = Direction.UP;
        return Block.sideCoversSmallSquare(world, pos.offset(direction), direction.getOpposite());
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (world.getBlockState(pos.up()).isAir()) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}
