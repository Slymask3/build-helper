package com.slymask3.buildhelper.network.packet;

import com.slymask3.buildhelper.network.PacketID;
import net.minecraft.network.FriendlyByteBuf;

public class ResetPacket extends AbstractPacket {
	public ResetPacket() {
		super(PacketID.RESET);
	}

	public static void encode(ResetPacket message, FriendlyByteBuf buffer) {}

	public static ResetPacket decode(FriendlyByteBuf buffer) {
		return new ResetPacket();
	}
}