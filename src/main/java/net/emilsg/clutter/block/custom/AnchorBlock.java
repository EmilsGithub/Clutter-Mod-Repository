package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.util.ModDamageSources;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class AnchorBlock extends HorizontalFacingBlock implements Waterloggable, LandingBlock {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public final VoxelShape NS_SHAPE= VoxelShapes.union(
            Block.createCuboidShape(5, 10.5, 7, 11, 13.5, 9),
            Block.createCuboidShape(10.5, 9.5, 7, 12.5, 14.5, 9),
            Block.createCuboidShape(3.5, 9.5, 7, 5.5, 14.5, 9),
            Block.createCuboidShape(4, 0, 6.5, 12, 2.5, 9.5),
            Block.createCuboidShape(2, 1, 6.5, 5, 3.5, 9.5),
            Block.createCuboidShape(11, 1, 6.5, 14, 3.5, 9.5),
            Block.createCuboidShape(0, 3, 6.5, 3, 5.5, 9.5),
            Block.createCuboidShape(13, 3, 6.5, 16, 5.5, 9.5),
            Block.createCuboidShape(6.5, 2.5, 6.5, 9.5, 15, 9.5),
            Block.createCuboidShape(5.5, 14.5, 5.5, 10.5, 16, 10.5)
    );

    public final VoxelShape EW_SHAPE= VoxelShapes.union(
            Block.createCuboidShape(7, 10.5, 5, 9, 13.5, 11),
            Block.createCuboidShape(7, 9.5, 3.5, 9, 14.5, 5.5),
            Block.createCuboidShape(7, 9.5, 10.5, 9, 14.5, 12.5),
            Block.createCuboidShape(6.5, 0, 4, 9.5, 2.5, 12),
            Block.createCuboidShape(6.5, 1, 11, 9.5, 3.5, 14),
            Block.createCuboidShape(6.5, 1, 2, 9.5, 3.5, 5),
            Block.createCuboidShape(6.5, 3, 13, 9.5, 5.5, 16),
            Block.createCuboidShape(6.5, 3, 0, 9.5, 5.5, 3),
            Block.createCuboidShape(6.5, 2.5, 6.5, 9.5, 15, 9.5),
            Block.createCuboidShape(5.5, 14.5, 5.5, 10.5, 16, 10.5)
    );

    public AnchorBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case EAST, WEST -> EW_SHAPE;
            default -> NS_SHAPE;
        };
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean isFluidWater = fluidState.getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, isFluidWater).with(FACING, ctx.getHorizontalPlayerFacing());
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        world.scheduleBlockTick(pos, this, this.getFallDelay());
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, this, this.getFallDelay());
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= world.getBottomY() && world.getBlockState(pos.up()).isReplaceable()) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state);
            this.configureFallingBlockEntity(fallingBlockEntity);
        }
    }

    protected int getFallDelay() {
        return 1;
    }

    public static boolean canFallThrough(BlockState state) {
        return state.isAir() || state.isIn(BlockTags.FIRE) || state.isLiquid() || state.isReplaceable();
    }

    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
        entity.setHurtEntities(1.5F, 30);
    }

    public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            world.syncWorldEvent(1031, pos, 0);
        }

    }

    public void onDestroyedOnLanding(World world, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            world.syncWorldEvent(1029, pos, 0);
        }

    }

    public DamageSource getDamageSource(Entity attacker) {
        return ModDamageSources.getDamageSource(new DamageSources(attacker.getWorld().getRegistryManager()), ModDamageSources.ANCHOR);
    }
}
