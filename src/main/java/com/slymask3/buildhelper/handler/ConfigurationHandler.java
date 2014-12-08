package com.slymask3.buildhelper.handler;

import java.io.File;

import com.slymask3.buildhelper.reference.Reference;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {
    public static Configuration configuration;

    public static boolean msg;
    public static boolean effect;
    public static String sound;

    public static void init(File configFile) {
        if (configuration == null) {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }
    
	public static String options = "Options";
    
    private static void loadConfiguration() {
		msg = configuration.get(options, "[Option] Messages", true, "Whether to show chat messages from the mod.\n(Default = true)").getBoolean(true);
		effect = configuration.get(options, "[Option] Effect", true, "Whether to show red particle effects when right-clicking with build wands.\n(Default = true)").getBoolean(true);
		sound = configuration.get(options, "[Option] Sound", "random.levelup", "Which sound to play when using build wands.\nThe directory is .minecarft\\resources\\sound3\\.\nFor example, the default sound is .minecarft\\resources\\sound3\\random\\levelup.ogg\n(Default = random.levelup)").getString();
		
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfiguration();
        }
    }
}