package mett.palemannie.spittingimage.entity.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpitEntity extends ProjectileEntity {
    public SpitEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    public void tick() {
        super.tick();

        Vec3d vec3d = this.getVelocity();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.hitOrDeflect(hitResult);

        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.updateRotation();

        if (this.getWorld().getStatesInBox(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {

            this.discard();
        } else if (this.isInsideWaterOrBubbleColumn()) {

            this.discard();
        } else {

            this.setVelocity(vec3d.multiply(0.99f));
            this.applyGravity();
            this.setPosition(d, e, f);
        }

        if (this.age % 9 == 0) {

            getWorld().addParticle(ParticleTypes.SPIT, this.getX(), this.getY() + 0.2, this.getZ(), 0d, 0d, 0d);
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity owner = getOwner();

        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity livingEntity) {
            entity = entityHitResult.getEntity();
            DamageSource damageSource = this.getDamageSources().spit(this, livingEntity);
            if (entity.damage(damageSource, 1.0F)) {
                World var6 = this.getWorld();
                if (var6 instanceof ServerWorld) {
                    ServerWorld serverWorld = (ServerWorld)var6;
                    EnchantmentHelper.onTargetDamaged(serverWorld, entity, damageSource);
                }
            }
        }
    }

    @Override
    protected double getGravity() {
        return 0.05d;
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.discard();
        }
    }
}
