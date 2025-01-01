package mett.palemannie.spittingimage;

import mett.palemannie.spittingimage.entity.ModEntities;
import mett.palemannie.spittingimage.item.ModItems;
import mett.palemannie.spittingimage.net.ModMessages;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpittingImage implements ModInitializer {
	public static final String MODID = "spittingimage";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		LOGGER.info("Spitting Image");

		ModEntities.registerEntities();
		ModItems.registerModItems();
		ModMessages.registerC2SPackets();

	}
}