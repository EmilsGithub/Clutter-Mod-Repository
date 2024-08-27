package net.emilsg.clutter.mixin;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.config.Configs;
import net.emilsg.clutter.config.ModConfigManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(BeaconScreen.class)
public class BeaconScreenMixin extends Screen {

    @Unique
    private int nextItem = 0, ticker = 0;

    protected BeaconScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"), cancellable = true)
    private void drawCoinsForBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        if (ModConfigManager.get(Configs.doEnhancedBeaconDisplay, true)) {
            ticker++;
            int backgroundWidth = 230;
            int backgroundHeight = 219;
            int i = (this.width - backgroundWidth) / 2;
            int j = (this.height - backgroundHeight) / 2;
            context.drawTexture(new Identifier(Clutter.MOD_ID, "textures/gui/container/altered_beacon.png"), i, j, 0, 0, backgroundWidth, backgroundHeight);
            context.getMatrices().push();
            context.getMatrices().translate(0.0F, 0.0F, 100.0F);
            List<Item> beaconPaymentItems = new ArrayList<>();
            for (RegistryEntry<Item> item : Registries.ITEM.iterateEntries(ItemTags.BEACON_PAYMENT_ITEMS)) {
                beaconPaymentItems.add(item.value());
            }
            context.drawText(textRenderer, Text.literal("Accepts:"), i + 40, j + 113, 14737632, true);
            context.drawItem(new ItemStack(beaconPaymentItems.get(nextItem)), i + 42 + 44, j + 109);

            if (ticker > 40) {
                if (nextItem + 1 >= beaconPaymentItems.size()) nextItem = 0;
                else nextItem++;
                ticker = 0;
            }
            context.getMatrices().pop();
            ci.cancel();
        }
    }

}
