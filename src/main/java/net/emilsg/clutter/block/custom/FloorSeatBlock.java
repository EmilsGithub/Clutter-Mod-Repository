package net.emilsg.clutter.block.custom;

import com.mojang.serialization.MapCodec;
import net.emilsg.clutter.block.entity.SeatEntity;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.emilsg.clutter.block.entity.SeatEntity.IS_OCCUPIED;

public class FloorSeatBlock extends AbstractSeatBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    protected static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 4.0, 15.0)
    );

    public static final MapCodec<FloorSeatBlock> CODEC = createCodec(FloorSeatBlock::new);

    public FloorSeatBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos;
        World worldAccess = ctx.getWorld();
        boolean bl = worldAccess.getFluidState(blockPos = ctx.getBlockPos()).getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, bl);
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

    @Override
    protected float getYOffset() {
        return 0;
    }

    @Override
    protected boolean isStrippable() {
        return false;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {

        int xPos = pos.getX();
        int yPos = pos.getY();
        int zPos = pos.getZ();

        List<SeatEntity> entities = world.getEntitiesByClass(SeatEntity.class, new Box(xPos, yPos, zPos, xPos + 1, yPos + 1, zPos + 1), Entity::hasPassengers);
        for (SeatEntity entity : entities) {
            entity.remove(Entity.RemovalReason.DISCARDED);
        }

        this.spawnBreakParticles(world, player, pos, state);
        world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);

        IS_OCCUPIED.remove(new Vec3d(xPos, yPos, zPos));
        return state;
    }
}
