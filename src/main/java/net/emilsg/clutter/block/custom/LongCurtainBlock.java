package net.emilsg.clutter.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class LongCurtainBlock extends HorizontalFacingBlock {
    public static BooleanProperty OPEN = BooleanProperty.of("open");
    public static final int MAX_MODEL = 2;
    public static IntProperty MODEL = IntProperty.of("model", 0, MAX_MODEL);

    public LongCurtainBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(OPEN, true));

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
            applyOpenToNeighbors(pos, world, state);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public void applyOpenToNeighbors(BlockPos pos, World world, BlockState state) {
        Queue<BlockPos> queue = new LinkedList<>();
        Set<BlockPos> visited = new HashSet<>();
        queue.offer(pos);
        visited.add(pos);

        boolean open = state.get(OPEN);

        while (!queue.isEmpty()) {
            BlockPos currentPos = queue.poll();
            BlockState currentState = world.getBlockState(currentPos);

            if (currentState.isOf(state.getBlock())) {
                if (currentState.get(OPEN) != open) {
                    world.setBlockState(currentPos, currentState.with(OPEN, open), Block.NOTIFY_ALL);
                    for (Direction direction : Direction.values()) {
                        BlockPos neighborPos = currentPos.offset(direction);
                        if (!visited.contains(neighborPos)) {
                            visited.add(neighborPos);
                            queue.offer(neighborPos);
                        }
                    }
                }
            }
        }
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(MODEL, FACING, OPEN);
    }
}
