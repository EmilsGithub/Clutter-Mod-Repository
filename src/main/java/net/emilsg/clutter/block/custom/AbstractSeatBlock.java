package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.block.entity.SeatEntity;
import net.emilsg.clutter.entity.ModEntities;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.emilsg.clutter.block.entity.SeatEntity.IS_OCCUPIED;

public abstract class AbstractSeatBlock extends HorizontalFacingBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty HORIZONTAL_FACING = Properties.HORIZONTAL_FACING;

    public AbstractSeatBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, WATERLOGGED);
        super.appendProperties(builder);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World worldAccess = ctx.getWorld();
        boolean bl = worldAccess.getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, bl).with(HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
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

            world.setBlockState(pos, (state.with(WATERLOGGED, true)), Block.NOTIFY_ALL);
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

    protected abstract float getYOffset();

    protected abstract boolean isStrippable();

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.isSneaking() || !player.getStackInHand(player.getActiveHand()).isEmpty()) return ActionResult.PASS;
        if (isStrippable() && (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof AxeItem || player.getStackInHand(Hand.OFF_HAND).getItem() instanceof AxeItem))
            return ActionResult.PASS;

        Vec3d comparePos = new Vec3d(pos.getX(), pos.getY(), pos.getZ());

        if (world.isClient || !world.canPlayerModifyAt(player, pos) || IS_OCCUPIED.containsKey(comparePos)) {
            return ActionResult.PASS;
        }

        return spawnAndSitOnSeat(world, player, pos, getYOffset(), comparePos);
    }

    private ActionResult spawnAndSitOnSeat(World world, PlayerEntity player, BlockPos blockPos, double yOffset, Vec3d comparePos) {
        SeatEntity seatEntity = ModEntities.SEAT.create(world);
        if(seatEntity != null) {
            Vec3d pos = new Vec3d(
                    blockPos.getX() + 0.5f,
                    blockPos.getY() + yOffset,
                    blockPos.getZ() + 0.5f
            );
            IS_OCCUPIED.put(comparePos, player.getBlockPos());
            seatEntity.updatePosition(pos.getX(), pos.getY(), pos.getZ());
            world.spawnEntity(seatEntity);
            player.startRiding(seatEntity);

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
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
