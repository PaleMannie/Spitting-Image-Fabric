package mett.palemannie.spittingimage.entity.client;

import mett.palemannie.spittingimage.SpittingImage;
import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.LlamaSpitEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class SpitRenderer extends EntityRenderer<SpitEntity, LlamaSpitEntityRenderState> {
    private static final Identifier TEXTURE = Identifier.of(SpittingImage.MODID, "textures/entity/spit/spit.png");
    private final SpitModel model;

    public SpitRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new SpitModel(context.getPart(SpitModel.SPIT));
    }

    public void render(LlamaSpitEntityRenderState llamaSpitEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        matrixStack.push();

        matrixStack.translate(0.0F, 0.1F, 0.0F);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(llamaSpitEntityRenderState.yaw - 90f));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(llamaSpitEntityRenderState.pitch));

        this.model.setAngles(llamaSpitEntityRenderState);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);

        matrixStack.pop();

        super.render(llamaSpitEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }

    public LlamaSpitEntityRenderState createRenderState() {
        return new LlamaSpitEntityRenderState();
    }

    public void updateRenderState(SpitEntity spitEntity, LlamaSpitEntityRenderState spitEntityRenderState, float f) {

        super.updateRenderState(spitEntity, spitEntityRenderState, f);
        spitEntityRenderState.pitch = spitEntity.getLerpedPitch(f);
        spitEntityRenderState.yaw = spitEntity.getLerpedYaw(f);
    }
}