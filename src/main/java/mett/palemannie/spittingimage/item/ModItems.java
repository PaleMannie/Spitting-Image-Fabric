package mett.palemannie.spittingimage.item;

import mett.palemannie.spittingimage.SpittingImage;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item SPIT_PROJECTILE = registerItem("spit_projectile", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(SpittingImage.MODID, "spit_projectile")))));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(SpittingImage.MODID, name), item);
    }
    public static void registerModItems() {
        SpittingImage.LOGGER.info("Registering Mod Items for " + SpittingImage.MODID);
    }
}
