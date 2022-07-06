package com.slymask3.buildhelper.network;

import com.slymask3.buildhelper.BuildHelper;
import com.slymask3.buildhelper.network.packet.ClientPacket;
import com.slymask3.buildhelper.network.packet.ResetPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(BuildHelper.MOD_ID,"main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void register() {
        int index = 100;
        INSTANCE.registerMessage(++index, ClientPacket.class, ClientPacket::encode, ClientPacket::decode, ClientPacket.Handler::handle);
        INSTANCE.registerMessage(++index, ResetPacket.class, ResetPacket::encode, ResetPacket::decode, ResetPacket.Handler::handle);
    }

    public static void sendToServer(Object message) {
        INSTANCE.sendToServer(message);
    }

    public static void sendToClient(ServerPlayer player, Object message) {
        INSTANCE.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }
}