package net.emilsg.clutter.util;

import net.emilsg.clutter.block.custom.SeahorseBucketItem;
import net.emilsg.clutter.entity.variants.ButterflyVariant;
import net.emilsg.clutter.entity.variants.SeahorseVariant;
import net.emilsg.clutter.item.custom.ButterflyBottleItem;
import net.emilsg.clutter.item.custom.ClutterElytraItem;
import net.emilsg.clutter.item.custom.CoinPouchItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.Optional;

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
        ModelPredicateProviderRegistry.register(elytra, Identifier.of("broken"), (stack, world, entity, seed) -> {
            if (!(stack.getItem() instanceof ClutterElytraItem clutterElytraItem)) return 0;

            return clutterElytraItem.isBroken(stack) ? 1 : 0;
        });
    }

    private static void registerButterflyInABottle(Item bottle) {
        ModelPredicateProviderRegistry.register(bottle, Identifier.of("type"), (stack, world, entity, seed) -> {
            if (!(stack.getItem() instanceof ButterflyBottleItem)) return 0;
            NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT);
            Optional<ButterflyVariant> optional = nbtComponent.get(ButterflyBottleItem.BUTTERFLY_VARIANT_MAP_CODEC).result();

            if (optional.isEmpty()) return 0;

            int id = optional.get().getId();

            return (float) id / 100;
        });
    }

    private static void registerSeahorseBucket(Item bucket) {
        ModelPredicateProviderRegistry.register(bucket, Identifier.of("type"), (stack, world, entity, seed) -> {
            if (!(stack.getItem() instanceof SeahorseBucketItem)) return 0;
            NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT);
            Optional<SeahorseVariant> optional = nbtComponent.get(SeahorseBucketItem.SEAHORSE_VARIANT_MAP_CODEC).result();

            if (optional.isEmpty()) return 0;

            int id = optional.get().getId();

            return (float) id / 10;
        });
    }

    private static void registerCoinPouch(Item pouch) {
        ModelPredicateProviderRegistry.register(pouch, Identifier.of("coins"), (stack, world, entity, seed) -> {
            if (!(stack.getItem() instanceof CoinPouchItem)) return 0;
            Rarity rarity = stack.getOrDefault(DataComponentTypes.RARITY, Rarity.COMMON);
            int rarityInt = CoinPouchItem.getIntFromRarity(rarity);

            return (float) (rarityInt + 1) / 10;
        });
    }
}
