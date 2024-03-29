package com.slymask3.buildhelper.network.packet;

import com.slymask3.buildhelper.network.PacketHelper;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractPacket {
    private final ResourceLocation key;
    public AbstractPacket(PacketHelper.PacketID packetID) {
        this.key = new ResourceLocation(packetID.toString().toLowerCase());
    }
    public ResourceLocation getKey() {
        return this.key;
    }
    public FriendlyByteBuf getBuffer() {
        return new FriendlyByteBuf(Unpooled.EMPTY_BUFFER);
    }
}