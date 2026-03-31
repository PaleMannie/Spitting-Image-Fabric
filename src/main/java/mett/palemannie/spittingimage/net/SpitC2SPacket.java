package mett.palemannie.spittingimage.net;

import mett.palemannie.spittingimage.SpittingImage;
import mett.palemannie.spittingimage.entity.ModEntities;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SpitC2SPacket() implements CustomPacketPayload {

    private static Identifier spitId = BuiltInRegistries.ENTITY_TYPE.getKey(ModEntities.SPIT);
    public static final CustomPacketPayload.Type<SpitC2SPacket> ID = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(SpittingImage.MODID, spitId.getPath()));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpitC2SPacket> PACKET_CODEC = StreamCodec.ofMember(SpitC2SPacket::encode, SpitC2SPacket::decode);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static void encode(SpitC2SPacket packet, RegistryFriendlyByteBuf buf) {}

    public static SpitC2SPacket decode(RegistryFriendlyByteBuf buf) {
        return new SpitC2SPacket();
    }

    public static void initializePacket() {
        PayloadTypeRegistry.serverboundPlay().register(SpitC2SPacket.ID, SpitC2SPacket.PACKET_CODEC);
    }
}
