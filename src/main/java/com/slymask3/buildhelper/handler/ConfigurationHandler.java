package com.slymask3.buildhelper.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {
    public static Configuration configuration;
    
    //public static boolean msg;

    public static void init(File configFile) {
        if (configuration == null) {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }
    
	public static String options = "Options";
    
    private static void loadConfiguration() {
		//msg = configuration.get(options, "[Option] Messages", true, "Whether to show InstantBlock messages or not.\n(Default = true)").getBoolean(true);
		
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase("buildhelper")) {
            loadConfiguration();
        }
    }
}