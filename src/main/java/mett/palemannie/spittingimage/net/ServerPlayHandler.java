package mett.palemannie.spittingimage.net;

import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import mett.palemannie.spittingimage.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class ServerPlayHandler {

    public static void handleSpitting(ServerPlayerEntity player){

        ServerWorld sevel = player.getServerWorld();
        Random rdm = new Random();

        ///Entity
        SpitEntity spit = new SpitEntity(player, sevel, new ItemStack(ModItems.SPIT_PROJECTILE));
        float re = (float)rdm.nextInt(4500,5000)/10000;
        spit.setVelocity(player, player.getPitch(), player.getYaw(), 0f, re,1f);
        sevel.spawnEntity(spit);

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
