package mett.palemannie.spittingimage.entity.custom;

import mett.palemannie.spittingimage.util.ModDamageTypes;
import mett.palemannie.spittingimage.util.SpittingImageConfig;
import net.minecraft.block.AbstractBlock;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
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

        Entity target = entityHitResult.getEntity();
        Entity owner = this.getOwner();
        World world = this.getWorld();

        if (owner instanceof PlayerEntity) {

            if(world instanceof ServerWorld serverWorld && target instanceof LivingEntity) {

                float damageAmount = SpittingImageConfig.spitdamage;

                DamageSource source = world.getDamageSources().create(ModDamageTypes.SPIT_DAMAGE, null, null);
                DamageSource source2 = world.getDamageSources().create(DamageTypes.PLAYER_ATTACK, this.getOwner(), this.getOwner());

                if (!(target == this.getOwner())) {

                    entityHitResult.getEntity().damage(serverWorld, source2, 0.000000000001f);
                }
                entityHitResult.getEntity().damage(serverWorld, source, damageAmount);
            }
        } if (target instanceof ItemFrameEntity frame && world instanceof ServerWorld serverWorld) {

            ItemStack held = frame.getHeldItemStack();

            if (!held.isEmpty()) {

                ItemStack drop = held.copy();
                frame.setHeldItemStack(ItemStack.EMPTY, false);
                world.spawnEntity(new ItemEntity(world, frame.getX(), frame.getY(), frame.getZ(), drop));
                this.discard();
            } else {

                Item frameItem = frame instanceof GlowItemFrameEntity ? Items.GLOW_ITEM_FRAME : Items.ITEM_FRAME;
                world.spawnEntity(new ItemEntity(world, frame.getX(), frame.getY(), frame.getZ(), new ItemStack(frameItem)));
                frame.kill(serverWorld);
                this.discard();
            }
        } else if(target instanceof PaintingEntity painting && world instanceof ServerWorld serverWorld){

            painting.kill(serverWorld);
            world.spawnEntity(new ItemEntity(world, painting.getX(), painting.getY(), painting.getZ(), Items.PAINTING.getDefaultStack()));
            this.discard();
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