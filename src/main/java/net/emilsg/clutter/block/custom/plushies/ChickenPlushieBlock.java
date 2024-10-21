package net.emilsg.clutter.block.custom.plushies;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ChickenPlushieBlock extends AbstractPlushieBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);

    public static final MapCodec<ChickenPlushieBlock> CODEC = createCodec(ChickenPlushieBlock::new);

    @Override
    protected MapCodec<? extends AbstractPlushieBlock> getCodec() {
        return CODEC;
    }

    public ChickenPlushieBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        double random = Math.random();
        ItemStack stack = player.getStackInHand(hand);

        if (!stack.isOf(Items.AIR)) {
            return ActionResult.PASS;
        }

        if (world.isClient && hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isEmpty()) {
            world.addParticle(ParticleTypes.NOTE, pos.getX() + 0.5, pos.getY() + 0.7, pos.getZ() + 0.5, random, 0.0, 0.0);
            return ActionResult.SUCCESS;
        }

        if (hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isEmpty()) {
            world.playSound(null, pos, SoundEvents.ENTITY_CHICKEN_AMBIENT, SoundCategory.BLOCKS, 1.0f, 1.25f);
            return ActionResult.CONSUME;
        }

        return ActionResult.CONSUME;
    }
}
