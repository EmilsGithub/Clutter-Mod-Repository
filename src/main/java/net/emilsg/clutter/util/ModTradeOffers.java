package net.emilsg.clutter.util;

import net.emilsg.clutter.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;

public class ModTradeOffers {
    public static void addTrades() {
        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
            factories.add((entity, random) ->
                    new TradeOffer(new TradedItem(Items.EMERALD, random.nextBetween(2, 4)),
                            new ItemStack(ModItems.GIANT_LILY_PAD, 1), 8, 0, 0.0f));
            factories.add((entity, random) ->
                    new TradeOffer(new TradedItem(Items.EMERALD, random.nextBetween(2, 4)),
                            new ItemStack(ModItems.SMALL_LILY_PADS, 1), 8, 0, 0.0f));
            factories.add((entity, random) ->
                    new TradeOffer(new TradedItem(Items.EMERALD, random.nextBetween(2, 4)),
                            new ItemStack(ModItems.GIANT_LILY_PAD_SEEDLING, 1), 8, 0, 0.0f));
        });
    }
}
