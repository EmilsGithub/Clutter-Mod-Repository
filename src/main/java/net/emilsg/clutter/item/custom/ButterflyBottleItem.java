package net.emilsg.clutter.item.custom;

import com.mojang.serialization.MapCodec;
import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.emilsg.clutter.entity.variants.ButterflyVariant;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;
import java.util.Optional;

public class ButterflyBottleItem extends Item {
    public static final MapCodec<ButterflyVariant> BUTTERFLY_VARIANT_MAP_CODEC = ButterflyVariant.CODEC.fieldOf("Variant");

    public ButterflyBottleItem(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        ItemStack stack = context.getStack();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();

        if (player == null) return ActionResult.FAIL;

        if (world instanceof ServerWorld) {
            this.spawnEntity((ServerWorld)world, stack, pos);
            world.emitGameEvent(player, GameEvent.ENTITY_PLACE, pos);
            player.setStackInHand(hand, getEmptiedStack(stack, player));
        }
        return ActionResult.success(world.isClient);
    }

    public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
        return !player.getAbilities().creativeMode ? new ItemStack(Items.GLASS_BOTTLE) : stack;
    }

    private void spawnEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
        ButterflyEntity butterfly = ModEntities.BUTTERFLY.spawnFromItemStack(world, stack, null, pos, SpawnReason.BUCKET, true, false);
        NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT);
        if(butterfly != null) butterfly.copyDataFromNbt(butterfly, nbtComponent.copyNbt());
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT);
        Optional<ButterflyVariant> optional = nbtComponent.get(BUTTERFLY_VARIANT_MAP_CODEC).result();

        if (optional.isPresent()) {
            ButterflyVariant variant = optional.get();

            Formatting formatting = variant.getColorFormatting();

            String string = "clutter." + variant.getName() + ".butterfly";

            MutableText mutableText = Text.translatable(string);
            mutableText.formatted(formatting);
            tooltip.add(mutableText);
        }
    }
}
