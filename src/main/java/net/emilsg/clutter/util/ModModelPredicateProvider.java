package net.emilsg.clutter.util;

import net.emilsg.clutter.block.custom.SeahorseBucketItem;
import net.emilsg.clutter.item.custom.ButterflyBottleItem;
import net.emilsg.clutter.item.custom.ClutterElytraItem;
import net.emilsg.clutter.item.custom.CoinPouchItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ModModelPredicateProvider {

    public static void registerModModels() {
        for (Item item : Registries.ITEM) {
            if (item instanceof ClutterElytraItem) registerElytra(item);
            if (item instanceof ButterflyBottleItem) registerButterflyInABottle(item);
            if (item instanceof SeahorseBucketItem) registerSeahorseBucket(item);
            if (item instanceof CoinPouchItem) registerCoinPouch(item);
        }
    }

    private static void registerElytra(Item elytra) {
        ModelPredicateProviderRegistry.register(elytra, new Identifier("broken"), (stack, world, entity, seed) -> {
            if (!(stack.getItem() instanceof ClutterElytraItem clutterElytraItem)) return 0;

            return clutterElytraItem.isBroken(stack) ? 1 : 0;
        });
    }

    private static void registerButterflyInABottle(Item bottle) {
        ModelPredicateProviderRegistry.register(bottle, new Identifier("type"), (stack, world, entity, seed) -> {
            if (!(stack.getItem() instanceof ButterflyBottleItem)) return 0;

            NbtCompound nbtCompound = stack.getNbt();

            if (nbtCompound == null || !nbtCompound.contains("Variant", 3)) return 0;

            int id = nbtCompound.getInt("Variant");

            return (float) id / 100;
        });
    }

    private static void registerSeahorseBucket(Item bucket) {
        ModelPredicateProviderRegistry.register(bucket, new Identifier("type"), (stack, world, entity, seed) -> {
            if (!(stack.getItem() instanceof SeahorseBucketItem)) return 0;

            NbtCompound nbtCompound = stack.getNbt();

            if (nbtCompound == null || !nbtCompound.contains("Variant", 3)) return 0;

            int id = nbtCompound.getInt("Variant");

            return (float) id / 10;
        });
    }

    private static void registerCoinPouch(Item pouch) {
        ModelPredicateProviderRegistry.register(pouch, new Identifier("coins"), (stack, world, entity, seed) -> {
            if (!(stack.getItem() instanceof CoinPouchItem)) return 0;

            NbtCompound nbtCompound = stack.getNbt();

            if (nbtCompound == null || !nbtCompound.contains("Rarity", 3)) return 0;

            int rarity = nbtCompound.getInt("Rarity");

            return (float) (rarity + 1) / 10;
        });
    }
}
