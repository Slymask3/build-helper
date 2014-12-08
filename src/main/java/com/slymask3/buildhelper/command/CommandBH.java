package com.slymask3.buildhelper.command;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

import com.slymask3.buildhelper.handler.ConfigurationHandler;
import com.slymask3.buildhelper.init.ModItems;
import com.slymask3.buildhelper.reference.Reference;

public class CommandBH extends CommandBase {
	
	public String getCommandName() {
        return "buildhelper";
    }

	public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
        return true;
    }
	
    public int getRequiredPermissionLevel() {
        return 0;
    }

    public List getCommandAliases() {
        return Arrays.asList(new String[] {"bh"});
    }
    
    public void processCommand(ICommandSender ics, String[] arg) {
    	EntityPlayer player = CommandBase.getCommandSenderAsPlayer(ics);
    	
        if (arg.length == 1) {
        	if (arg[0].equalsIgnoreCase("1") || arg[0].equalsIgnoreCase("one")) {
        		list1(player);
        	} else if (arg[0].equalsIgnoreCase("info")) {
        		info(player);
        	} else if (arg[0].equalsIgnoreCase("changelog")) {
                changelog(player);
        	} else {
            	list1(player);
        	}
        } else if (arg.length == 2) {
        	if (arg[0].equalsIgnoreCase("msg")) {
        		ConfigurationHandler.msg = configBoolean(arg, player, ConfigurationHandler.msg, "Messages");
        	} else if (arg[0].equalsIgnoreCase("effect")) {
        		ConfigurationHandler.effect = configBoolean(arg, player, ConfigurationHandler.effect, "Effect");
        	} else if (arg[0].equalsIgnoreCase("sound")) {
        		ConfigurationHandler.sound = configString(arg, player, ConfigurationHandler.sound, "Sound");
        	}
        	
         	else {
             	list1(player);
         	}
        
        } else {
    		list1(player);
    	}
    }
    
    public void list1(EntityPlayer player) {
		player.addChatMessage(new ChatComponentText("\u00a78\u00a7l=============================================")); //1
    	player.addChatMessage(new ChatComponentText("\u00a73\u00a7l"+Reference.MOD_NAME+" v" + Reference.VERSION + " - Command List - Page 1/1")); //2
    	player.addChatMessage(new ChatComponentText("\u00a72/bh info \u00a7a- Gives info of held wand.")); //3
    	player.addChatMessage(new ChatComponentText("\u00a72/bh changelog \u00a7a- Mod changelog.")); //4
    	player.addChatMessage(new ChatComponentText("\u00a72/bh msg [true/false] \u00a7a- Toggle messages from this mod.")); //5
    	player.addChatMessage(new ChatComponentText("\u00a72/bh effect [true/false] \u00a7a- Show particles on wand right-clicks.")); //6
    	player.addChatMessage(new ChatComponentText("\u00a72/bh sound [sound] \u00a7a- Sound to play when wand used.")); //7
        player.addChatMessage(new ChatComponentText("\u00a78\u00a7l=============================================")); //8
	}
	
	public void info(EntityPlayer player) {
        ItemStack held = player.getCurrentEquippedItem();
    	
        player.addChatMessage(new ChatComponentText("\u00a78\u00a7l============================================="));
    	player.addChatMessage(new ChatComponentText("\u00a77\u00a7l"+Reference.MOD_NAME+" v" + Reference.VERSION + " - Info"));
    	
    	if (held != null && held.getItem() == ModItems.wandUniversal) {
    		player.addChatMessage(new ChatComponentText("\u00a72Item: \u00a7aUniversal Wand"));
    		player.addChatMessage(new ChatComponentText("\u00a72Description: \u00a7aBuilds a rectangle with a certain block."));
    		player.addChatMessage(new ChatComponentText("\u00a721st Click: \u00a7aSelects the block to use in building."));
    		player.addChatMessage(new ChatComponentText("\u00a722nd Click: \u00a7aSelects the first position."));
    		player.addChatMessage(new ChatComponentText("\u00a723rd Click: \u00a7aSelects the second position."));
    	} else if (held != null && held.getItem() == ModItems.wandDelete) {
    		player.addChatMessage(new ChatComponentText("\u00a72Item: \u00a7aDeletion Wand"));
    		player.addChatMessage(new ChatComponentText("\u00a72Description: \u00a7aDeletes a rectangle."));
    		player.addChatMessage(new ChatComponentText("\u00a721st Click: \u00a7aSelects the first position."));
    		player.addChatMessage(new ChatComponentText("\u00a722nd Click: \u00a7aSelects the second position."));
    	} else {
    		player.addChatMessage(new ChatComponentText("\u00a7c'" + held.getDisplayName() + "' is not an item part of Build Helper mod."));
    		player.addChatMessage(new ChatComponentText("\u00a7cHold an item that is part of the mod, and perform this command to get information on the item."));
    	}
    	
    	player.addChatMessage(new ChatComponentText("\u00a78\u00a7l============================================="));
    }
	
	public void changelog(EntityPlayer player) {
		player.addChatMessage(new ChatComponentText("\u00a78\u00a7l============================================="));
    	player.addChatMessage(new ChatComponentText("\u00a73\u00a7l"+Reference.MOD_NAME+" v" + Reference.VERSION + " - Changelog"));
		player.addChatMessage(new ChatComponentText("\u00a72- Added Universal Wand."));
		player.addChatMessage(new ChatComponentText("\u00a72- Added Deletion Wand."));
		player.addChatMessage(new ChatComponentText("\u00a78\u00a7l============================================="));
	}
	
	public boolean configBoolean(String[] arg, EntityPlayer player, boolean b, String var) {
		if (arg[1].equalsIgnoreCase("true")) {
	    	player.addChatMessage(new ChatComponentText("\u00a78[\u00a73"+Reference.MOD_NAME+" v" + Reference.VERSION + "\u00a78] \u00a7a" + var + " set to true."));
			return true;
		} else if (arg[1].equalsIgnoreCase("false")) {
	    	player.addChatMessage(new ChatComponentText("\u00a78[\u00a73"+Reference.MOD_NAME+" v" + Reference.VERSION + "\u00a78] \u00a7c" + var + " set to false."));
			return false;
		} else {
			player.addChatMessage(new ChatComponentText("\u00a78[\u00a73"+Reference.MOD_NAME+" v" + Reference.VERSION + "\u00a78] \u00a7cCorrect Usage: /soda " + arg[0] + " [true/false]"));
			return b;
		}
	}
	
	public int configInt(String[] arg, EntityPlayer player, int i, String var) {
		int n;
		
		n = Integer.parseInt(arg[1]);
			
		player.addChatMessage(new ChatComponentText("\u00a78[\u00a73Instant Blocks v" + Reference.VERSION + "\u00a78] \u00a7a" + var + " set to " + n + "."));
		return n;
	}
	
	public String configString(String[] arg, EntityPlayer player, String s, String var) {
		player.addChatMessage(new ChatComponentText("\u00a78[\u00a73Instant Blocks v" + Reference.VERSION + "\u00a78] \u00a7a" + var + " set to '" + arg[1] + "'."));
		return arg[1];
	}

	//NEW 1.7.10 IMPLEMENTATION
	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		// TODO Auto-generated method stub
		return null;
	}
}