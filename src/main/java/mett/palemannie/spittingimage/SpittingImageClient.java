package mett.palemannie.spittingimage;

import mett.palemannie.spittingimage.entity.ModEntities;
import mett.palemannie.spittingimage.entity.client.SpitModel;
import mett.palemannie.spittingimage.entity.client.SpitRenderer;
import mett.palemannie.spittingimage.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class SpittingImageClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        KeyInputHandler.register();
        EntityRendererRegistry.register(ModEntities.SPIT, SpitRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SpitModel.SPIT, SpitModel::getTexturedModelData);

    }
}
