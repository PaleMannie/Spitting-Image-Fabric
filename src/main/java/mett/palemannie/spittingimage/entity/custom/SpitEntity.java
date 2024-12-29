package mett.palemannie.spittingimage.entity.custom;

import mett.palemannie.spittingimage.entity.ModEntities;
import mett.palemannie.spittingimage.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class SpitEntity extends ThrownItemEntity {
    public SpitEntity(EntityType<? extends SpitEntity> entityType, World world) {
        super((EntityType<? extends ThrownItemEntity>)entityType, world);
    }

    public SpitEntity(LivingEntity livingEntity, World world) {
        super(ModEntities.SPIT_PROJECTILE, livingEntity, world);
    }

    public SpitEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public SpitEntity(World world, LivingEntity owner, ItemStack stack) {
        this(owner.getX(), owner.getEyeY() - 0.1f, owner.getZ(), world, stack);
        this.setOwner(owner);
    }

    public SpitEntity(double x, double y, double z, World world, ItemStack stack) {
        super(ModEntities.SPIT_PROJECTILE, x, y, z, world, stack);
        this.setItem(stack);
    }

    public SpitEntity(EntityType<? extends ThrownItemEntity> type, LivingEntity owner, World world, ItemStack stack) {
        super(type, owner, world, stack);
    }

    public SpitEntity(EntityType<SpitEntity> entityEntityType, World world) {
        super(entityEntityType, world);

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
            ServerWorld serverWorld = MinecraftClient.getInstance().world.getServer().getOverworld();
            DamageSource damageSource = this.getDamageSources().mobProjectile(this, (LivingEntity) owner);
            entityHitResult.getEntity().damage(serverWorld, damageSource, 1.0F);
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

}
