package net.emilsg.clutter.block.custom;

import com.mojang.serialization.MapCodec;
import net.emilsg.clutter.block.entity.ChimneyBlockEntity;
import net.emilsg.clutter.block.ModBlockEntities;
import net.emilsg.clutter.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
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
    public static BooleanProperty OPEN = ModProperties.OPEN;
    protected static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0),
            Block.createCuboidShape(1.0, 13.0, 1.0, 15.0, 16.0, 15.0)
    );
    public static final MapCodec<ChimneyBlock> CODEC = createCodec(ChimneyBlock::new);


    public ChimneyBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(OPEN, true).with(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
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
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        boolean i = state.get(OPEN);
        Hand hand = player.getActiveHand();
        if (!world.isClient && hand.equals(Hand.MAIN_HAND) && !player.isSneaking() && player.getStackInHand(hand).isEmpty()) {
            if (!i) {
                world.setBlockState(pos, state.with(OPEN, true), Block.NOTIFY_ALL);
            } else {
                world.setBlockState(pos, state.with(OPEN, false), Block.NOTIFY_ALL);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockState blockState = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        if (!blockState.isAir()) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
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
            ChimneyBlock.spawnSmokeParticles(world, pos, state);
        }
    }

    public static void spawnSmokeParticles(World world, BlockPos pos, BlockState state) {
        Random random = world.getRandom();
        if (!state.get(WATERLOGGED)) {
            world.addImportantParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, true, (double) pos.getX() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getY() + random.nextDouble() + random.nextDouble() + 0.65, (double) pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1), random.nextDouble() * 0.02 - 0.01, 0.07, random.nextDouble() * 0.02 - 0.01);
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
    public boolean canFillWithFluid(@Nullable PlayerEntity player, BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
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

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("block.clutter.toggle_smoke.tooltip").formatted(Formatting.BLUE));
        super.appendTooltip(stack, context, tooltip, options);
    }
}