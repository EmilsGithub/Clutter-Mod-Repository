package net.emilsg.clutter.block.custom.plushies;

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

public class EnderDragonPlushieBlock extends AbstractPlushieBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 1.0, 0.0, 15.0, 5.0, 15.0);

    public EnderDragonPlushieBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        double random = Math.random();
        ItemStack stack = player.getStackInHand(hand);

        if (!stack.isOf(Items.AIR)) {
            return ActionResult.PASS;
        }

        if(hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isEmpty()) {
            if(world.isClient) {
                world.addParticle(ParticleTypes.NOTE, pos.getX() + 0.5, pos.getY() + 0.7, pos.getZ() + 0.5, random, 0.0, 0.0);
                return ActionResult.SUCCESS;
            }
            world.playSound(null, pos, SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.BLOCKS, 0.125f, 1.4f);
            return ActionResult.CONSUME;
        }
        return ActionResult.CONSUME;
    }
}
