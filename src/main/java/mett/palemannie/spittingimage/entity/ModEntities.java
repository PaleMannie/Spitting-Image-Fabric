package mett.palemannie.spittingimage.entity;

import mett.palemannie.spittingimage.SpittingImage;
import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<SpitEntity> SPIT_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(SpittingImage.MODID, "spit_projectile"), FabricEntityTypeBuilder.<SpitEntity>create(SpawnGroup.MISC, SpitEntity::new)
                    .dimensions(EntityDimensions.fixed(0.2f, 0.2f)).build());
}
