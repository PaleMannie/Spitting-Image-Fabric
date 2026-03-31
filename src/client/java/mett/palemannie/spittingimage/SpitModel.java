package mett.palemannie.spittingimage;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class SpitModel extends EntityModel<EntityRenderState> {

    public static final ModelLayerLocation SPIT = new ModelLayerLocation(Identifier.fromNamespaceAndPath(SpittingImage.MODID, "spit"), "main");
    private static final String MAIN = "main";
    public final ModelPart spit;

    public SpitModel(ModelPart modelPart) {
        super(modelPart);
        this.spit = modelPart.getChild("spit");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition main = modelPartData.addOrReplaceChild("spit", CubeListBuilder.create().texOffs(3, 3).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(3, 1).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 2).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                , PartPose.ZERO);

        return LayerDefinition.create(modelData, 16, 16);
    }
}