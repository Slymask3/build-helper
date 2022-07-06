package com.slymask3.buildhelper.init;

import com.slymask3.buildhelper.BuildHelper;
import com.slymask3.buildhelper.item.ItemDeleteWand;
import com.slymask3.buildhelper.item.ItemUniversalWand;
import com.slymask3.buildhelper.reference.Names;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BuildHelper.MOD_ID);

	public static final RegistryObject<Item> WAND_UNIVERSAL = ITEMS.register(Names.Items.WAND_UNIVERSAL, ItemUniversalWand::new);
	public static final RegistryObject<Item> WAND_DELETE = ITEMS.register(Names.Items.WAND_DELETE, ItemDeleteWand::new);

	public static class ModCreativeTab extends CreativeModeTab {
		public static final ModCreativeTab instance = new ModCreativeTab(CreativeModeTab.TABS.length);
		private ModCreativeTab(int index) {
			super(index, BuildHelper.MOD_ID);
		}

		@Override
		public ItemStack makeIcon() {
			return new ItemStack(WAND_UNIVERSAL.get());
		}
	}
}