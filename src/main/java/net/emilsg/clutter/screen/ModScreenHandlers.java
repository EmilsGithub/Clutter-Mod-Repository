package net.emilsg.clutter.screen;

import net.emilsg.clutter.Clutter;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {

    public static final ScreenHandlerType<BrickKilnScreenHandler> BRICK_KILN_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Clutter.MOD_ID, "brick_kiln_screen_handler"),
                    new ExtendedScreenHandlerType<>(BrickKilnScreenHandler::new));

    public static final ScreenHandlerType<CardboardBoxScreenHandler> CARDBOARD_BOX_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Clutter.MOD_ID, "cardboard_box_screen_handler"),
                    new ScreenHandlerType<>(CardboardBoxScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final ScreenHandlerType<WallBookshelfScreenHandler> WALL_BOOKSHELF_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Clutter.MOD_ID, "wall_bookshelf_screen_handler"),
                    new ScreenHandlerType<>(WallBookshelfScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final ScreenHandlerType<PresentScreenHandler> PRESENT_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Clutter.MOD_ID, "present_screen_handler"),
                    new ScreenHandlerType<>(PresentScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static void registerScreenHandlers() {

    }
}
