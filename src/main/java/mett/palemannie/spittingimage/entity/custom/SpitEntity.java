package mett.palemannie.spittingimage.entity.custom;

import mett.palemannie.spittingimage.util.ModDamageTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.GlowItemFrameEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
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
    protected void initDataTracker() {

    }

    @Override
    public void tick() {
        super.tick();

        Vec3d vec3d = this.getVelocity();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.onCollision(hitResult);

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
            if (!this.hasNoGravity()) {

                this.setVelocity(this.getVelocity().add(0f, -0.05f, 0f));
            }

            this.setPosition(d, e, f);
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        World world = entityHitResult.getEntity().getWorld();
        Entity entity = this.getOwner();

        if(entity instanceof PlayerEntity player){

            entity = entityHitResult.getEntity();

            if(entity instanceof LivingEntity livingEntity && (livingEntity.hurtTime == 0 || (player.isCreative() && livingEntity.hurtTime == 0 ))){

                DamageSource damageSource = new DamageSource(
                        world.getRegistryManager()
                                .get(RegistryKeys.DAMAGE_TYPE)
                                .entryOf(ModDamageTypes.SPIT_DAMAGE));
                entityHitResult.getEntity().damage(damageSource, 1f);

                    Vec3d knockback = this.getVelocity();
                    livingEntity.takeKnockback(1/3d,
                            -knockback.x,
                            -knockback.z);

            }
            else if(entity instanceof ItemFrameEntity itemFrame && !itemFrame.getEntityWorld().isClient()){

                if(!itemFrame.getHeldItemStack().isEmpty()){

                    itemFrame.getWorld().spawnEntity(new ItemEntity(itemFrame.getWorld(),
                        itemFrame.getX(), itemFrame.getY(), itemFrame.getZ(),
                        itemFrame.getHeldItemStack().copy()));
                    this.discard();
                    itemFrame.setHeldItemStack(ItemStack.EMPTY);
                }
                else {

                    this.discard();
                    if(itemFrame instanceof GlowItemFrameEntity e){

                        e.dropItem(Items.GLOW_ITEM_FRAME);
                    } else { itemFrame.dropItem(Items.ITEM_FRAME); }

                    itemFrame.kill();
                }
            }

            else if(entity instanceof PaintingEntity painting){

                this.discard();
                painting.dropItem(Items.PAINTING);
                painting.kill();
            }

        }

        this.discard();
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.discard();
        }
    }

    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);

        double d = packet.getVelocityX();
        double e = packet.getVelocityY();
        double f = packet.getVelocityZ();

        for(int i = 0; i < 3; ++i) {
            double g = 0.4 + 0.1 * (double)i;
            this.getWorld().addParticle(ParticleTypes.SPIT, this.getX(), this.getY(), this.getZ(), d * g, e, f * g);
        }

        this.setVelocity(d, e, f);
    }
}
