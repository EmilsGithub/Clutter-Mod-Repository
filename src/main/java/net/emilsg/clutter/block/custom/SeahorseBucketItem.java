package net.emilsg.clutter.block.custom;

import com.mojang.serialization.MapCodec;
import net.emilsg.clutter.entity.ModEntityTypes;
import net.emilsg.clutter.entity.custom.SeahorseEntity;
import net.emilsg.clutter.entity.variants.SeahorseVariant;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class SeahorseBucketItem extends BucketItem {
    public static final MapCodec<SeahorseVariant> SEAHORSE_VARIANT_MAP_CODEC = SeahorseVariant.CODEC.fieldOf("Variant");
    private final EntityType<SeahorseEntity> seahorse = ModEntityTypes.SEAHORSE;

    public SeahorseBucketItem(Fluid fluid, Settings settings) {
        super(fluid, settings);

    }

    @Override
    public void onEmptied(@Nullable PlayerEntity player, World world, ItemStack stack, BlockPos pos) {
        if (world instanceof ServerWorld) {
            this.spawnEntity((ServerWorld)world, stack, pos);
            world.emitGameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }

    @Override
    protected void playEmptyingSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos) {
        world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }

    private void spawnEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
        SeahorseEntity seahorse = ModEntityTypes.SEAHORSE.spawnFromItemStack(world, stack, null, pos, SpawnReason.BUCKET, true, false);
        NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT);
        if(seahorse != null) seahorse.copyDataFromNbt(seahorse, nbtComponent.copyNbt());
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT);
        Optional<SeahorseVariant> optional = nbtComponent.get(SEAHORSE_VARIANT_MAP_CODEC).result();

        if (optional.isPresent()) {
            SeahorseVariant variant = optional.get();
            Formatting formatting = variant.getColorFormatting();
            String string = "clutter." + variant.getName() + ".seahorse";
            MutableText mutableText = Text.translatable(string);
            mutableText.formatted(formatting);
            tooltip.add(mutableText);
        }
    }

}
