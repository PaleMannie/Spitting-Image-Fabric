package mett.palemannie.spittingimage;

import mett.palemannie.spittingimage.net.SpitC2SPacket;
import mett.palemannie.spittingimage.util.SpittingImageConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import org.lwjgl.glfw.GLFW;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KeyInputHandler {

    private static final Map<UUID, Long> cooldownMap = new HashMap<>();
    private static final long COOLDOWN_TIME = SpittingImageConfig.spitCooldown*50L;

    public static final String KEY_CATEGORY_SPIT = "spit";
    public static final String KEY_SPIT = "key.spittingimage.spit";

    public static KeyMapping spitKey;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            LivingEntity player = Minecraft.getInstance().player;
            if (player == null) return;
            if (player.isSpectator()) return;

            if(spitKey.consumeClick()){

                UUID playerId = player.getUUID();
                long currentTime = System.currentTimeMillis();

                if (!cooldownMap.containsKey(playerId) || (currentTime - cooldownMap.get(playerId) >= COOLDOWN_TIME)) {
                    ClientPlayNetworking.send(new SpitC2SPacket());
                    cooldownMap.put(playerId, currentTime);
                }
            }
        });
    }

    public static void register(){
        spitKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(KEY_SPIT, InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_COMMA, new KeyMapping.Category(Identifier.parse(KEY_CATEGORY_SPIT))));
        registerKeyInputs();
    }
}
