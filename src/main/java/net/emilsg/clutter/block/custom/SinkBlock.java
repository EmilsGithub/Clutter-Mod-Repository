package net.emilsg.clutter.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SinkBlock extends HorizontalFacingBlock {

    private static final BooleanProperty HAS_WATER = BooleanProperty.of("has_water");
    private static final BooleanProperty ON = BooleanProperty.of("on");
    private static final BooleanProperty INFINITE = BooleanProperty.of("infinite");

    protected static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 0, 0, 2, 3, 2),
            Block.createCuboidShape(0, 0, 14, 2, 3, 16),
            Block.createCuboidShape(14, 0, 0, 16, 3, 2),
            Block.createCuboidShape(0, 3, 0, 16, 11, 16),
            Block.createCuboidShape(0, 11, 0, 2, 16, 16),
            Block.createCuboidShape(14, 11, 0, 16, 16, 16),
            Block.createCuboidShape(2, 11, 0, 14, 16, 2),
            Block.createCuboidShape(2, 11, 14, 14, 16, 16),
            Block.createCuboidShape(14, 0, 14, 16, 3, 16));

    public SinkBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, ON, HAS_WATER, INFINITE);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        boolean neighbourWater = false;

        for (BlockPos neighbourPos : BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
            BlockState neighbourState = world.getBlockState(neighbourPos);
            if (neighbourState.getFluidState().getFluid().equals(Fluids.WATER)) {
                neighbourWater = true;
                break;
            }
        }

        return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(ON, false).with(HAS_WATER, neighbourWater).with(INFINITE, false);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean infinite = false;
        boolean hasWater = state.get(HAS_WATER);
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(neighborPos);

            if (neighborState.getFluidState().getFluid().equals(Fluids.WATER)) {
                infinite = true;
                break;
            } else {
                infinite = false;
            }
        }

        if (infinite) {
            hasWater = true;
        } else {
            hasWater = state.get(HAS_WATER);
        }

        world.setBlockState(pos, state.with(HAS_WATER, hasWater).with(INFINITE, infinite));
    }


    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(ON)) {
            if (random.nextInt(4) == 0) {
                world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25f + 0.5f, random.nextFloat() + 0.8f, false);
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        boolean bucketsAndBottle = stack.isOf(Items.WATER_BUCKET) || stack.isOf(Items.BUCKET) || stack.isOf(Items.GLASS_BOTTLE);

        if (!bucketsAndBottle && !stack.isOf(Items.AIR)) {
            return ActionResult.PASS;
        }

        if (!world.isClient && bucketsAndBottle) {
        if (stack.isOf(Items.WATER_BUCKET) && !state.get(HAS_WATER)) {
            world.setBlockState(pos, state.with(HAS_WATER, true), Block.NOTIFY_ALL);
            world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (!player.getAbilities().creativeMode) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(player.getStackInHand(hand), player, new ItemStack(Items.BUCKET)));
            }
            return ActionResult.SUCCESS;
        } else if (stack.isOf(Items.BUCKET) && state.get(ON)) {
            if (!state.get(INFINITE)) {
                world.setBlockState(pos, state.with(HAS_WATER, false).with(ON, false), Block.NOTIFY_ALL);
            }
            world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            player.setStackInHand(hand, ItemUsage.exchangeStack(player.getStackInHand(hand), player, new ItemStack(Items.WATER_BUCKET)));
            return ActionResult.SUCCESS;
        } else if (stack.isOf(Items.GLASS_BOTTLE) && state.get(ON)) {
            if (!state.get(INFINITE)) {
                world.setBlockState(pos, state.with(HAS_WATER, false).with(ON, false), Block.NOTIFY_ALL);
            }
            world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            player.setStackInHand(hand, ItemUsage.exchangeStack(player.getStackInHand(hand), player, PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER)));
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME_PARTIAL;
    }
        else if (!world.isClient && stack.isOf(Items.AIR) && state.get(HAS_WATER) && hand.equals(Hand.MAIN_HAND)) {
            if (state.get(ON)) {
                world.setBlockState(pos, state.with(ON, false), Block.NOTIFY_ALL);
            } else if (!state.get(ON)) {
                world.setBlockState(pos, state.with(ON, true), Block.NOTIFY_ALL);
            }
            world.playSound(null, pos, SoundEvents.BLOCK_IRON_DOOR_OPEN, SoundCategory.BLOCKS, 0.75f, 2.0f);
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }
}
