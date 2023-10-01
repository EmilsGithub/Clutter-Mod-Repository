package net.emilsg.clutter.block.custom.plants;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.server.world.ServerWorld;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

import java.util.function.ToIntFunction;

public class GlowLilyPlantBlock extends PlantBlock implements Fertilizable {
    public static final IntProperty AGE = Properties.AGE_2;
    public static final BooleanProperty CLIPPED = ModProperties.CLIPPED;

    public GlowLilyPlantBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(AGE, 0).with(CLIPPED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, CLIPPED);
    }

    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(ModBlocks.GLOWLILY);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(2, 0, 2, 14, 16, 14);
    }

    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 2 && !state.get(CLIPPED);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(AGE);
        if (i < 2 && random.nextInt(5) == 0) {
            BlockState blockState = state.with(AGE, i + 1);
            world.setBlockState(pos, blockState, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
        }

    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int i = state.get(AGE);
        boolean ageIsTwo = i == 2;
        ItemStack stackInHand = player.getStackInHand(hand);
        if (!ageIsTwo && stackInHand.isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        } else if(stackInHand.getItem() instanceof ShearsItem && !state.get(CLIPPED)) {
            if(!world.isClient) world.setBlockState(pos, state.with(CLIPPED, true), Block.NOTIFY_ALL);
            world.playSound(null, pos, SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if(!player.getAbilities().creativeMode) stackInHand.damage(1, player, (p) -> p.sendToolBreakStatus(hand));
            return ActionResult.success(world.isClient);
        } else if (i > 1) {
            int count = 1 + world.random.nextInt(2);
            dropStack(world, pos, new ItemStack(ModItems.GLOWLILY_BULB, count + (ageIsTwo ? 1 : 0)));
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            BlockState stateWithAge = state.with(AGE, 0);
            world.setBlockState(pos, stateWithAge, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, stateWithAge));
            return ActionResult.success(world.isClient);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 2;
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if(state.get(AGE) < 2) world.setBlockState(pos, state.with(CLIPPED, false).with(AGE, state.get(AGE) + 1), Block.NOTIFY_ALL);
    }

    public static ToIntFunction<BlockState> createLightLevelFromAge() {
        return state -> {
            return switch (state.get(AGE)) {
                default -> 0;
                case 1 -> 4;
                case 2 -> 8;
            };
        };
    }
}
