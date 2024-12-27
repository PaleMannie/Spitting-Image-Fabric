package mett.palemannie.spittingimage.net;

import mett.palemannie.spittingimage.SpittingImage;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier SPITTING = new Identifier(SpittingImage.MODID, "spitting");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(SPITTING, SpitC2SPacket::receive);
    }

    public static void registerS2CPackets() {

    }
}
