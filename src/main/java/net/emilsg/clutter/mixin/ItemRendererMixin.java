package net.emilsg.clutter.mixin;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.item.custom.HatItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    private final ModelIdentifier CROWN = new ModelIdentifier(Clutter.MOD_ID, "crown_hat", "inventory");
    private final ModelIdentifier BEACH_HAT = new ModelIdentifier(Clutter.MOD_ID, "beach_hat_hat", "inventory");
    private final ModelIdentifier TOP_HAT = new ModelIdentifier(Clutter.MOD_ID, "top_hat_hat", "inventory");
    private final ModelIdentifier BERET = new ModelIdentifier(Clutter.MOD_ID, "beret_hat", "inventory");
    private final ModelIdentifier COWBOY_HAT = new ModelIdentifier(Clutter.MOD_ID, "cowboy_hat_hat", "inventory");
    private final ModelIdentifier CAP = new ModelIdentifier(Clutter.MOD_ID, "cap_hat", "inventory");
    private final ModelIdentifier BUTTERFLY_WINGS = new ModelIdentifier(Clutter.MOD_ID, "butterfly_wings_hat", "inventory");
    private final ModelIdentifier PROPELLER_CAP = new ModelIdentifier(Clutter.MOD_ID, "propeller_cap_hat", "inventory");
    private final ModelIdentifier TIARA = new ModelIdentifier(Clutter.MOD_ID, "tiara_hat", "inventory");
    private final ModelIdentifier VIKING_HELMET = new ModelIdentifier(Clutter.MOD_ID, "viking_helmet_hat", "inventory");

    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useHatModels(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.getItem() instanceof HatItem && renderMode == ModelTransformationMode.HEAD) {
            ModelIdentifier modelIdentifier = null;

            if (stack.getItem().equals(ModItems.CROWN)) {
                modelIdentifier = CROWN;
            } else if (stack.getItem().equals(ModItems.BEACH_HAT)) {
                modelIdentifier = BEACH_HAT;
            } else if (stack.getItem().equals(ModItems.TOP_HAT)) {
                modelIdentifier = TOP_HAT;
            } else if (stack.getItem().equals(ModItems.BERET)) {
                modelIdentifier = BERET;
            } else if (stack.getItem().equals(ModItems.COWBOY_HAT)) {
                modelIdentifier = COWBOY_HAT;
            } else if (stack.getItem().equals(ModItems.CAP)) {
                modelIdentifier = CAP;
            } else if (stack.getItem().equals(ModItems.BUTTERFLY_WINGS)) {
                modelIdentifier = BUTTERFLY_WINGS;
            } else if (stack.getItem().equals(ModItems.PROPELLER_CAP)) {
                modelIdentifier = PROPELLER_CAP;
            } else if (stack.getItem().equals(ModItems.TIARA)) {
                modelIdentifier = TIARA;
            } else if (stack.getItem().equals(ModItems.VIKING_HELMET)) {
                modelIdentifier = VIKING_HELMET;
            }



            if (modelIdentifier != null) {
                return ((ItemRendererAccessor) this).getModels().getModelManager().getModel(modelIdentifier);
            }
        }
        return value;
    }
}
