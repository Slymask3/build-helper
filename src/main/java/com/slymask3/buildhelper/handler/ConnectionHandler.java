package com.slymask3.buildhelper.handler;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import net.minecraft.entity.player.EntityPlayer;

import com.slymask3.buildhelper.reference.Colors;
import com.slymask3.buildhelper.reference.Reference;
import com.slymask3.buildhelper.reference.Strings;
import com.slymask3.buildhelper.utility.Helper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class ConnectionHandler {
	@SubscribeEvent
	public void onClientConnection(PlayerLoggedInEvent event) {
		EntityPlayer play = event.player;
		
		//Helper.msgClean(play, "\u00a7aLoaded successfully.", Colors.a);
		
		try {
			URL url = new URL(Strings.checkURL);
			Scanner s = new Scanner(url.openStream());
			
			String ver = s.nextLine();
			String mc = s.nextLine();
			
			if (!ver.equalsIgnoreCase(Reference.VERSION)) {
				Helper.msgCleanBypass(play, "\u00a7cUpdate avaliable: v" + ver + " \u00a7c(MC " + mc + ")!", Colors.c);
				Helper.msgCleanBypass(play, "\u00a7cLink: \u00a7b\u00a7n" + Strings.modURL, Colors.c);
			}
			
			s.close();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
