package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.EmperorPenguinEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;

public class EmperorPenguinEggBlock extends Block {
    public static final IntProperty HATCH = Properties.HATCH;
    private static final VoxelShape SHAPE = VoxelShapes.union(Block.createCuboidShape(6, 0, 6, 10, 6, 10));

    public EmperorPenguinEggBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(HATCH, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HATCH);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.shouldHatchProgress(world, state)) {
            int hatchState = (Integer)state.get(HATCH);
            if (hatchState < 2) {
                world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                world.setBlockState(pos, (BlockState)state.with(HATCH, hatchState + 1), 2);
            } else {
                world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                world.removeBlock(pos, false);

                for(int j = 0; j < 1; ++j) {
                    world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
                    EmperorPenguinEntity emperorPenguinEntity = ModEntities.EMPEROR_PENGUIN.create(world);
                    if (emperorPenguinEntity != null) {
                        emperorPenguinEntity.setBaby(true);
                        emperorPenguinEntity.refreshPositionAndAngles((double)pos.getX() + 0.3 + (double)j * 0.2, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.3, 0.0F, 0.0F);
                        world.spawnEntity(emperorPenguinEntity);
                    }
                }
            }
        }
    }

    private boolean shouldHatchProgress(World world, BlockState state) {
        boolean isDay = world.isDay();
        boolean isNether = world.getDimensionKey() == DimensionTypes.THE_NETHER;
        boolean isEnd = world.getDimensionKey() == DimensionTypes.THE_END;
        return isDay || isNether || isEnd;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
