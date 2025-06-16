package mett.palemannie.spittingimage.entity.client;

import mett.palemannie.spittingimage.SpittingImage;
import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import mett.palemannie.spittingimage.util.SpittingImageConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class SpitRenderer extends EntityRenderer<SpitEntity> {

    public static final Identifier TEXTURE = Identifier.of(SpittingImage.MODID, "textures/entity/spit/spit.png");
    protected SpitModel model;

    public SpitRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new SpitModel(context.getPart(SpitModel.SPIT));
    }

    public void render(SpitEntity spitEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {

        if(SpittingImageConfig.enable3dmodel){
            matrixStack.push();

            matrixStack.translate(0.0F, 0.1f, 0.0F);

            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, spitEntity.prevYaw, spitEntity.getYaw()) + 90.0F));
            matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, spitEntity.prevPitch, spitEntity.getPitch())));

            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
            this.model.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();

            super.render(spitEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
        }
    }

    public Identifier getTexture(SpitEntity SpitEntity) {
        return TEXTURE;
    }
}