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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.HashMap;
import java.util.Map;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Unique
    private static final Map<Item, ModelIdentifier> itemModelMap = new HashMap<>();

    @Unique
    private static final ModelIdentifier CROWN = new ModelIdentifier(Identifier.of(Clutter.MOD_ID, "crown_hat"), "inventory");
    @Unique
    private static final ModelIdentifier BEACH_HAT = new ModelIdentifier(Identifier.of(Clutter.MOD_ID, "beach_hat_hat"), "inventory");
    @Unique
    private static final ModelIdentifier TOP_HAT = new ModelIdentifier(Identifier.of(Clutter.MOD_ID, "top_hat_hat"), "inventory");
    @Unique
    private static final ModelIdentifier BERET = new ModelIdentifier(Identifier.of(Clutter.MOD_ID, "beret_hat"), "inventory");
    @Unique
    private static final ModelIdentifier COWBOY_HAT = new ModelIdentifier(Identifier.of(Clutter.MOD_ID, "cowboy_hat_hat"), "inventory");
    @Unique
    private static final ModelIdentifier CAP = new ModelIdentifier(Identifier.of(Clutter.MOD_ID, "cap_hat"), "inventory");
    @Unique
    private static final ModelIdentifier BUTTERFLY_WINGS = new ModelIdentifier(Identifier.of(Clutter.MOD_ID, "butterfly_wings_hat"), "inventory");
    @Unique
    private static final ModelIdentifier PROPELLER_CAP = new ModelIdentifier(Identifier.of(Clutter.MOD_ID, "propeller_cap_hat"), "inventory");
    @Unique
    private static final ModelIdentifier TIARA = new ModelIdentifier(Identifier.of(Clutter.MOD_ID, "tiara_hat"), "inventory");
    @Unique
    private static final ModelIdentifier SILVER_TIARA = new ModelIdentifier(Identifier.of(Clutter.MOD_ID, "silver_tiara_hat"), "inventory");
    @Unique
    private static final ModelIdentifier VIKING_HELMET = new ModelIdentifier(Identifier.of(Clutter.MOD_ID, "viking_helmet_hat"), "inventory");

    static {
        itemModelMap.put(ModItems.CROWN, CROWN);
        itemModelMap.put(ModItems.BEACH_HAT, BEACH_HAT);
        itemModelMap.put(ModItems.TOP_HAT, TOP_HAT);
        itemModelMap.put(ModItems.BERET, BERET);
        itemModelMap.put(ModItems.COWBOY_HAT, COWBOY_HAT);
        itemModelMap.put(ModItems.CAP, CAP);
        itemModelMap.put(ModItems.BUTTERFLY_WINGS, BUTTERFLY_WINGS);
        itemModelMap.put(ModItems.PROPELLER_CAP, PROPELLER_CAP);
        itemModelMap.put(ModItems.TIARA, TIARA);
        itemModelMap.put(ModItems.SILVER_TIARA, SILVER_TIARA);
        itemModelMap.put(ModItems.VIKING_HELMET, VIKING_HELMET);
    }

    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useHatModels(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.getItem() instanceof HatItem && renderMode == ModelTransformationMode.HEAD) {

            ModelIdentifier modelIdentifier = itemModelMap.getOrDefault(stack.getItem(), null);

            if (modelIdentifier != null) {
                return ((ItemRendererAccessor) this).getModels().getModelManager().getModel(modelIdentifier);
            }
        }
        return value;
    }
}
