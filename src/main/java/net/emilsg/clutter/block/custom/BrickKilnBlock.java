package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.block.ModBlockEntities;
import net.emilsg.clutter.block.entity.BrickKilnEntity;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BrickKilnBlock extends AbstractFurnaceBlock {

    public BrickKilnBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BrickKilnEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(world, type, ModBlockEntities.BRICK_KILN_ENTITY);
    }

    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BrickKilnEntity) {
            player.openHandledScreen((NamedScreenHandlerFactory)blockEntity);
        }
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
