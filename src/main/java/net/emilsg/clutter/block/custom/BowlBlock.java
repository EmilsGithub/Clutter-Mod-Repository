package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.util.ModItemTags;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class BowlBlock extends Block implements Waterloggable {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 3.0, 11.0);
    public static final int MAX_MODEL = 5;
    public static IntProperty CURRENT_MODEL = IntProperty.of("current_model", 0, MAX_MODEL);
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty FILLED = BooleanProperty.of("filled");
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public BowlBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(CURRENT_MODEL, 0).with(WATERLOGGED, false).with(FILLED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        boolean i = state.get(FILLED);
        int j = state.get(CURRENT_MODEL);
        if (!world.isClient && !player.isSneaking() && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isEmpty()) {
            if (i && j == 1) {
                world.setBlockState(pos, state.with(CURRENT_MODEL, 0).with(FILLED, false), Block.NOTIFY_ALL);
                player.playSound(SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1.0F, 1.0F );
                player.getHungerManager().add(6, 0.0f);
            } else if (i && j == 2) {
                world.setBlockState(pos, state.with(CURRENT_MODEL, 0).with(FILLED, false), Block.NOTIFY_ALL);
                player.playSound(SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1.0F, 1.0F );
                player.getHungerManager().add(6, 0.0f);
            } else if (i && j == 3) {
                world.setBlockState(pos, state.with(CURRENT_MODEL, 0).with(FILLED, false), Block.NOTIFY_ALL);
                player.playSound(SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1.0F, 1.0F );
                player.getHungerManager().add(10, 0.0f);
            } else if (i && j == 4) {
                world.setBlockState(pos, state.with(CURRENT_MODEL, 0).with(FILLED, false), Block.NOTIFY_ALL);
                player.playSound(SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1.0F, 1.0F );
                player.getHungerManager().add(4, 0.0f);
            } else if (i && j == 5) {
                world.setBlockState(pos, state.with(CURRENT_MODEL, 0).with(FILLED, false), Block.NOTIFY_ALL);
                player.playSound(SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1.0F, 1.0F );
                giveRandomEffect(player);
                player.getHungerManager().add(6, 0.0f);
            } else if (!i || j == 0) {
                return ActionResult.PASS;
            }
            return ActionResult.SUCCESS;
        } else if (!world.isClient && !player.isSneaking() && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isOf(Items.MUSHROOM_STEW) && !i) {
            world.setBlockState(pos, state.with(CURRENT_MODEL, 1).with(FILLED, true), Block.NOTIFY_ALL);
            player.setStackInHand(hand, ItemUsage.exchangeStack(player.getStackInHand(hand), player, new ItemStack(Items.BOWL)));
            if (!player.getAbilities().creativeMode) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(player.getStackInHand(hand), player, new ItemStack(Items.BOWL)));
            }
            return ActionResult.SUCCESS;
        } else if (!world.isClient && !player.isSneaking() && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isOf(Items.BEETROOT_SOUP) && !i) {
            world.setBlockState(pos, state.with(CURRENT_MODEL, 2).with(FILLED, true), Block.NOTIFY_ALL);
            if (!player.getAbilities().creativeMode) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(player.getStackInHand(hand), player, new ItemStack(Items.BOWL)));
            }
            return ActionResult.SUCCESS;
        } else if (!world.isClient && !player.isSneaking() && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isOf(Items.RABBIT_STEW) && !i) {
            world.setBlockState(pos, state.with(CURRENT_MODEL, 3).with(FILLED, true), Block.NOTIFY_ALL);
            player.setStackInHand(hand, ItemUsage.exchangeStack(player.getStackInHand(hand), player, new ItemStack(Items.BOWL)));
            if (!player.getAbilities().creativeMode) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(player.getStackInHand(hand), player, new ItemStack(Items.BOWL)));
            }
            return ActionResult.SUCCESS;
        } else if (!world.isClient && !player.isSneaking() && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isIn(ModItemTags.FRUITS_AND_BERRIES) && !i) {
            world.setBlockState(pos, state.with(CURRENT_MODEL, 4).with(FILLED, true), Block.NOTIFY_ALL);
            if (!player.getAbilities().creativeMode) {
                player.getStackInHand(hand).decrement(1);
            }
            return ActionResult.SUCCESS;
        } else if (!world.isClient && !player.isSneaking() && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isOf(Items.SUSPICIOUS_STEW) && !i) {
            world.setBlockState(pos, state.with(CURRENT_MODEL, 5).with(FILLED, true), Block.NOTIFY_ALL);
            player.setStackInHand(hand, ItemUsage.exchangeStack(player.getStackInHand(hand), player, new ItemStack(Items.BOWL)));
            if (!player.getAbilities().creativeMode) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(player.getStackInHand(hand), player, new ItemStack(Items.BOWL)));
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public void giveRandomEffect(PlayerEntity player) {
        Random random = new Random();
        int effectIndex = random.nextInt(9);
        StatusEffect[] effects = {
                StatusEffects.SATURATION,
                StatusEffects.NIGHT_VISION,
                StatusEffects.FIRE_RESISTANCE,
                StatusEffects.BLINDNESS,
                StatusEffects.WEAKNESS,
                StatusEffects.REGENERATION,
                StatusEffects.JUMP_BOOST,
                StatusEffects.POISON,
                StatusEffects.WITHER
        };
        StatusEffect effect = effects[effectIndex];
        int duration = effect == StatusEffects.SATURATION ? 2 : 100; // 0.1 seconds or 5 seconds in ticks
        int amplifier = 0;
        StatusEffectInstance effectInstance = new StatusEffectInstance(effect, duration, amplifier);
        player.addStatusEffect(effectInstance);
    }



    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(CURRENT_MODEL, 0).with(FACING, ctx.getPlayerFacing());
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
        builder.add(FACING, CURRENT_MODEL, WATERLOGGED, FILLED);
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

            world.setBlockState(pos, (BlockState)((BlockState)state.with(WATERLOGGED, true)), Block.NOTIFY_ALL);
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
}
