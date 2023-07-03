package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.util.ModItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
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

public class TablePlateBlock extends Block implements Waterloggable {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty MEATS = BooleanProperty.of("meats");
    public static final BooleanProperty VEGGIES_AND_FRUITS = BooleanProperty.of("veggies_and_fruits");
    public static final BooleanProperty CARBS = BooleanProperty.of("carbs");

    public static final IntProperty FOOD = IntProperty.of("food", 0, 32);
    public static final IntProperty FILLED = IntProperty.of("filled", 0, 3);


    public TablePlateBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        int filled = state.get(FILLED);
        int food = state.get(FOOD);

        boolean full = filled == 3;
        boolean empty = filled < 1;

        if (!world.isClient && hand.equals(Hand.MAIN_HAND)) {
            if (!stack.isIn(ModItemTags.PLATE_PLACEABLE)) {
                if (!empty && stack.isEmpty()) {
                    if (state.get(MEATS) && state.get(VEGGIES_AND_FRUITS) && state.get(CARBS)) {
                        player.getHungerManager().add(food, 0.5f);
                    } else {
                        player.getHungerManager().add(food, 0.25f);
                    }
                    world.setBlockState(pos, state.with(FILLED, 0).with(FOOD, 0).with(MEATS, false).with(VEGGIES_AND_FRUITS, false).with(CARBS, false), Block.NOTIFY_ALL);
                    player.playSound(SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    return ActionResult.SUCCESS;
                }
                return ActionResult.CONSUME_PARTIAL;
            } else if (!full && stack.isIn(ModItemTags.PLATE_PLACEABLE)) {
                int foodRestored = Objects.requireNonNull(stack.getItem().getFoodComponent()).getHunger();

                if (stack.isIn(ModItemTags.MEATS)) {
                    if (!player.getAbilities().creativeMode) {
                        player.getStackInHand(hand).decrement(1);
                    }
                    world.setBlockState(pos, state.with(FILLED, state.get(FILLED) + 1).with(FOOD, state.get(FOOD) + foodRestored).with(MEATS, true), Block.NOTIFY_ALL);
                } else if (stack.isIn(ModItemTags.VEGGIES)) {
                    if (!player.getAbilities().creativeMode) {
                        player.getStackInHand(hand).decrement(1);
                    }
                    world.setBlockState(pos, state.with(FILLED, state.get(FILLED) + 1).with(FOOD, state.get(FOOD) + foodRestored).with(VEGGIES_AND_FRUITS, true), Block.NOTIFY_ALL);
                } else if (stack.isIn(ModItemTags.CARBS)) {
                    if (!player.getAbilities().creativeMode) {
                        player.getStackInHand(hand).decrement(1);
                    }
                    world.setBlockState(pos, state.with(FILLED, state.get(FILLED) + 1).with(FOOD, state.get(FOOD) + foodRestored).with(CARBS, true), Block.NOTIFY_ALL);
                }
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.CONSUME;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, MEATS, VEGGIES_AND_FRUITS, CARBS, FILLED, FOOD);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos;
        World worldAccess = ctx.getWorld();
        boolean bl = worldAccess.getFluidState(blockPos = ctx.getBlockPos()).getFluid() == Fluids.WATER;
        return (BlockState)this.getDefaultState()
                .with(WATERLOGGED, bl)
                .with(MEATS, false)
                .with(VEGGIES_AND_FRUITS, false)
                .with(CARBS, false)
                .with(FOOD, 0)
                .with(FILLED, 0);
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
