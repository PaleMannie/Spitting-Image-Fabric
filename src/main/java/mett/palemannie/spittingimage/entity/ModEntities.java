package mett.palemannie.spittingimage.entity;

import mett.palemannie.spittingimage.SpittingImage;
import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static Identifier id = Identifier.of(SpittingImage.MODID, "spit_projectile");
    public static RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, id);

    public static final EntityType<SpitEntity> SPIT_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(SpittingImage.MODID, "spit_projectile"),
            EntityType.Builder.create(SpitEntity::new, SpawnGroup.MISC)
                    .dimensions(0.2f, 0.2f).build(key);

    /*public static final EntityType<SpitEntity> SPIT_PROJECTILE = registerSpit("spit_projectile");

    private static final RegistryKey<Item> SPIT_REGISTRY_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(SpittingImage.MODID, "spit_projectile"));

    private static EntityType<SpitEntity> registerSpit(final String id) {
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(SpittingImage.MODID, id),
                EntityType.Builder.<SpitEntity>create(SpitEntity::new, SpawnGroup.MISC)
                        .dimensions(0.2f, 0.2f)
                        .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(SpittingImage.MODID, id)))
        );
    }*/

}