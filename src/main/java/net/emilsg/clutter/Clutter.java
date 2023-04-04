package net.emilsg.clutter;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.entity.ModBlockEntities;
import net.emilsg.clutter.item.ModItemGroup;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.util.ModFlammableAndOrStrippableBlocks;
import net.emilsg.clutter.util.ModSit;
import net.emilsg.clutter.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Clutter implements ModInitializer {

	public static final String MOD_ID = "clutter";
	public static final Logger LOGGER = LoggerFactory.getLogger("clutter");

	@Override
	public void onInitialize() {
		ModItemGroup.registerItemGroups();
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModBlockEntities.registerBlockEntities();
		ModSit.registerSitUtil();
		ModWorldGeneration.generateModWorldGen();
		ModFlammableAndOrStrippableBlocks.registerFlammableAndStrippableBlocks();
		ModBlocks.copperBlockPairs();
	}
}