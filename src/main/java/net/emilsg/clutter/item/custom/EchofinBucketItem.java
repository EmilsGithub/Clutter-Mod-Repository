package net.emilsg.clutter.item.custom;

import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.EchofinEntity;
import net.emilsg.clutter.entity.variants.EchofinVariant;
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

public class EchofinBucketItem extends Item {
    private final EchofinVariant variant;

    public EchofinBucketItem(Settings settings, EchofinVariant variant) {
        super(settings);
        this.variant = variant;
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!(world instanceof ServerWorld)) {
            return ActionResult.SUCCESS;
        } else {
            BlockPos blockPos = context.getBlockPos();
            Direction direction = context.getSide();
            BlockState blockState = world.getBlockState(blockPos);
            BlockPos blockPos2;
            if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
                blockPos2 = blockPos;
            } else {
                blockPos2 = blockPos.offset(direction);
            }

            EchofinEntity echofinEntity = ModEntities.ECHOFIN.create(world);
            assert echofinEntity != null;
            echofinEntity.setVariant(variant);
            echofinEntity.setPersistent();
            echofinEntity.updatePosition(blockPos2.getX() + 0.5, blockPos2.getY(), blockPos2.getZ() + 0.5);
            echofinEntity.setHomePos(blockPos2);
            world.playSound(null, blockPos2, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1 ,1);
            world.spawnEntity(echofinEntity);
            if(Objects.requireNonNull(context.getPlayer()).isPlayer()) {
                if(!context.getPlayer().getAbilities().creativeMode) {
                    context.getPlayer().setStackInHand(context.getHand(), new ItemStack(Items.BUCKET));
                }
            }
            world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);

            return ActionResult.CONSUME;
        }
    }
}
