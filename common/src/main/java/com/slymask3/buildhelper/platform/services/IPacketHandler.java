package com.slymask3.buildhelper.platform.services;

import com.slymask3.buildhelper.network.packet.AbstractPacket;
import net.minecraft.server.level.ServerPlayer;

public interface IPacketHandler {
    void sendToServer(AbstractPacket message);
    void sendToClient(ServerPlayer player, AbstractPacket message);
}
