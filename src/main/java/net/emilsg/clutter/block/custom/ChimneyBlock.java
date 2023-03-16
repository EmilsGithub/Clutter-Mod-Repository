package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.block.entity.ChimneyBlockEntity;
import net.emilsg.clutter.block.entity.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChimneyBlock extends BlockWithEntity implements FluidFillable, Waterloggable{
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static BooleanProperty OPEN = BooleanProperty.of("open");
    protected static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0),
            Block.createCuboidShape(1.0, 13.0, 1.0, 15.0, 16.0, 15.0)
    );

    public ChimneyBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(OPEN, true).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
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
        boolean i = state.get(OPEN);
        if (!world.isClient && hand.equals(Hand.MAIN_HAND) && !player.isSneaking() && player.getStackInHand(hand).isEmpty()) {
            if (!i) {
                world.setBlockState(pos, state.with(OPEN, true), Block.NOTIFY_ALL);
            } else {
                world.setBlockState(pos, state.with(OPEN, false), Block.NOTIFY_ALL);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockState blockState = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        if (!blockState.isAir()) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return blockState;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(OPEN);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if(state.get(OPEN)) {
            ChimneyBlock.spawnSmokeParticles(world, pos, true, state);
        }
    }

    public static void spawnSmokeParticles(World world, BlockPos pos, boolean lotsOfSmoke, BlockState state) {
        Random random = world.getRandom();
        DefaultParticleType defaultParticleType = ParticleTypes.CAMPFIRE_COSY_SMOKE;
            if (!state.get(WATERLOGGED)) {
                world.addImportantParticle(defaultParticleType, true, (double) pos.getX() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getY() + random.nextDouble() + random.nextDouble() + 0.5, (double) pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1), 0.0, 0.07, 0.0);
            if (lotsOfSmoke) {
                world.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getY() + 0.4, (double) pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double) (random.nextBoolean() ? 1 : -1), 0.0, 0.005, 0.0);
            }
        }
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.get(Properties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            world.setBlockState(pos, (BlockState)((BlockState)state.with(WATERLOGGED, true)), Block.NOTIFY_ALL);
            world.createAndScheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            return true;
        }
        return false;
    }

    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, OPEN);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ChimneyBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient() && type == ModBlockEntities.CHIMNEY ? ChimneyBlockEntity::clientTick : null;
    }

    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("block.clutter.toggle_smoke.tooltip").formatted(Formatting.BLUE));
        super.appendTooltip(stack, world, tooltip, context);
    }

}
