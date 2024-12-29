package mett.palemannie.spittingimage.entity;

import mett.palemannie.spittingimage.SpittingImage;
import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.predicate.entity.EntitySubPredicateTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<SpitEntity> SPIT_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(SpittingImage.MODID, "spit_projectile"),
            EntityType.Builder.create(SpitEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1f, 2.5f).build();
}
