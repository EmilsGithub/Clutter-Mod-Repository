package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WarpedNewtEntity extends AbstractNetherNewtEntity {

    public WarpedNewtEntity(EntityType<? extends ClutterAnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Item getBreedingItem() {
        return Items.WARPED_ROOTS;
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.WARPED_NEWT.create(world);
    }
}
