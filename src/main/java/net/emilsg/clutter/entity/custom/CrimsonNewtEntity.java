package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.effect.ModEffects;
import net.emilsg.clutter.entity.ModEntityTypes;
import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.emilsg.clutter.util.ModBlockTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class CrimsonNewtEntity extends AbstractNetherNewtEntity {

    public CrimsonNewtEntity(EntityType<? extends ClutterAnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static boolean isValidNaturalSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(ModBlockTags.CRIMSON_NEWTS_SPAWN_ON);
    }

    @Override
    public Item getBreedingItem() {
        return Items.CRIMSON_ROOTS;
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntityTypes.CRIMSON_NEWT.create(world);
    }

    @Override
    public StatusEffect getOnAttackEffect() {
        return ModEffects.VULNERABILITY;
    }
}
