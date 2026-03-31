package mett.palemannie.spittingimage.net;

import mett.palemannie.spittingimage.entity.ModEntities;
import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import mett.palemannie.spittingimage.util.SpittingImageConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerPlayHandler {

    private static final Map<UUID, Integer> spitCooldowns = new HashMap<>();

    public static void handleSpitting(ServerPlayer player){

        int currentTick = player.level().getServer().getTickCount();
        int cooldown = SpittingImageConfig.spitCooldown; // deine konfigurierbare Zahl

        int lastUsed = spitCooldowns.getOrDefault(player.getUUID(), -cooldown - 1);

        if (currentTick - lastUsed < cooldown) {

            player.sendSystemMessage(Component.translatable("spittingimage.spitcooldown").withStyle(ChatFormatting.RED), true);
            return;
        }

        // Cooldown aktualisieren
        spitCooldowns.put(player.getUUID(), currentTick);

        ///Entity
        Level world = player.level();

        if (world instanceof ServerLevel serverWorld) {
            SpitEntity spitEntity = new SpitEntity(ModEntities.SPIT, serverWorld);
            spitEntity.setOwner(player);
            spitEntity.setPos(player.getX(), player.getEyeY() - 0.15f, player.getZ());
            float velocity = 0.45f + world.getRandom().nextFloat() * 0.1f;
            spitEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, velocity, 1f);
            serverWorld.addFreshEntity(spitEntity);
        }

        ///Sound
        Level lvl = player.level();
        float r = 0.8f + lvl.getRandom().nextFloat() * 0.3f;
        lvl.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LLAMA_SPIT, SoundSource.BLOCKS, 1f, r);
    }
}
