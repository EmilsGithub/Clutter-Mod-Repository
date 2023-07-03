package net.emilsg.clutter.item.custom;

import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.emilsg.clutter.entity.variants.ButterflyVariant;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Objects;

public class ButterflyBottleItem extends Item {
    private final ButterflyVariant variant;

    public ButterflyBottleItem(Settings settings, ButterflyVariant variant) {
        super(settings);
        this.variant = variant;
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!(world instanceof ServerWorld)) {
            return ActionResult.SUCCESS;
        } else {
            ItemStack itemStack = context.getStack();
            BlockPos blockPos = context.getBlockPos();
            Direction direction = context.getSide();
            BlockState blockState = world.getBlockState(blockPos);
            BlockPos blockPos2;
            if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
                blockPos2 = blockPos;
            } else {
                blockPos2 = blockPos.offset(direction);
            }

            ButterflyEntity butterflyEntity = ModEntities.BUTTERFLY.create(context.getWorld());
            butterflyEntity.setVariant(variant);
            butterflyEntity.setPersistent();
            butterflyEntity.updatePosition(blockPos2.getX() + 0.5, blockPos2.getY(), blockPos2.getZ() + 0.5);
            butterflyEntity.setHomePos(blockPos2);
            butterflyEntity.setBreedingAge(6000);
            world.playSound(null, blockPos2, SoundEvents.BLOCK_WOOL_FALL, SoundCategory.NEUTRAL, 1 ,1);
            world.spawnEntity(butterflyEntity);
            if(Objects.requireNonNull(context.getPlayer()).isPlayer() && !context.getPlayer().getAbilities().creativeMode) {
                context.getPlayer().giveItemStack(new ItemStack(Items.GLASS_BOTTLE));
                itemStack.decrement(1);
            }
            world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);

            return ActionResult.CONSUME;
        }
    }
}
