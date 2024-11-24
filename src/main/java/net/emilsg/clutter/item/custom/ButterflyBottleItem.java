package net.emilsg.clutter.item.custom;

import net.emilsg.clutter.entity.ModEntityTypes;
import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.emilsg.clutter.entity.variants.ButterflyVariant;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ButterflyBottleItem extends Item {

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
        ButterflyEntity butterfly = ModEntityTypes.BUTTERFLY.spawnFromItemStack(world, stack, (PlayerEntity)null, pos, SpawnReason.BUCKET, true, false);
        if (butterfly != null) {
            butterfly.copyDataFromNbt(butterfly, stack.getOrCreateNbt());
        }
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound != null && nbtCompound.contains("Variant", 3)) {
            int i = nbtCompound.getInt("Variant");

            Formatting formatting = ButterflyVariant.byId(i).getColorFormatting();

            String string = "clutter." + ButterflyVariant.byId(i).getName() + ".butterfly";

            MutableText mutableText = Text.translatable(string);
            mutableText.formatted(formatting);
            tooltip.add(mutableText);
        }
    }
}
