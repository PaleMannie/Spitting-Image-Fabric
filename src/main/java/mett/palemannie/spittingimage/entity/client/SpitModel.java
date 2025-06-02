package mett.palemannie.spittingimage.entity.client;

import mett.palemannie.spittingimage.SpittingImage;
import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SpitModel extends EntityModel<SpitEntity> {

	public static final EntityModelLayer SPIT = new EntityModelLayer(Identifier.of(SpittingImage.MODID, "spit"), "main");
	private final ModelPart spit;
	public SpitModel(ModelPart root) { this.spit = root.getChild("spit"); }

	public static TexturedModelData getTexturedModelData() {

		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData bb_main = modelPartData.addChild("spit", ModelPartBuilder.create()
		.uv(0, 0).cuboid(0.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(4, 0).cuboid(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 4).cuboid(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 2).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.NONE);

		return TexturedModelData.of(modelData, 16, 16);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		spit.render(matrices, vertices, light, color);
	}

	@Override
	public void setAngles(SpitEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}
}
