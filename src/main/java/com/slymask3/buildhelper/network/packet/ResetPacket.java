package com.slymask3.buildhelper.network.packet;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ResetPacket {
	public ResetPacket() {}

	public static void encode(ResetPacket message, FriendlyByteBuf buffer) {}

	public static ResetPacket decode(FriendlyByteBuf buffer) {
		return new ResetPacket();
	}

	public static class Handler {
		public static void handle(ResetPacket message, Supplier<NetworkEvent.Context> context) {
			context.get().enqueueWork(() -> {
				Player player = context.get().getSender();
				if(player != null) {
					ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
					itemStack.setTag(new CompoundTag());
				}
			});
			context.get().setPacketHandled(true);
		}
	}
}