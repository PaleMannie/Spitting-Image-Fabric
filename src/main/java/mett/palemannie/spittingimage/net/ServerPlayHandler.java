package mett.palemannie.spittingimage.net;

import mett.palemannie.spittingimage.entity.ModEntities;
import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import mett.palemannie.spittingimage.util.SpittingImageConfig;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerPlayHandler {

    private static final Map<UUID, Integer> spitCooldowns = new HashMap<>();

    public static void handleSpitting(ServerPlayerEntity player){

        int currentTick = player.getEntityWorld().getServer().getTicks();
        int cooldown = SpittingImageConfig.spitCooldown; // deine konfigurierbare Zahl

        int lastUsed = spitCooldowns.getOrDefault(player.getUuid(), -cooldown - 1);

        if (currentTick - lastUsed < cooldown) {

            player.sendMessage(Text.translatable("spittingimage.spitcooldown").formatted(Formatting.RED), true);
            return;
        }

        // Cooldown aktualisieren
        spitCooldowns.put(player.getUuid(), currentTick);

        ///Entity
        World world = player.getEntityWorld();

        if (world instanceof ServerWorld serverWorld) {
            SpitEntity spitEntity = new SpitEntity(ModEntities.SPIT, serverWorld);
            spitEntity.setOwner(player);
            spitEntity.setPosition(player.getX(), player.getEyeY() - 0.15f, player.getZ());
            float velocity = 0.45f + world.random.nextFloat() * 0.1f;
            spitEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0f, velocity, 1f);
            serverWorld.spawnEntity(spitEntity);
        }

        ///Sound
        World lvl = player.getEntityWorld();
        float r = 0.8f + lvl.random.nextFloat() * 0.3f;
        lvl.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_LLAMA_SPIT, SoundCategory.BLOCKS, 1f, r);
    }
}
