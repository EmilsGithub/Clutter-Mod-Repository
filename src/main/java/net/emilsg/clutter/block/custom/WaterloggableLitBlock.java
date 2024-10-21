package net.emilsg.clutter.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class WaterloggableLitBlock extends Block implements Waterloggable {
    public static final BooleanProperty LIT = Properties.LIT;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public WaterloggableLitBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(LIT, false));
    }

    public static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(LIT) ? litLevel : 0;
    }

    public static void extinguish(@Nullable PlayerEntity player, BlockState state, WorldAccess world, BlockPos pos) {
        WaterloggableLitBlock.setLit(world, state, pos, false, player);
        world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    public static void setLit(WorldAccess world, BlockState state, BlockPos pos, boolean lit, @Nullable PlayerEntity player) {
        world.setBlockState(pos, state.with(LIT, lit), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient && projectile.isOnFire() && this.isNotLit(state)) {
            setLit(world, state, hit.getBlockPos(), true, null);
        }
    }

    protected boolean isNotLit(BlockState state) {
        return !(Boolean) state.get(LIT);
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
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, LIT);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!(Boolean) state.get(WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            BlockState blockState = state.with(WATERLOGGED, true);
            if (state.get(LIT)) {
                extinguish(null, blockState, world, pos);
            } else {
                world.setBlockState(pos, blockState, 3);
            }

            world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        ItemStack stackInHand = player.getStackInHand(hand);

        if (player.getAbilities().allowModifyWorld && !state.get(WATERLOGGED)) {
            if (stackInHand.isOf(Items.FLINT_AND_STEEL) && !state.get(LIT)) {
                world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4F + 0.8F);
                setLit(world, state, pos, true, player);
                if (!player.getAbilities().creativeMode)
                    stackInHand.damage(1, player, LivingEntity.getSlotForHand(hand));
                return ActionResult.success(world.isClient);
            } else if (stackInHand.isOf(Items.FIRE_CHARGE) && !state.get(LIT)) {
                world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 1.0F);
                setLit(world, state, pos, true, player);
                if (!player.getAbilities().creativeMode) player.getStackInHand(hand).decrement(1);
                return ActionResult.success(world.isClient);
            } else if (stackInHand.isEmpty() && state.get(LIT)) {
                extinguish(player, state, world, pos);
                return ActionResult.success(world.isClient);
            }
            return super.onUse(state, world, pos, player, hit);
        }
        return super.onUse(state, world, pos, player, hit);
    }

}
