package net.emilsg.clutter.block.custom.plants;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.entity.ModEntityTypes;
import net.emilsg.clutter.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchflowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThornbloomCropBlock extends TorchflowerBlock {


    public ThornbloomCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.THORNBLOOM_SEEDS;
    }

    @Override
    public BlockState withAge(int age) {
        return age == 2 ? ModBlocks.THORNBLOOM.getDefaultState() : this.getDefaultState().with(this.getAgeProperty(), age);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE && entity.getType() != ModEntityTypes.BUTTERFLY && entity.getType() != ModEntityTypes.MOSSBLOOM && entity.getType() != EntityType.SNIFFER) {
            entity.slowMovement(state, new Vec3d(0.800000011920929, 0.75, 0.800000011920929));
            if (!world.isClient && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                double d = Math.abs(entity.getX() - entity.lastRenderX);
                double e = Math.abs(entity.getZ() - entity.lastRenderZ);
                if (d >= 0.003000000026077032 || e >= 0.003000000026077032) {
                    entity.damage(world.getDamageSources().cactus(), 2.0F);
                }
            }

        }
    }

}
