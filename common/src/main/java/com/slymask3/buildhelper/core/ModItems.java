package com.slymask3.buildhelper.core;

import com.slymask3.buildhelper.Common;
import com.slymask3.buildhelper.item.DeleteWandItem;
import com.slymask3.buildhelper.item.UniversalWandItem;
import net.minecraft.world.item.Item;

public class ModItems {
    public static Item WAND_UNIVERSAL;
    public static Item WAND_DELETE;

    public static void init() {
        WAND_UNIVERSAL = new UniversalWandItem(new Item.Properties().tab(Common.ITEM_GROUP));
        WAND_DELETE = new DeleteWandItem(new Item.Properties().tab(Common.ITEM_GROUP));
    }
}