package net.emilsg.clutter.util;

import net.emilsg.clutter.networking.ModMessages;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class ModKeyInputHandler {
    public static final String KEY_CATEGORY_CLUTTER = "key.category.clutter";
    public static final String KEY_PET = "key.clutter.pet";

    public static KeyBinding petKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (petKey.wasPressed()) {
                ClientPlayNetworking.send(ModMessages.SYNC_PETS, PacketByteBufs.create());
                if(client.player != null) client.player.swingHand(Hand.MAIN_HAND);
            }
        });
    }

    public static void register() {
        petKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_PET, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_CLUTTER));

        registerKeyInputs();
    }
}
