package net.emilsg.clutter.compat.trinkets;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;

import static net.emilsg.clutter.block.ModBlocks.*;
import static net.emilsg.clutter.block.ModBlocks.ENDER_DRAGON_PLUSHIE;

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
    }
}
