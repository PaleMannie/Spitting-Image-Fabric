package mett.palemannie.spittingimage;

import mett.palemannie.spittingimage.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

public class SpittingImageClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        KeyInputHandler.register();
        EntityRendererRegistry.register(ModEntities.SPIT, SpitRenderer::new);
        ModelLayerRegistry.registerModelLayer(SpitModel.SPIT, SpitModel::getTexturedModelData);
    }
}
