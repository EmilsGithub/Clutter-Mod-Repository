package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.block.ModBlockEntities;
import net.emilsg.clutter.block.entity.BrickKilnBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BrickKilnBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = Properties.LIT;

    public BrickKilnBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH).with(LIT, false));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BrickKilnBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BrickKilnBlockEntity) {
                ItemScatterer.spawn(world, pos, (BrickKilnBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = ((BrickKilnBlockEntity) world.getBlockEntity(pos));

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.BRICK_KILN_ENTITY, (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1, blockEntity));
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            Direction direction = state.get(FACING);
            Direction.Axis axis = direction.getAxis();
            double xPos = (double) pos.getX() + 0.5;
            double yPos = (double) pos.getY();
            double zPos = (double) pos.getZ() + 0.5;
            double randomDouble = random.nextDouble() * 0.6 - 0.3;
            double xOffsetFront = axis == Direction.Axis.X ? (double) direction.getOffsetX() * 0.52 : randomDouble;
            double yOffsetFront = random.nextDouble() * 6.0 / 16.0;
            double zOffsetFront = axis == Direction.Axis.Z ? (double) direction.getOffsetZ() * 0.52 : randomDouble;

            if (random.nextDouble() < 0.1) {
                world.playSound(xPos, yPos, zPos, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            world.addParticle(ParticleTypes.SMOKE, xPos + xOffsetFront, yPos + yOffsetFront, zPos + zOffsetFront, 0.0, 0.0, 0.0);
            world.addParticle(ParticleTypes.FLAME, xPos + xOffsetFront, yPos + yOffsetFront, zPos + zOffsetFront, 0.0, 0.0, 0.0);
            world.addParticle(ParticleTypes.SMOKE, xPos, yPos + 1.1, zPos, 0.0, 0.0, 0.0);
        }
    }
}
