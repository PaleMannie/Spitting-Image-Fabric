package mett.palemannie.spittingimage.net;

import mett.palemannie.spittingimage.SpittingImage;
import mett.palemannie.spittingimage.item.ModItems;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public record SpitC2SPacket() implements CustomPayload {

    private static final Identifier spitId = Registries.ITEM.getId(ModItems.SPIT_PROJECTILE);
    public static final CustomPayload.Id<SpitC2SPacket> ID = new CustomPayload.Id<>(Identifier.of(SpittingImage.MODID, spitId.getPath()));
    public static final PacketCodec<RegistryByteBuf, SpitC2SPacket> PACKET_CODEC = PacketCodec.of(SpitC2SPacket::encode, SpitC2SPacket::decode);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void encode(SpitC2SPacket packet, RegistryByteBuf buf) {}

    public static SpitC2SPacket decode(RegistryByteBuf buf) {
        return new SpitC2SPacket();
    }

    public static void initializePacket() {
        PayloadTypeRegistry.playC2S().register(SpitC2SPacket.ID, SpitC2SPacket.PACKET_CODEC);
    }
}
