package net.emilsg.clutter.util;

import net.emilsg.clutter.item.custom.ClutterElytraItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ModModelPredicateProvider {

    public static void registerModModels() {
        for (Item elytra : Registries.ITEM) {
            if (elytra instanceof ClutterElytraItem) registerElytra(elytra);
        }
    }

    private static void registerElytra(Item elytra) {
        ModelPredicateProviderRegistry.register(elytra, new Identifier("broken"), (stack, world, entity, seed) -> {
            if(!(stack.getItem() instanceof ClutterElytraItem clutterElytraItem)) return 0;
            if (entity == null) return 0.0f;

            return clutterElytraItem.isBroken(stack) ? 1 : 0;
        });
    }
}
