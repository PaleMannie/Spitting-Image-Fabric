package mett.palemannie.spittingimage;

import eu.midnightdust.lib.config.MidnightConfig;
import mett.palemannie.spittingimage.entity.ModEntities;
import mett.palemannie.spittingimage.net.ServerPlayHandler;
import mett.palemannie.spittingimage.net.SpitC2SPacket;
import mett.palemannie.spittingimage.util.SpittingImageConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SpittingImage implements ModInitializer {
	public static final String MODID = "spittingimage";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);


	@Override
	public void onInitialize() {
		LOGGER.info("Spitting Image");

		ModEntities.registerEntities();
		MidnightConfig.init(SpittingImage.MODID, SpittingImageConfig.class);

		SpitC2SPacket.initializePacket();
		ServerPlayNetworking.registerGlobalReceiver(SpitC2SPacket.ID, (payload, context) -> {
			context.server().execute(() -> {
				ServerPlayer serverPlayer = context.player();
				ServerPlayHandler.handleSpitting(serverPlayer);
			});
		});

	}
}