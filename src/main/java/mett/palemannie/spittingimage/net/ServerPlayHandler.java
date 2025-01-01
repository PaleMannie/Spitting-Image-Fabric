package mett.palemannie.spittingimage.net;

import mett.palemannie.spittingimage.entity.ModEntities;
import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ServerPlayHandler {

    public static void handleSpitting(ServerPlayerEntity player){

        World world = player.getWorld();

        if (world instanceof ServerWorld serverWorld) {
            SpitEntity spitEntity = new SpitEntity(ModEntities.SPIT_PROJECTILE, serverWorld);
            spitEntity.setOwner(player);
            spitEntity.setPosition(player.getX(), player.getEyeY() - 0.15f, player.getZ());
            float velocity = 0.45f + world.random.nextFloat() * 0.1f;
            spitEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0f, velocity, 1f);
            serverWorld.spawnEntity(spitEntity);
        }

        ///Sound
        World lvl = player.getWorld();
        float r = 0.8f + lvl.random.nextFloat() * 0.3f;
        lvl.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_LLAMA_SPIT, SoundCategory.BLOCKS, 1f, r);

        ///Particle
        Vec3d vec3 = player.getRotationVec(1f);
        Vec3d MousePos = player.getEyePos();

        double x = player.getX() + vec3.x/4;
        double y = MousePos.y + vec3.y/4;
        double z = player.getZ() + vec3.z/4;

        if(lvl instanceof ServerWorld slevel) {
            slevel.spawnParticles(ParticleTypes.SPIT, x, y, z, 3, 0d, 0d, 0d,0.15d);
            slevel.addParticle(ParticleTypes.LARGE_SMOKE, player.getX(), player.getEyeY()-0.15d, player.getZ(), vec3.x, vec3.y, vec3.z);
        }
    }
}
