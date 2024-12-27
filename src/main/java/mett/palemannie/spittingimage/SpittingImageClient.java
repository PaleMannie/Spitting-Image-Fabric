package mett.palemannie.spittingimage;

import mett.palemannie.spittingimage.entity.ModEntities;
import mett.palemannie.spittingimage.event.KeyInputHandler;
import mett.palemannie.spittingimage.net.ModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class SpittingImageClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        KeyInputHandler.register();

        EntityRendererRegistry.register(ModEntities.SPIT_PROJECTILE, FlyingItemEntityRenderer::new);
        ModMessages.registerS2CPackets();

    }
}
