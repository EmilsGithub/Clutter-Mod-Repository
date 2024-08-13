package net.emilsg.clutter.entity.client.render;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.client.layer.ModModelLayers;
import net.emilsg.clutter.entity.client.model.BabyEmperorPenguinModel;
import net.emilsg.clutter.entity.client.model.EmperorPenguinModel;
import net.emilsg.clutter.entity.client.model.parent.ClutterModel;
import net.emilsg.clutter.entity.custom.EmperorPenguinEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class EmperorPenguinRenderer extends MobEntityRenderer<EmperorPenguinEntity, ClutterModel<EmperorPenguinEntity>> {
    public static final Identifier ADULT_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/adult_emperor_penguin_box.png");
    public static final Identifier BABY_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/baby_emperor_penguin_box.png");

    private final BabyEmperorPenguinModel<EmperorPenguinEntity> babyModel;
    private final EmperorPenguinModel<EmperorPenguinEntity> adultModel;


    public EmperorPenguinRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new EmperorPenguinModel<>(ctx.getPart(ModModelLayers.EMPEROR_PENGUIN)), 0.3f);

        this.adultModel = new EmperorPenguinModel<>(ctx.getPart(ModModelLayers.EMPEROR_PENGUIN));
        this.babyModel = new BabyEmperorPenguinModel<>(ctx.getPart(ModModelLayers.BABY_EMPEROR_PENGUIN));
    }

    @Override
    public void render(EmperorPenguinEntity emperorPenguinEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.model = emperorPenguinEntity.isBaby() ? babyModel : adultModel;
        super.render(emperorPenguinEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(EmperorPenguinEntity emperorPenguinEntity) {
        return emperorPenguinEntity.isBaby() ? BABY_TEXTURE : ADULT_TEXTURE;
    }
}
