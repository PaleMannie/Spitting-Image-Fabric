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

    public static EntityType<SpitEntity> SPIT_PROJECTILE;// = registerSpit("spit_projectile");

    public static void registerEntities(){
        SPIT_PROJECTILE = Registry.register(Registries.ENTITY_TYPE, Identifier.of(SpittingImage.MODID, "spit_projectile"),
                EntityType.Builder.create(SpitEntity::new, SpawnGroup.MISC)
                        .dimensions(0.2f, 0.2f)
                        .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(SpittingImage.MODID, "spit_projectile")))
        );
    }

}