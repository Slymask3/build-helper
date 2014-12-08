package com.slymask3.buildhelper.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.slymask3.buildhelper.handler.ConfigurationHandler;
import com.slymask3.buildhelper.reference.Reference;

public class Helper {
	public static void msgClean(EntityPlayer player, String msg, String color) {
		//if (ConfigurationHandler.msg == true) {
			player.addChatMessage(new ChatComponentText("\u00a78[\u00a72"+Reference.MOD_NAME+" v" + Reference.VERSION + "\u00a78] " + ColorHelper.colorEveryWord(msg, color)));
		//}
	}
		
	public static void msgCleanBypass(EntityPlayer player, String msg, String color) {
		player.addChatMessage(new ChatComponentText("\u00a78[\u00a72"+Reference.MOD_NAME+" v" + Reference.VERSION + "\u00a78] " + ColorHelper.colorEveryWord(msg, color)));
	}
	
	public static void sound(World world, String sound, int x, int y, int z) {
		world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), sound, 2.0F, 1.0F);
	}
	
	public static void effectFull(World world, String particle, int x, int y, int z) {
		if (ConfigurationHandler.effect == true) {
			for (double i = 0; i <= 1; i = i + 0.1) {
				for (double n = 0; n <= 1; n = n + 0.1) {
					for (double v = 0; v <= 1; v = v + 0.1) {
						world.spawnParticle(particle, x+i, y+v, z+n, 0.0D, 0.0D, 0.0D);
					}
				}
			}
		}
	}
	
	public static void msg(EntityPlayer player, String msg, String color) {
		if (ConfigurationHandler.msg == true) {
			World world = player.worldObj;
		
			if (world.isRemote) { //IF CLIENT	
				player.addChatMessage(new ChatComponentText("\u00a78[\u00a72"+Reference.MOD_NAME+" v" + Reference.VERSION + "\u00a78] " + ColorHelper.colorEveryWord(msg, color)));
			}
		}
	}
	
	public static void tp(World world, EntityPlayer player, int x, int y, int z) {
		if (!world.isRemote) { //IF SERVER
			player.setPositionAndUpdate(x + 0.5, y + 0.5, z + 0.5);
		}
	}
	
	public static void tp(World world, EntityPlayer player, double x, double y, double z) {
		tp(world, player, (int)x, (int)y, (int)z);
	}
}
