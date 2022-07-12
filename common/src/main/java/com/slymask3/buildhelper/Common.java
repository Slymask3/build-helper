package com.slymask3.buildhelper;

import com.slymask3.buildhelper.platform.Services;
import com.slymask3.buildhelper.platform.services.IPacketHandler;
import net.minecraft.world.item.CreativeModeTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Common {
    public static final String MOD_ID = "buildhelper";
    public static final String MOD_NAME = "Build Helper";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static CreativeModeTab ITEM_GROUP;
    public static IPacketHandler NETWORK;

    public static void init() {
        Common.LOG.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.isDevelopmentEnvironment() ? "development" : "production");
    }
}