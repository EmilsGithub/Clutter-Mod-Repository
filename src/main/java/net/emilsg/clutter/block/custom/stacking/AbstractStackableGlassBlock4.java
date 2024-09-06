package net.emilsg.clutter.block.custom.stacking;

import net.emilsg.clutter.util.ModProperties;
import net.emilsg.clutter.util.ModUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractStackableGlassBlock4 extends HorizontalFacingBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final IntProperty COUNT = ModProperties.COUNT_4;

    public AbstractStackableGlassBlock4(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.NORTH).with(COUNT, 1));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COUNT, WATERLOGGED, FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(COUNT)) {
            default -> this.getSingleShape();
            case 2 -> state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH ? this.getTwoNSShape() : this.getTwoEWShape();
            case 3 -> state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH ? this.getThreeNSShape() : this.getThreeEWShape();
            case 4 -> this.getFourShape();
        };
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        player.addExhaustion(0.005F);

        int count = state.get(COUNT);

        if (count > 1) {
            world.setBlockState(pos, state.with(COUNT, count - 1), Block.NOTIFY_ALL);
            dropStack(world, pos, new ItemStack(getDroppedItem()));
        } else {
            dropStacks(state, world, pos, blockEntity, player, tool);
        }
    }

    public abstract Item getDroppedItem();

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        BlockPos pos = hit.getBlockPos();

        int count = state.get(COUNT);

        if (world instanceof ServerWorld serverWorld && count > 1) {
            dropStack(world, pos, new ItemStack(this.asItem()));

            if (projectile instanceof ArrowEntity arrowEntity) arrowEntity.discard();
        }

        world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0f, 1.25f);

        if (count > 1) {
            world.setBlockState(pos, state.with(COUNT, count - 1), Block.NOTIFY_ALL);
        } else {
            world.breakBlock(pos, true, null);
        }

    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.shouldCancelInteraction() && context.getStack().getItem() == this.asItem() && state.get(COUNT) < 4 || super.canReplace(state, context);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return blockState.cycle(COUNT);
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
            boolean bl = fluidState.getFluid() == Fluids.WATER;
            return super.getPlacementState(ctx).with(WATERLOGGED, bl).with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!(Boolean) state.get(WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            BlockState blockState = state.with(WATERLOGGED, true);
            world.setBlockState(pos, blockState, 3);
            world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            return true;
        } else {
            return false;
        }
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    public abstract float getModelXLength();

    public abstract float getModelYHeight();

    public abstract float getModelZDepth();


    public VoxelShape getSingleShape() {
        return ModUtil.createBasicShape(this.getModelXLength() + 1f, this.getModelYHeight() + 0.5f, this.getModelZDepth() + 1f);
    }

    public VoxelShape getTwoNSShape() {
        return ModUtil.createBasicShape((this.getModelXLength() * 2) + 3f, this.getModelYHeight() + 0.5f, this.getModelZDepth() + 1f);
    }

    public VoxelShape getTwoEWShape() {
        return ModUtil.createBasicShape(this.getModelXLength() + 1f, this.getModelYHeight() + 0.5f, (this.getModelZDepth() * 2) + 3f);
    }

    public VoxelShape getThreeNSShape() {
        return ModUtil.createBasicShape((this.getModelXLength() * 3) + 3f, this.getModelYHeight() + 0.5f, this.getModelZDepth() + 1f);
    }

    public VoxelShape getThreeEWShape() {
        return ModUtil.createBasicShape(this.getModelXLength() + 1f, this.getModelYHeight() + 0.5f, (this.getModelZDepth() * 3) + 3f);
    }

    public VoxelShape getFourShape() {
        return ModUtil.createBasicShape((this.getModelXLength() * 2) + 3f, this.getModelYHeight() + 0.5f, (this.getModelZDepth() * 2) + 3f);
    }

}
