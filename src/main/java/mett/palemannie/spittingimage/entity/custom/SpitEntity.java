package mett.palemannie.spittingimage.entity.custom;

import mett.palemannie.spittingimage.util.ModDamageTypes;
import mett.palemannie.spittingimage.util.SpittingImageConfig;
import net.minecraft.block.AbstractBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.decoration.GlowItemFrameEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpitEntity extends ProjectileEntity {

    public SpitEntity(EntityType<SpitEntity> entityEntityType, World world) {
        super(entityEntityType, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) { }

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
        } else if (this.isTouchingWater()) {

            this.discard();
        } else {

            this.setVelocity(vec3d.multiply(0.99f));
            this.applyGravity();
            this.setPosition(d, e, f);
        }

        if (this.age % 9 == 0) {

            getWorld().addParticleClient(ParticleTypes.SPIT, this.getX(), this.getY() + 0.2, this.getZ(), 0d, 0d, 0d);
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {

        Entity owner = this.getOwner();
        Entity target = entityHitResult.getEntity();
        World world = this.getWorld();

        if (owner instanceof PlayerEntity) {

            if(world instanceof ServerWorld serverWorld) {

                if(target instanceof LivingEntity) {

                    float damageAmount = SpittingImageConfig.spitdamage;

                    DamageSource source = world.getDamageSources().create(ModDamageTypes.SPIT_DAMAGE, null, null);
                    DamageSource source2 = world.getDamageSources().create(DamageTypes.PLAYER_ATTACK, this.getOwner(), this.getOwner());

                    if (!(target == this.getOwner())) {

                        entityHitResult.getEntity().damage(serverWorld, source2, 0.000000000001f);
                    }
                    entityHitResult.getEntity().damage(serverWorld, source, damageAmount);
                }
                else if(target instanceof ItemFrameEntity){

                    if(!((ItemFrameEntity) target).getHeldItemStack().isEmpty()){

                        target.getWorld().spawnEntity(new ItemEntity(target.getWorld(), target.getX(), target.getY(), target.getZ(),
                                ((ItemFrameEntity) target).getHeldItemStack().copy()));
                        this.discard();
                        ((ItemFrameEntity) target).setHeldItemStack(ItemStack.EMPTY);
                        serverWorld.playSoundFromEntity(target, target, SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.AMBIENT, 1f, 1f);

                    }
                    else {

                        this.discard();
                        target.kill(serverWorld);
                        if(target instanceof GlowItemFrameEntity) {

                            target.dropItem(serverWorld,Items.GLOW_ITEM_FRAME);
                            world.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.ENTITY_GLOW_ITEM_FRAME_BREAK, SoundCategory.AMBIENT, 1f,1f);
                        }
                        else {

                            target.dropItem(serverWorld,Items.ITEM_FRAME);
                            world.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.ENTITY_ITEM_FRAME_BREAK, SoundCategory.AMBIENT, 1f,1f);
                        }
                    }
                }
                else if(target instanceof PaintingEntity){

                    this.discard();
                    target.dropItem(serverWorld, Items.PAINTING);
                    target.kill(serverWorld);
                    world.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.ENTITY_PAINTING_BREAK, SoundCategory.AMBIENT, 1f,1f);
                }
            }
        }
        this.discard();
    }

    protected double getGravity() {
        return 0.05;
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
            this.getWorld().addParticleClient(ParticleTypes.SPIT, this.getX(), this.getY(), this.getZ(), d * g, e, f * g);
        }

        this.setVelocity(d, e, f);
    }
}