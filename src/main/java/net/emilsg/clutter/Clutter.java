package net.emilsg.clutter;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Clutter implements ModInitializer {

	public static final String MOD_ID = "clutter";
	public static final Logger LOGGER = LoggerFactory.getLogger("clutter");

	@Override
	public void onInitialize() {

		LOGGER.info("Hello Fabric world!");
	}
}