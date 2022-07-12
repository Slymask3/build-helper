package com.slymask3.buildhelper.util;

import com.slymask3.buildhelper.Common;
import com.slymask3.buildhelper.network.packet.ClientPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Helper {
	public static boolean isServer(Level world) {
		return !world.isClientSide();
	}

	public static boolean isClient(Level world) {
		return world.isClientSide();
	}

	public static void sendMessage(Player player, String message) {
		sendMessage(player, message, "", BlockPos.ZERO, ClientHelper.Particles.NONE);
	}

	public static void sendMessage(Player player, String message, String variable) {
		sendMessage(player, message, variable, BlockPos.ZERO, ClientHelper.Particles.NONE);
	}

	public static void sendMessage(Player player, String message, BlockPos pos, ClientHelper.Particles particles) {
		sendMessage(player, message, "", pos, particles);
	}

	public static void sendMessage(Player player, String message, String variable, BlockPos pos, ClientHelper.Particles particles) {
		if(isServer(player.getLevel())) {
			Common.NETWORK.sendToClient((ServerPlayer)player,new ClientPacket(message,variable,pos,particles.ordinal()));
		}
	}

	public static Block readBlock(String string, Block fallback) {
		return readBlockState(string, fallback.defaultBlockState()).getBlock();
	}

	public static BlockState readBlockState(String string) {
		return readBlockState(string, Blocks.AIR.defaultBlockState());
	}

	public static BlockState readBlockState(String string, BlockState fallback) {
		CompoundTag tag = new CompoundTag();
		String[] split = string.split("\\[",2);
		tag.putString("Name",split[0]);
		if(split.length == 2) {
			CompoundTag propertiesTag = new CompoundTag();
			String[] properties = split[1].replace("]","").split(",");
			for(String property : properties) {
				String[] values = property.split("=");
				if(values.length == 2) {
					propertiesTag.putString(values[0],values[1]);
				}
			}
			tag.put("Properties",propertiesTag);
		}
		BlockState state = NbtUtils.readBlockState(tag);
		if(state.getBlock().equals(Blocks.AIR)) {
			return fallback;
		}
		return state;
	}

	public static boolean isPositive(int i) {
		if(i == 0) return true;
		return i >> 31 == 0;
	}
}