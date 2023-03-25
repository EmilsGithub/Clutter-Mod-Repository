package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.util.ModBlockTags;
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
        if (!world.isClient) {
            toggleBlockOpen(state, world, pos);
        }
        return ActionResult.SUCCESS;
    }

    public static void toggleBlockOpen(BlockState state, World world, BlockPos pos) {
        boolean open = !state.get(OPEN);
        world.setBlockState(pos, state.with(OPEN, open), 3);
        updateConnectedBlocks(world, pos, open);
    }

    public static void updateConnectedBlocks(World world, BlockPos pos, boolean open) {
        Queue<BlockPos> queue = new LinkedList<>();
        Set<BlockPos> visited = new HashSet<>();

        queue.offer(pos);
        visited.add(pos);

        while (!queue.isEmpty()) {
            BlockPos currPos = queue.poll();
            BlockState currState = world.getBlockState(currPos);

            if (currState.isIn(ModBlockTags.LONG_CURTAINS) && !visited.contains(currPos)) {
                visited.add(currPos);
                world.setBlockState(currPos, currState.with(OPEN, open), 3);

                for (Direction direction : Direction.values()) {
                    BlockPos nextPos = currPos.offset(direction);
                    BlockState nextState = world.getBlockState(nextPos);
                    if (!visited.contains(nextPos) && nextState.isIn(ModBlockTags.LONG_CURTAINS)) {
                        queue.offer(nextPos);
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

