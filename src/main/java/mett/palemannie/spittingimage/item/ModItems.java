package mett.palemannie.spittingimage.item;

import mett.palemannie.spittingimage.SpittingImage;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item SPIT_PROJECTILE = registerItem("spit_projectile", new Item(new FabricItemSettings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(SpittingImage.MODID, name), item);
    }
    public static void registerModItems() {
        SpittingImage.LOGGER.info("Registering Mod Items for " + SpittingImage.MODID);
    }
}
