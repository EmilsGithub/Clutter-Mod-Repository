package net.emilsg.clutter.block.custom;


import net.emilsg.clutter.util.ModItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class PlateBlock extends Block implements Waterloggable {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty HAS_VEGGIES = BooleanProperty.of("has_veggies");
    public static final BooleanProperty HAS_MEAT = BooleanProperty.of("has_meat");
    public static final BooleanProperty HAS_CARBS = BooleanProperty.of("has_carbs");

    public static final IntProperty POISON_VALUE = IntProperty.of("poison_value", 0, 10);
    public static final IntProperty HUNGER_VALUE = IntProperty.of("hunger_value", 0, 10);
    public static final IntProperty NAUSEA_VALUE = IntProperty.of("nausea_value", 0, 10);
    public static final IntProperty FOOD_VALUE = IntProperty.of("food_value", 0, 32);
    public static final IntProperty FILLED_STATE = IntProperty.of("filled_state", 0, 3);

    public PlateBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(WATERLOGGED, false)
                .with(HAS_VEGGIES, false)
                .with(HAS_MEAT, false)
                .with(HAS_CARBS, false)
                .with(FILLED_STATE, 0)
                .with(FOOD_VALUE, 0)
                .with(POISON_VALUE, 0)
                .with(HUNGER_VALUE, 0)
                .with(NAUSEA_VALUE, 0));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public void consumeFood(PlayerEntity player, BlockState state, BlockPos pos, World world, int foodValue){
        int poisonAmount = state.get(POISON_VALUE);
        int nauseaAmount = state.get(NAUSEA_VALUE);
        int hungerAmount = state.get(HUNGER_VALUE);
        if (poisonAmount != 0) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 300, poisonAmount - 1));
        }
        if (nauseaAmount != 0) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300, nauseaAmount - 1));
        }
        if (hungerAmount != 0) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 300, hungerAmount - 1));
        }
        player.getHungerManager().add(foodValue, 0.25f);
        player.playSound(SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1.0F, 1.0F );
        world.setBlockState(pos, getDefaultState().with(WATERLOGGED, state.get(WATERLOGGED)), Block.NOTIFY_ALL);
    }

    public void addMeats(PlayerEntity player, BlockState state, BlockPos pos, World world, Hand hand, ItemStack stack,
                         int foodValue, int foodRestored,
                         int filledState){

        int poisonAmount = state.get(POISON_VALUE);
        int nauseaAmount = state.get(NAUSEA_VALUE);
        int hungerAmount = state.get(HUNGER_VALUE);
        boolean puffer = stack.isOf(Items.PUFFERFISH);
        boolean rotten = stack.isOf(Items.ROTTEN_FLESH);
        boolean chicken = stack.isOf(Items.CHICKEN);
        boolean spider = stack.isOf(Items.SPIDER_EYE);

        if (puffer) {
            poisonAmount += 2;
            nauseaAmount ++;
            hungerAmount += 3;
        } else if (rotten) {
            hungerAmount ++;
        } else if (chicken && Math.random() < 0.3) {
            hungerAmount ++;
        } else if (spider) {
            poisonAmount ++;
        }
        world.setBlockState(pos, state
                .with(HAS_MEAT, true)
                .with(FOOD_VALUE, foodValue + foodRestored)
                .with(FILLED_STATE, filledState + 1)
                .with(POISON_VALUE, poisonAmount)
                .with(HUNGER_VALUE, hungerAmount)
                .with(NAUSEA_VALUE, nauseaAmount), Block.NOTIFY_ALL);
        if (!player.getAbilities().creativeMode) {
            player.getStackInHand(hand).decrement(1);
        }
    }
    public void addCarbs(PlayerEntity player, BlockState state, BlockPos pos, World world, Hand hand, ItemStack stack,
                         int foodValue, int foodRestored, int filledState){
        int poisonAmount = state.get(POISON_VALUE);

        if (stack.isOf(Items.POISONOUS_POTATO)) {
            poisonAmount++;
        }
        world.setBlockState(pos, state
                .with(HAS_CARBS, true)
                .with(FOOD_VALUE, foodValue + foodRestored)
                .with(FILLED_STATE, filledState + 1)
                .with(POISON_VALUE, poisonAmount), Block.NOTIFY_ALL);
        if (!player.getAbilities().creativeMode) {
            player.getStackInHand(hand).decrement(1);
        }
    }
    public void addVeggies(PlayerEntity player, BlockState state, BlockPos pos, World world, Hand hand, int foodValue,
                           int foodRestored, int filledState){
        world.setBlockState(pos, state
                .with(HAS_VEGGIES, true)
                .with(FOOD_VALUE, foodValue + foodRestored)
                .with(FILLED_STATE, filledState + 1), Block.NOTIFY_ALL);
        if (!player.getAbilities().creativeMode) {
            player.getStackInHand(hand).decrement(1);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        int filledState = state.get(FILLED_STATE);
        int foodRestored = Objects.requireNonNull(stack.getItem().getFoodComponent()).getHunger();
        int foodValue = state.get(FOOD_VALUE);
        boolean filled = filledState == 3;
        boolean empty = filledState < 1;

        if (world.isClient) {
            return ActionResult.PASS;
        }

        if (!empty && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isEmpty()) {
            consumeFood(player, state, pos, world, foodValue);
            return ActionResult.SUCCESS;
        }

        if (!filled && hand.equals(Hand.MAIN_HAND) && stack.isIn(ModItemTags.PLATE_PLACEABLE)) {

            if (stack.isIn(ModItemTags.MEATS)) {
                addMeats(player, state, pos, world, hand, stack,
                        foodValue, foodRestored, filledState);
                return ActionResult.SUCCESS;
            }
            else if (stack.isIn(ModItemTags.VEGGIES)) {
                addVeggies(player, state, pos, world, hand, foodValue, foodRestored, filledState);
                return ActionResult.SUCCESS;
            }
            else if (stack.isIn(ModItemTags.CARBS)) {
                addCarbs(player, state, pos, world, hand, stack, foodValue, foodRestored, filledState);
                return ActionResult.SUCCESS;
            }
            else {
                return ActionResult.PASS;
            }

        }
        return ActionResult.CONSUME;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FOOD_VALUE, FILLED_STATE, HAS_VEGGIES, HAS_MEAT, HAS_CARBS, POISON_VALUE, HUNGER_VALUE, NAUSEA_VALUE);
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