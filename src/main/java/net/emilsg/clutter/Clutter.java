package net.emilsg.clutter;

import net.emilsg.clutter.block.ModBlockEntities;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.config.ModConfigs;
import net.emilsg.clutter.effect.ModEffects;
import net.emilsg.clutter.enchantment.ModEnchantments;
import net.emilsg.clutter.entity.ClutterAttributes;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.networking.ModMessages;
import net.emilsg.clutter.potion.ModPotions;
import net.emilsg.clutter.recipe.ModRecipeSerializers;
import net.emilsg.clutter.screen.ModScreenHandlers;
import net.emilsg.clutter.util.*;
import net.emilsg.clutter.world.gen.ModWorldGeneration;
import net.emilsg.clutter.world.gen.features.ModFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Clutter implements ModInitializer {
	public static final String MOD_VERSION = "0.5.3";
	public static final String MOD_ID = "clutter";
	public static final Logger LOGGER = LoggerFactory.getLogger("Clutter");
	public static final boolean IS_TRINKETS_LOADED = FabricLoader.getInstance().getModContainer("trinkets").isPresent();
	public static final boolean IS_SUPPLEMENTARIES_LOADED = FabricLoader.getInstance().getModContainer("supplementaries").isPresent();

	@Override
	public void onInitialize() {
		ModConfigs.registerConfigs();

		ModItemGroups.registerItemGroups();
		ModEffects.registerEffects();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModRecipeSerializers.registerRecipeSerializers();

		ModEnchantments.registerModEnchantments();
		ModLootTableModifiers.modifyLootTables();
		ModScreenHandlers.registerScreenHandlers();
		ModBlockEntities.registerBlockEntities();

		ModFeatures.registerModFeatures();

		ModWorldGeneration.generateModWorldGen();

		ModUtil.registerModUtil();
		ModTradeOffers.addTrades();

		ModCallbackRegistry.handleSitting();
		if (ModConfigs.PET_MOBS) ModCallbackRegistry.handlePetsPets();
		if (!IS_SUPPLEMENTARIES_LOADED) ModCallbackRegistry.handlePlacingBooks();
		ModCallbackRegistry.handlePlacingNautilusShells();

		ModPotions.registerPotionRecipes();

		ModBlocks.registerCopperBlockPairs();

		ClutterAttributes.registerAttributes();

		ModMessages.registerHandshakePackets();
		LOGGER.info("[Clutter] Finished initializing.");
	}


}