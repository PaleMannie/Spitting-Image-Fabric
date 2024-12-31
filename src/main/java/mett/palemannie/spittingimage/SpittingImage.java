package mett.palemannie.spittingimage;

import mett.palemannie.spittingimage.item.ModItems;
import mett.palemannie.spittingimage.net.ServerPlayHandler;
import mett.palemannie.spittingimage.net.SpitC2SPacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SpittingImage implements ModInitializer {
	public static final String MODID = "spittingimage";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);


	@Override
	public void onInitialize() {
		LOGGER.info("Spitting Image");

		ModItems.registerModItems();

		SpitC2SPacket.initializePacket();
		ServerPlayNetworking.registerGlobalReceiver(SpitC2SPacket.ID, (payload, context) -> {
			context.server().execute(() -> {
				ServerPlayerEntity serverPlayer = context.player();
				ServerPlayHandler.handleSpitting(serverPlayer);
			});
		});

	}
}