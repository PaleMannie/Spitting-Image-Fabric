package mett.palemannie.spittingimage.entity;

import mett.palemannie.spittingimage.SpittingImage;
import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {

    public static EntityType<SpitEntity> SPIT_PROJECTILE;

    public static void registerEntities() {
        SPIT_PROJECTILE = Registry.register(Registry.ENTITY_TYPE,
                new Identifier(SpittingImage.MODID, "spit_projectile"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, SpitEntity::new)
                        .dimensions(EntityDimensions.fixed(0.2f, 0.2f)).build());
    }
}
