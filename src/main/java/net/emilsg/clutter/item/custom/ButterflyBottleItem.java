package net.emilsg.clutter.item.custom;

import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.emilsg.clutter.entity.variants.ButterflyVariant;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
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

            ButterflyEntity butterflyEntity = ModEntities.BUTTERFLY.create(world);
            butterflyEntity.setVariant(variant);
            butterflyEntity.setPersistent();
            butterflyEntity.updatePosition(blockPos2.getX() + 0.5, blockPos2.getY(), blockPos2.getZ() + 0.5);
            butterflyEntity.setHomePos(blockPos2);
            world.spawnEntity(butterflyEntity);
            if(Objects.requireNonNull(context.getPlayer()).isPlayer()) {
                context.getPlayer().giveItemStack(new ItemStack(Items.GLASS_BOTTLE));
                if(!context.getPlayer().getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
            }
            world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);

            return ActionResult.CONSUME;
        }
    }
}
