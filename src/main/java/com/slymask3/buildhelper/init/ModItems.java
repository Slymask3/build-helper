package com.slymask3.buildhelper.init;

import net.minecraft.item.Item;

import com.slymask3.buildhelper.item.ItemDeleteWand;
import com.slymask3.buildhelper.item.ItemUniversalWand;
import com.slymask3.buildhelper.reference.Names;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
	public static Item wandUniversal = new ItemUniversalWand().setUnlocalizedName(Names.Items.WAND_UNIVERSAL);
	public static Item wandDelete = new ItemDeleteWand().setUnlocalizedName(Names.Items.WAND_DELETE);
	//public static Item wandTeleport = new ItemTeleportWand().setUnlocalizedName(Names.Items.WAND_TELEPORT);
	
	public static void init() {
		GameRegistry.registerItem(wandUniversal, Names.Items.WAND_UNIVERSAL);
		GameRegistry.registerItem(wandDelete, Names.Items.WAND_DELETE);
		//GameRegistry.registerItem(wandTeleport, Names.Items.WAND_TELEPORT);
	}
}