package mett.palemannie.spittingimage.entity.custom;

import mett.palemannie.spittingimage.entity.ModEntities;
import mett.palemannie.spittingimage.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class SpitEntity extends ThrownItemEntity {
    public SpitEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    public SpitEntity(LivingEntity livingEntity, World world) {
        super(ModEntities.SPIT_PROJECTILE, livingEntity, world);
    }
    public SpitEntity(World pLevel, LivingEntity livingEntity, ItemStack stack) {
        this(livingEntity.getX(), livingEntity.getEyeY() - 0.10000000149011612, livingEntity.getZ(), pLevel, stack);
        this.setOwner(livingEntity);
    }
    public SpitEntity(double x, double y, double z, World pLevel, ItemStack stack) {
        super(ModEntities.SPIT_PROJECTILE, x, y, z, pLevel);
        this.setItem(stack);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isInsideWaterOrBubbleColumn()) this.discard();
        else if (this.getWorld().getStatesInBox(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) this.discard();

        if (this.age % 7 == 0) {
            getWorld().addParticle(ParticleTypes.SPIT, this.getX(), this.getY() + 0.2, this.getZ(), 0d, 0d, 0d);
        }
        getWorld().addParticle(ParticleTypes.SPLASH, this.getX(), this.getY() + 0.2, this.getZ(), 0d, 0d, 0d);
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity owner = getOwner();

        if (owner instanceof LivingEntity) {
            DamageSource damageSource = this.getDamageSources().mobProjectile(this, (LivingEntity) owner);
            entityHitResult.getEntity().damage(damageSource, 1.0F);
        }
    }


    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SPIT_PROJECTILE;
    }

    /*@Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }*/
}
