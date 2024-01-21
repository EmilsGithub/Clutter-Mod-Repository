package net.emilsg.clutter.mixin;

import net.emilsg.clutter.Clutter;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;
@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void addModel(ModelIdentifier modelId);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 3, shift = At.Shift.AFTER))
    public void addCustomModels(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels, Map<Identifier, List<ModelLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
        this.addModel(new ModelIdentifier(Clutter.MOD_ID, "crown_hat", "inventory"));
        this.addModel(new ModelIdentifier(Clutter.MOD_ID, "beach_hat_hat", "inventory"));
        this.addModel(new ModelIdentifier(Clutter.MOD_ID, "top_hat_hat", "inventory"));
        this.addModel(new ModelIdentifier(Clutter.MOD_ID, "beret_hat", "inventory"));
        this.addModel(new ModelIdentifier(Clutter.MOD_ID, "cowboy_hat_hat", "inventory"));
        this.addModel(new ModelIdentifier(Clutter.MOD_ID, "cap_hat", "inventory"));
        this.addModel(new ModelIdentifier(Clutter.MOD_ID, "butterfly_wings_hat", "inventory"));
        this.addModel(new ModelIdentifier(Clutter.MOD_ID, "propeller_cap_hat", "inventory"));
        this.addModel(new ModelIdentifier(Clutter.MOD_ID, "tiara_hat", "inventory"));
        this.addModel(new ModelIdentifier(Clutter.MOD_ID, "silver_tiara_hat", "inventory"));
        this.addModel(new ModelIdentifier(Clutter.MOD_ID, "viking_helmet_hat", "inventory"));

    }
}
