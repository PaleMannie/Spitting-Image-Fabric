package mett.palemannie.spittingimage.event;

import mett.palemannie.spittingimage.net.SpitC2SPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KeyInputHandler {

    private static final Map<UUID, Long> cooldownMap = new HashMap<>();
    private static final long COOLDOWN_TIME = 150;

    public static final String KEY_CATEGORY_SPIT = "key.category.spittingimage.spit";
    public static final String KEY_SPIT = "key.spittingimage.spit";

    public static KeyBinding spitKey;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            LivingEntity player = MinecraftClient.getInstance().player;
            if (player == null) return;
            if (player.isSpectator()) return;

            if(spitKey.wasPressed()){

                UUID playerId = player.getUuid();
                long currentTime = System.currentTimeMillis();

                if (!cooldownMap.containsKey(playerId) || (currentTime - cooldownMap.get(playerId) >= COOLDOWN_TIME)) {
                    ClientPlayNetworking.send(new SpitC2SPacket());
                    cooldownMap.put(playerId, currentTime);
                }
            }
        });
    }

    public static void register(){
        spitKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_SPIT, InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_COMMA, KEY_CATEGORY_SPIT));
        registerKeyInputs();
    }
}
