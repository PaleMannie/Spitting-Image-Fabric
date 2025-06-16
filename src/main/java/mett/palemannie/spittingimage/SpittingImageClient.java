package mett.palemannie.spittingimage;

import mett.palemannie.spittingimage.entity.ModEntities;
import mett.palemannie.spittingimage.entity.client.SpitModel;
import mett.palemannie.spittingimage.entity.client.SpitRenderer;
import mett.palemannie.spittingimage.event.KeyInputHandler;
import mett.palemannie.spittingimage.net.ModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class SpittingImageClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        KeyInputHandler.register();

        EntityRendererRegistry.register(ModEntities.SPIT, SpitRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SpitModel.SPIT, SpitModel::getTexturedModelData);
        ModMessages.registerS2CPackets();

    }
}
