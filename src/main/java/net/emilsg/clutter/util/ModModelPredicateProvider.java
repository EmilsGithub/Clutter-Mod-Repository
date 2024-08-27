package net.emilsg.clutter.util;

import net.emilsg.clutter.item.custom.ButterflyBottleItem;
import net.emilsg.clutter.item.custom.ClutterElytraItem;
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
        }
    }

    private static void registerElytra(Item elytra) {
        ModelPredicateProviderRegistry.register(elytra, new Identifier("broken"), (stack, world, entity, seed) -> {
            if (!(stack.getItem() instanceof ClutterElytraItem clutterElytraItem)) return 0;
            if (entity == null) return 0.0f;

            return clutterElytraItem.isBroken(stack) ? 1 : 0;
        });
    }

    private static void registerButterflyInABottle(Item bottle) {
        ModelPredicateProviderRegistry.register(bottle, new Identifier("type"), (stack, world, entity, seed) -> {
            if (!(stack.getItem() instanceof ButterflyBottleItem)) return 0;
            if (entity == null) return 0;

            NbtCompound nbtCompound = stack.getNbt();

            if (nbtCompound == null || !nbtCompound.contains("Variant", 3)) return 0;

            int id = nbtCompound.getInt("Variant");

            return (float) id / 100;
        });
    }
}
