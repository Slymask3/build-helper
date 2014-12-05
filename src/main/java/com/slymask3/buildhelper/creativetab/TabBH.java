package com.slymask3.buildhelper.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.slymask3.buildhelper.init.ModItems;

public class TabBH {
	
	public static final CreativeTabs BUILDHELPER_TAB = new CreativeTabs("buildhelper") {
		@Override
		public Item getTabIconItem() {
			return ModItems.wandUniversal;
		}
	};
}