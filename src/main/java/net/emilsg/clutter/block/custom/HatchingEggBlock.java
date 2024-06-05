package net.emilsg.clutter.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class HatchingEggBlock extends Block {
    public static final IntProperty HATCH = Properties.HATCH;
    private final EntityType<?> type;
    private final float averageHatchTimeInMinutes;
    private final TagKey<Block> hatchBoostTag;
    private final double height;
    private final double width;

    public HatchingEggBlock(Settings settings, EntityType<?> type, float averageHatchTimeInMinutes, TagKey<Block> hatchBoostTag, double height, double width) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(HATCH, 0));
        this.type = type;
        this.averageHatchTimeInMinutes = averageHatchTimeInMinutes;
        this.hatchBoostTag = hatchBoostTag;
        this.height = height;
        this.width = width;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HATCH);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(8 - (width / 2), 0, 8 - (width / 2), 8 + (width / 2), height, 8 + (width / 2));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public int getHatchStage(BlockState state) {
        return state.get(HATCH);
    }

    private boolean isReadyToHatch(BlockState state) {
        return this.getHatchStage(state) == 2;
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!this.isReadyToHatch(state)) {
            world.playSound(null, pos, SoundEvents.BLOCK_SNIFFER_EGG_CRACK, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
            world.setBlockState(pos, state.with(HATCH, this.getHatchStage(state) + 1), 2);
        } else {
            world.playSound(null, pos, SoundEvents.BLOCK_SNIFFER_EGG_HATCH, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
            world.breakBlock(pos, false);
            AnimalEntity animalEntity = (AnimalEntity) type.create(world);
            if (animalEntity != null) {
                Vec3d vec3d = pos.toCenterPos();
                animalEntity.setBaby(true);
                animalEntity.refreshPositionAndAngles(vec3d.getX(), vec3d.getY(), vec3d.getZ(), MathHelper.wrapDegrees(world.random.nextFloat() * 360.0F), 0.0F);
                world.spawnEntity(animalEntity);
            }

        }
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        boolean aboveHatchBooster = isAboveHatchBooster(world, pos);
        if (!world.isClient() && aboveHatchBooster) {
            world.syncWorldEvent(3009, pos, 0);
        }
        var hatchTime = aboveHatchBooster ? (averageHatchTimeInMinutes * 600) : (averageHatchTimeInMinutes * 1200);
        int hatchEventTime = (int) hatchTime / 3;
        world.emitGameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Emitter.of(state));
        world.scheduleBlockTick(pos, this, hatchEventTime + world.random.nextInt(300));
    }

    public boolean isAboveHatchBooster(BlockView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isIn(hatchBoostTag);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
