package com.slymask3.buildhelper.init;

import com.slymask3.buildhelper.core.ModItems;
import com.slymask3.buildhelper.platform.services.IRegistryHelper;
import com.slymask3.buildhelper.reference.Names;
import net.minecraft.world.item.Item;

public class Registration {
    public static void registerItems(IRegistryHelper<Item> helper) {
        ModItems.init();
        helper.register(Names.Items.WAND_UNIVERSAL, ModItems.WAND_UNIVERSAL);
        helper.register(Names.Items.WAND_DELETE, ModItems.WAND_DELETE);
    }
}