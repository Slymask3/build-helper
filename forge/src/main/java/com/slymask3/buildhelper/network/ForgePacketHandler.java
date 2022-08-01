package com.slymask3.buildhelper.network;

import com.slymask3.buildhelper.Common;
import com.slymask3.buildhelper.network.packet.AbstractPacket;
import com.slymask3.buildhelper.network.packet.ClientPacket;
import com.slymask3.buildhelper.network.packet.ResetPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class ForgePacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Common.MOD_ID,"main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void register() {
        int index = 100;
        INSTANCE.registerMessage(++index, ClientPacket.class, ClientPacket::encode, ClientPacket::decode, Handler::client);
        INSTANCE.registerMessage(++index, ResetPacket.class, ResetPacket::encode, ResetPacket::decode, Handler::common);
    }

    public static class Handler {
        public static void common(AbstractPacket message, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                Player player = context.get().getSender();
                if(player != null) {
                    if(message.getClass().equals(ResetPacket.class)) {
                        PacketHelper.handleReset(player);
                    }
                }
            });
            context.get().setPacketHandled(true);
        }
        public static void client(AbstractPacket message, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHandler.handle(message,context));
            });
            context.get().setPacketHandled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class ClientHandler {
        public static void handle(AbstractPacket message, Supplier<NetworkEvent.Context> context) {
            Player player = Minecraft.getInstance().player;
            if(player != null) {
                if(message.getClass().equals(ClientPacket.class)) {
                    PacketHelper.handleClient((ClientPacket)message, player);
                }
            }
        }
    }
}