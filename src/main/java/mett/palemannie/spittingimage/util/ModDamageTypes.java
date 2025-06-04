package mett.palemannie.spittingimage.util;

import mett.palemannie.spittingimage.SpittingImage;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModDamageTypes {

    public static final RegistryKey<DamageType> SPIT_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(SpittingImage.MODID, "spit_damage"));


}