package net.emilsg.clutter.util;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.entity.SeatEntity;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import static net.emilsg.clutter.block.entity.SeatEntity.OCCUPIED;

public class ModSit
{
    public static final EntityType<SeatEntity> SEAT = Registry.register(Registries.ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "seat"),
            FabricEntityTypeBuilder.<SeatEntity>create(SpawnGroup.MISC, SeatEntity::new).dimensions(EntityDimensions.fixed(0.001F, 0.001F))
                    .build());

    public static void registerSitUtil()
    {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

            if(world.isClient)
            {return ActionResult.PASS;}

            if(!world.canPlayerModifyAt(player, hitResult.getBlockPos()))
            {return ActionResult.PASS;}

            BlockState state = world.getBlockState(hitResult.getBlockPos());


            if (!OCCUPIED.containsKey(new Vec3d(hitResult.getBlockPos().getX(), hitResult.getBlockPos().getY(), hitResult.getBlockPos().getZ())))
            {
                Vec3d comparePos = new Vec3d(hitResult.getBlockPos().getX(), hitResult.getBlockPos().getY(), hitResult.getBlockPos().getZ());
                boolean notSneakingEmptyHand = player.getStackInHand(hand).isEmpty() && !player.isSneaking();

                //ARMCHAIRS
                if(notSneakingEmptyHand && state.isIn(ModBlockTags.ARMCHAIRS)) {
                    SeatEntity sit = SEAT.create(world);
                    Vec3d pos = new Vec3d(hitResult.getBlockPos().getX() + 0.5D, hitResult.getBlockPos().getY() + 0.2, hitResult.getBlockPos().getZ() + 0.5D);
                    OCCUPIED.put(comparePos, player.getBlockPos());
                    sit.updatePosition(pos.getX(), pos.getY(), pos.getZ());
                    world.spawnEntity(sit);
                    player.startRiding(sit);

                    return ActionResult.SUCCESS;
                }
                //WOODEN CHAIRS
                if(notSneakingEmptyHand && state.isIn(ModBlockTags.WOODEN_CHAIRS)) {
                    SeatEntity sit = SEAT.create(world);
                    Vec3d pos = new Vec3d(hitResult.getBlockPos().getX() + 0.5D, hitResult.getBlockPos().getY() + 0.3, hitResult.getBlockPos().getZ() + 0.5D);
                    OCCUPIED.put(comparePos, player.getBlockPos());
                    sit.updatePosition(pos.getX(), pos.getY(), pos.getZ());
                    world.spawnEntity(sit);
                    player.startRiding(sit);

                    return ActionResult.SUCCESS;
                }
                //FLOOR SEATING
                if(notSneakingEmptyHand && state.isIn(ModBlockTags.FLOOR_SEATING)) {
                    SeatEntity sit = SEAT.create(world);
                    Vec3d pos = new Vec3d(hitResult.getBlockPos().getX() + 0.5D, hitResult.getBlockPos().getY(), hitResult.getBlockPos().getZ() + 0.5D);
                    OCCUPIED.put(comparePos, player.getBlockPos());
                    sit.updatePosition(pos.getX(), pos.getY(), pos.getZ());
                    world.spawnEntity(sit);
                    player.startRiding(sit);

                    return ActionResult.SUCCESS;
                }
                //BENCHES
                if(notSneakingEmptyHand && state.isIn(ModBlockTags.BENCHES)) {
                    SeatEntity sit = SEAT.create(world);
                    Vec3d pos = new Vec3d(hitResult.getBlockPos().getX() + 0.5D, hitResult.getBlockPos().getY() + 0.3, hitResult.getBlockPos().getZ() + 0.5D);
                    OCCUPIED.put(comparePos, player.getBlockPos());
                    sit.updatePosition(pos.getX(), pos.getY(), pos.getZ());
                    world.spawnEntity(sit);
                    player.startRiding(sit);

                    return ActionResult.SUCCESS;
                }
            }

            return ActionResult.PASS;
        });
    }
}
