package net.emilsg.clutter.block.custom.coins;

import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class SilverCoinStackBlock extends Block {
    public static final IntProperty COIN_LAYERS = IntProperty.of("coin_layers", 1, 8);

    public SilverCoinStackBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(COIN_LAYERS, 1));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(COIN_LAYERS)) {
            case 1 -> Block.createCuboidShape(0,0,0,16,2,16);
            case 2 -> Block.createCuboidShape(0,0,0,16,4,16);
            case 3 -> Block.createCuboidShape(0,0,0,16,6,16);
            case 4 -> Block.createCuboidShape(0,0,0,16,8,16);
            case 5 -> Block.createCuboidShape(0,0,0,16,10,16);
            case 6 -> Block.createCuboidShape(0,0,0,16,12,16);
            case 7 -> Block.createCuboidShape(0,0,0,16,14,16);
            default -> Block.createCuboidShape(0,0,0,16,16,16);
        };
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos posDown = pos.down();
        BlockState downState = world.getBlockState(posDown);
        return downState.isFullCube(world, posDown);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient && player.getStackInHand(hand).isOf(ModItems.SILVER_COIN) && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).getCount() >= 8 && state.get(COIN_LAYERS) < 8 && !player.isSneaking()) {
            return ActionResult.SUCCESS;
        }
        if (!world.isClient && player.getStackInHand(hand).isOf(ModItems.SILVER_COIN) && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).getCount() >= 8 && state.get(COIN_LAYERS) < 8 && !player.isSneaking()) {
            world.setBlockState(pos, state.with(COIN_LAYERS, state.get(COIN_LAYERS) + 1), Block.NOTIFY_ALL);
            world.playSound(null, pos, ModSounds.COIN_PILE_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (!player.getAbilities().creativeMode) {
                player.getStackInHand(hand).decrement(8);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COIN_LAYERS);
    }

    @Override
    @Deprecated
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (world.getBlockState(pos.down()).getBlock() == Blocks.AIR) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}
