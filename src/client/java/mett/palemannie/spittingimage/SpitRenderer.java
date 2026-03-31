package mett.palemannie.spittingimage;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import mett.palemannie.spittingimage.util.SpittingImageConfig;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LlamaSpitRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class SpitRenderer extends EntityRenderer<SpitEntity, LlamaSpitRenderState> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(SpittingImage.MODID, "textures/entity/spit/spit.png");
    private final SpitModel model;

    public SpitRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SpitModel(context.bakeLayer(SpitModel.SPIT));
    }

    @Override
    public void submit(LlamaSpitRenderState renderState, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {

        if(SpittingImageConfig.enable3dmodel) {

            matrices.pushPose();
            matrices.translate(0.0F, 0.15F, 0.0F);
            matrices.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0F));
            matrices.mulPose(Axis.ZP.rotationDegrees(renderState.xRot));
            queue.submitModel(this.model, renderState, matrices, this.model.renderType(TEXTURE), renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.outlineColor, (ModelFeatureRenderer.CrumblingOverlay)null);
            matrices.popPose();

        }
        super.submit(renderState, matrices, queue, cameraState);
    }

    public LlamaSpitRenderState createRenderState() {
        return new LlamaSpitRenderState();
    }

    public void updateRenderState(SpitEntity spitEntity, LlamaSpitRenderState spitEntityRenderState, float f) {

        super.extractRenderState(spitEntity, spitEntityRenderState, f);
        spitEntityRenderState.xRot = spitEntity.getXRot(f);
        spitEntityRenderState.yRot = spitEntity.getYRot(f);
    }
}