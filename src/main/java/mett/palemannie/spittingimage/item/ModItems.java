package mett.palemannie.spittingimage.item;

import mett.palemannie.spittingimage.SpittingImage;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {

    public static final Item SPIT_PROJECTILE = registerItem("spit_projectile", new Item(new FabricItemSettings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(SpittingImage.MODID, name), item);
    }
    public static void registerModItems() {
        SpittingImage.LOGGER.info("Registering Mod Items for " + SpittingImage.MODID);
    }
}
