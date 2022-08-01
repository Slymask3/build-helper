package com.slymask3.buildhelper;

import com.slymask3.buildhelper.config.IConfig;
import com.slymask3.buildhelper.platform.services.IPacketHandler;
import net.minecraft.world.item.CreativeModeTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Common {
    public static final String MOD_ID = "buildhelper";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);
    public static CreativeModeTab ITEM_GROUP;
    public static IPacketHandler NETWORK;
    public static IConfig CONFIG = new IConfig(){};
}