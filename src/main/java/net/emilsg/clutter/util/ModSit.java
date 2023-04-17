package net.emilsg.clutter.util;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.custom.FloorSeatBlock;
import net.emilsg.clutter.block.custom.SeatBlock;
import net.emilsg.clutter.block.entity.SeatEntity;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static net.emilsg.clutter.block.entity.SeatEntity.OCCUPIED;

public class ModSit
{
    public static final EntityType<SeatEntity> SEAT = Registry.register(Registry.ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "seat"),
            FabricEntityTypeBuilder.<SeatEntity>create(SpawnGroup.MISC, SeatEntity::new).dimensions(EntityDimensions.fixed(0.001F, 0.001F))
                    .build());

    private static ActionResult spawnSeat(World world, PlayerEntity player, BlockState state, BlockPos blockPos, double yOffset, Vec3d comparePos) {
        if (state.getBlock() instanceof SeatBlock || state.getBlock() instanceof FloorSeatBlock) {
            SeatEntity sit = SEAT.create(world);
            Vec3d pos = new Vec3d(blockPos.getX() + 0.5D, blockPos.getY() + yOffset, blockPos.getZ() + 0.5D);
            OCCUPIED.put(comparePos, player.getBlockPos());
            assert sit != null;
            sit.updatePosition(pos.getX(), pos.getY(), pos.getZ());
            world.spawnEntity(sit);
            player.startRiding(sit);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public static void registerSitUtil() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

            if(world.isClient) {
                return ActionResult.PASS;
            }

            if(!world.canPlayerModifyAt(player, hitResult.getBlockPos())) {
                return ActionResult.PASS;
            }

            BlockState state = world.getBlockState(hitResult.getBlockPos());


            if (!OCCUPIED.containsKey(new Vec3d(hitResult.getBlockPos().getX(), hitResult.getBlockPos().getY(), hitResult.getBlockPos().getZ()))) {
                Vec3d comparePos = new Vec3d(hitResult.getBlockPos().getX(), hitResult.getBlockPos().getY(), hitResult.getBlockPos().getZ());
                boolean notSneakingEmptyHand = player.getStackInHand(hand).isEmpty() && !player.isSneaking();
                boolean noAxe = !(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof AxeItem || player.getStackInHand(Hand.OFF_HAND).getItem() instanceof AxeItem);

                // ARMCHAIRS
                if (notSneakingEmptyHand && state.isIn(ModBlockTags.ARMCHAIRS)) {
                    return spawnSeat(world, player, state, hitResult.getBlockPos(), 0.2, comparePos);
                }

                // WOODEN CHAIRS
                if (noAxe && notSneakingEmptyHand && state.isIn(ModBlockTags.WOODEN_CHAIRS)) {
                    return spawnSeat(world, player, state, hitResult.getBlockPos(), 0.3, comparePos);
                }

                // FLOOR SEATING
                if (notSneakingEmptyHand && state.isIn(ModBlockTags.FLOOR_SEATING)) {
                    return spawnSeat(world, player, state, hitResult.getBlockPos(), 0, comparePos);
                }

                // BENCHES
                if (noAxe && notSneakingEmptyHand && state.isIn(ModBlockTags.BENCHES)) {
                    return spawnSeat(world, player, state, hitResult.getBlockPos(), 0.3, comparePos);
                }
            }
            return ActionResult.PASS;
        });
    }
}
