package net.emilsg.clutter.util;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.custom.*;
import net.emilsg.clutter.block.entity.SeatEntity;
import net.emilsg.clutter.config.ModConfigs;
import net.emilsg.clutter.entity.ModEntities;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

import static net.emilsg.clutter.block.entity.SeatEntity.IS_OCCUPIED;

public class ModCallbackRegistry {

    public static void handleSitting() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockState state = world.getBlockState(hitResult.getBlockPos());
            BlockPos blockPos = hitResult.getBlockPos();
            Vec3d comparePos = new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            boolean notSneakingAndHandEmpty = !player.isSneaking() && player.getStackInHand(hand).isEmpty();

            if (world.isClient || !world.canPlayerModifyAt(player, blockPos) || IS_OCCUPIED.containsKey(comparePos) || !notSneakingAndHandEmpty) {
                return ActionResult.PASS;
            }

            boolean noAxe = !(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof AxeItem || player.getStackInHand(Hand.OFF_HAND).getItem() instanceof AxeItem);

            if (state.getBlock() instanceof ArmchairBlock) {
                return spawnSeat(world, player, blockPos, 0.2, comparePos);
            } else if (noAxe && state.getBlock() instanceof WoodenChairBlock) {
                return spawnSeat(world, player, blockPos, 0.3, comparePos);
            } else if (state.getBlock() instanceof FloorSeatBlock) {
                return spawnSeat(world, player, blockPos, 0, comparePos);
            } else if (noAxe && state.getBlock() instanceof WoodenBenchBlock) {
                return spawnSeat(world, player, blockPos, 0.3, comparePos);
            } else if (noAxe && state.getBlock() instanceof ToiletBlock) {
                return spawnSeat(world, player, blockPos, 0.3, comparePos);
            }

            return ActionResult.PASS;
        });
    }

    public static void handlePetsPets() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (ModConfigs.PET_MOBS && (entity instanceof PassiveEntity || (entity instanceof HostileEntity && ((HostileEntity) entity).isBaby())) && player.getStackInHand(hand).isEmpty() && player.isSneaking()) {
                Random random = new Random();

                if(entity instanceof PassiveEntity && ((PassiveEntity) entity).getHealth() <= ((PassiveEntity) entity).getMaxHealth() && random.nextInt(10) == 0) {
                    ((PassiveEntity) entity).heal(1.0f);
                }

                double x = entity.getX();
                double y = entity.getY() + entity.getHeight() + 0.4;
                double z = entity.getZ();

                MobEntity mobEntity = (MobEntity) entity;
                mobEntity.playAmbientSound();

                for (int i = 0; i < 7; i++) {
                    world.addParticle(ParticleTypes.HEART, x + random.nextDouble() / 2.0 * (double)(random.nextBoolean() ? 1 : -1), y + random.nextDouble() / 2.0 * (double)(random.nextBoolean() ? 1 : -1), z + random.nextDouble() / 2.0 * (double)(random.nextBoolean() ? 1 : -1), 0, 0, 0);
                }

                return ActionResult.success(world.isClient);
            }

            return ActionResult.PASS;
        });
    }

    public static void handlePlacingBooks() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockPos blockPos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(blockPos);
            Direction side = hitResult.getSide();
            BlockState stateSide = world.getBlockState(hitResult.getBlockPos().offset(side));
            BlockPos blockPosSide = hitResult.getBlockPos().offset(side);

            if (world.isClient || !world.canPlayerModifyAt(player, blockPos) || !player.getStackInHand(hand).isOf(Items.BOOK)) {
                return ActionResult.PASS;
            }

            if (player.getStackInHand(hand).isOf(Items.BOOK)) {
                if (state.isOf(ModBlocks.BOOK_PILE) && !state.get(ModProperties.LAYERS).equals(7) && !player.isSneaking()) {
                    world.setBlockState(blockPos, ModBlocks.BOOK_PILE.getDefaultState()
                            .with(ModProperties.LAYERS, state.get(ModProperties.LAYERS) + 1), Block.NOTIFY_ALL);
                    return decrementAndPlaySound(player, world, blockPos, hand, SoundEvents.ITEM_BOOK_PUT);
                }

                if (player.isSneaking()) {
                    if (!state.isOf(ModBlocks.BOOK_PILE) && stateSide.isReplaceable()) {
                        world.setBlockState(blockPosSide, ModBlocks.BOOK_PILE.getDefaultState()
                                .with(Properties.HORIZONTAL_FACING, player.getHorizontalFacing()), Block.NOTIFY_ALL);
                        return decrementAndPlaySound(player, world, blockPos, hand, SoundEvents.ITEM_BOOK_PUT);
                    }
                }
            }
            return ActionResult.PASS;
        });
    }

    public static void handlePlacingNautilusShells() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            Direction side = hitResult.getSide();
            BlockState stateSide = world.getBlockState(hitResult.getBlockPos().offset(side));
            BlockPos blockPosSide = hitResult.getBlockPos().offset(side);

            if (world.isClient || !world.canPlayerModifyAt(player, blockPosSide) || !player.getStackInHand(hand).isOf(Items.NAUTILUS_SHELL)) {
                return ActionResult.PASS;
            }

            if (player.getStackInHand(hand).isOf(Items.NAUTILUS_SHELL) && player.isSneaking()) {
                if (stateSide.isReplaceable() && Block.sideCoversSmallSquare(world, blockPosSide.down(), Direction.UP)) {
                    world.setBlockState(blockPosSide, ModBlocks.NAUTILUS_SHELL_BLOCK.getDefaultState()
                            .with(Properties.HORIZONTAL_FACING, player.getHorizontalFacing().getOpposite())
                            .with(Properties.WATERLOGGED, world.getFluidState(blockPosSide).isOf(Fluids.WATER)), Block.NOTIFY_ALL);
                    return decrementAndPlaySound(player, world, blockPosSide, hand, SoundEvents.BLOCK_BONE_BLOCK_PLACE);
                }
            }
            return ActionResult.PASS;
        });
    }

    private static ActionResult spawnSeat(World world, PlayerEntity player, BlockPos blockPos, double yOffset, Vec3d comparePos) {
        SeatEntity seatEntity = ModEntities.SEAT.create(world);
        if(seatEntity != null) {
            Vec3d pos = new Vec3d(blockPos.getX() + 0.5f, blockPos.getY() + yOffset, blockPos.getZ() + 0.5f);
            IS_OCCUPIED.put(comparePos, player.getBlockPos());
            seatEntity.updatePosition(pos.getX(), pos.getY(), pos.getZ());
            world.spawnEntity(seatEntity);
            player.startRiding(seatEntity);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private static ActionResult decrementAndPlaySound(PlayerEntity player, World world, BlockPos blockPos, Hand hand, SoundEvent events) {
        if (!player.getAbilities().creativeMode) player.getStackInHand(hand).decrement(1);
        world.playSound(null, blockPos, events, SoundCategory.BLOCKS, 1.0f, 0.9f);
        return ActionResult.SUCCESS;
    }
}
