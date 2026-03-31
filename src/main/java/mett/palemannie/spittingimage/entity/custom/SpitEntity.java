package mett.palemannie.spittingimage.entity.custom;

import mett.palemannie.spittingimage.util.ModDamageTypes;
import mett.palemannie.spittingimage.util.SpittingImageConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.painting.Painting;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SpitEntity extends Projectile {

    public SpitEntity(EntityType<SpitEntity> entityEntityType, Level world) {
        super(entityEntityType, world);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) { }

    @Override
    public void tick() {
        super.tick();

        Vec3 vec3d = this.getDeltaMovement();
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        this.hitTargetOrDeflectSelf(hitResult);

        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.updateRotation();

        if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {

            this.discard();
        } else if (this.isInWater()) {

            this.discard();
        } else {

            this.setDeltaMovement(vec3d.scale(0.99f));
            this.applyGravity();
            this.setPos(d, e, f);
        }

        if (this.tickCount % 9 == 0) {

            level().addParticle(ParticleTypes.SPIT, this.getX(), this.getY() + 0.2, this.getZ(), 0d, 0d, 0d);
        }
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {

        Entity owner = this.getOwner();
        Entity target = entityHitResult.getEntity();
        Level world = this.level();

        if (owner instanceof Player) {

            if(world instanceof ServerLevel serverWorld) {

                if(target instanceof LivingEntity) {

                    float damageAmount = SpittingImageConfig.spitdamage;

                    DamageSource source = world.damageSources().source(ModDamageTypes.SPIT_DAMAGE, null, null);
                    DamageSource source2 = world.damageSources().source(DamageTypes.PLAYER_ATTACK, this.getOwner(), this.getOwner());

                    if (!(target == this.getOwner())) {

                        entityHitResult.getEntity().hurtServer(serverWorld, source2, 0.000000000001f);
                    }
                    entityHitResult.getEntity().hurtServer(serverWorld, source, damageAmount);
                }
                else if(target instanceof ItemFrame){

                    if(!((ItemFrame) target).getItem().isEmpty()){

                        target.level().addFreshEntity(new ItemEntity(target.level(), target.getX(), target.getY(), target.getZ(),
                                ((ItemFrame) target).getItem().copy()));
                        this.discard();
                        ((ItemFrame) target).setItem(ItemStack.EMPTY);
                        serverWorld.playSound(target, target, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.AMBIENT, 1f, 1f);

                    }
                    else {

                        this.discard();
                        target.kill(serverWorld);
                        if(target instanceof GlowItemFrame) {

                            target.spawnAtLocation(serverWorld,Items.GLOW_ITEM_FRAME);
                            world.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.GLOW_ITEM_FRAME_BREAK, SoundSource.AMBIENT, 1f,1f);
                        }
                        else {

                            target.spawnAtLocation(serverWorld,Items.ITEM_FRAME);
                            world.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.ITEM_FRAME_BREAK, SoundSource.AMBIENT, 1f,1f);
                        }
                    }
                }
                else if(target instanceof Painting){

                    this.discard();
                    target.spawnAtLocation(serverWorld, Items.PAINTING);
                    target.kill(serverWorld);
                    world.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.PAINTING_BREAK, SoundSource.AMBIENT, 1f,1f);
                }
            }
        }
        this.discard();
    }

    protected double getDefaultGravity() {
        return 0.05;
    }

    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }

    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        double d = packet.getMovement().x;
        double e = packet.getMovement().y;
        double f = packet.getMovement().z;

        for(int i = 0; i < 3; ++i) {
            double g = 0.4 + 0.1 * (double)i;
            this.level().addParticle(ParticleTypes.SPIT, this.getX(), this.getY(), this.getZ(), d * g, e, f * g);
        }

        this.setDeltaMovement(d, e, f);
    }
}