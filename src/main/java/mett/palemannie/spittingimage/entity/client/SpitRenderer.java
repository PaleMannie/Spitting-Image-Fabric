package mett.palemannie.spittingimage.entity.client;

import mett.palemannie.spittingimage.SpittingImage;
import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import mett.palemannie.spittingimage.util.SpittingImageConfig;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.LlamaSpitEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
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

    @Override
    public void render(LlamaSpitEntityRenderState renderState, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {

        if(SpittingImageConfig.enable3dmodel) {

            matrices.push();
            matrices.translate(0.0F, 0.15F, 0.0F);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(renderState.yaw - 90.0F));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(renderState.pitch));
            queue.submitModel(this.model, renderState, matrices, this.model.getLayer(TEXTURE), renderState.light, OverlayTexture.DEFAULT_UV, renderState.outlineColor, (ModelCommandRenderer.CrumblingOverlayCommand)null);
            matrices.pop();

        }
        super.render(renderState, matrices, queue, cameraState);
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