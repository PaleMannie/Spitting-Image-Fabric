package mett.palemannie.spittingimage.entity.client;

import mett.palemannie.spittingimage.SpittingImage;
import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
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

    private static final Identifier TEXTURE = Identifier.of(SpittingImage.MODID,"textures/entity/spit/spit.png");
    protected SpitModel model;

    public SpitRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new SpitModel(context.getPart(SpitModel.SPIT));
    }

    public void render(SpitEntity spitEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        matrixStack.push();

        matrixStack.translate(0.0F, 0.1f, 0.0F);

        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(g, spitEntity.prevYaw, spitEntity.getYaw()) - 90.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(g, spitEntity.prevPitch, spitEntity.getPitch())));

        this.model.setAngles(spitEntity, g, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
        matrixStack.pop();

        super.render(spitEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public Identifier getTexture(SpitEntity SpitEntity) {
        return TEXTURE;
    }
}
