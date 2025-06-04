package mett.palemannie.spittingimage.entity.client;


import mett.palemannie.spittingimage.SpittingImage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SpitModel extends EntityModel<EntityRenderState> {

    public static final EntityModelLayer SPIT = new EntityModelLayer(Identifier.of(SpittingImage.MODID, "spit"), "main");
    private static final String MAIN = "main";
    public final ModelPart spit;

    public SpitModel(ModelPart modelPart) {
        super(modelPart);
        this.spit = modelPart.getChild("spit");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData main = modelPartData.addChild("spit", ModelPartBuilder.create().uv(3, 3).cuboid(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                        .uv(3, 1).cuboid(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                        .uv(0, 2).cuboid(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                        .uv(0, 0).cuboid(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                , ModelTransform.NONE);

        return TexturedModelData.of(modelData, 16, 16);
    }
}