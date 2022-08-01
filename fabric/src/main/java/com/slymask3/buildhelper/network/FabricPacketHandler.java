package com.slymask3.buildhelper.network;

import com.slymask3.buildhelper.network.packet.ClientPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;

public class FabricPacketHandler {
    public static class Common {
        public static void init() {
            ServerPlayNetworking.registerGlobalReceiver(new ResourceLocation(PacketHelper.PacketID.RESET.toString().toLowerCase()), (server, player, handler, buf, responseSender) -> {
                if(player != null) {
                    server.execute(() -> PacketHelper.handleReset(player));
                }
            });
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Client {
        public static void init() {
            ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation(PacketHelper.PacketID.CLIENT.toString().toLowerCase()), (client, handler, buf, responseSender) -> {
                if(client.player != null) {
                    ClientPacket message = ClientPacket.decode(buf);
                    client.execute(() -> PacketHelper.handleClient(message, client.player));
                }
            });
        }
    }
}