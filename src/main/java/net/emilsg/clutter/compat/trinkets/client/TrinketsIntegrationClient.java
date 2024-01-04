package net.emilsg.clutter.compat.trinkets.client;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.emilsg.clutter.item.ModItems;

import static net.emilsg.clutter.block.ModBlocks.*;

public class TrinketsIntegrationClient {

    public static void registerTrinkets() {
        TrinketRendererRegistry.registerRenderer((SHEEP_PLUSHIE).asItem(), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((COW_PLUSHIE).asItem(), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((PIG_PLUSHIE).asItem(), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((SQUID_PLUSHIE.asItem()), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((CHICKEN_PLUSHIE).asItem(), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((FOX_PLUSHIE).asItem(), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((SNOW_FOX_PLUSHIE).asItem(), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((OCELOT_PLUSHIE).asItem(), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((PANDA_PLUSHIE).asItem(), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((ENDER_DRAGON_PLUSHIE).asItem(), new TrinketsClientRenderer());

        TrinketRendererRegistry.registerRenderer((ModItems.BEACH_HAT), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((ModItems.TOP_HAT), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((ModItems.BERET), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((ModItems.COWBOY_HAT), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((ModItems.BUTTERFLY_WINGS), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((ModItems.CROWN), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((ModItems.CAP), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((ModItems.PROPELLER_CAP), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((ModItems.TIARA), new TrinketsClientRenderer());
        TrinketRendererRegistry.registerRenderer((ModItems.VIKING_HELMET), new TrinketsClientRenderer());
    }
}
