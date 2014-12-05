package com.slymask3.buildhelper.gui;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import com.slymask3.buildhelper.handler.ConfigurationHandler;

import cpw.mods.fml.client.config.GuiConfig;

public class GuiConfigBH extends GuiConfig {  
    static List list = new ConfigElement(ConfigurationHandler.configuration.getCategory("options")).getChildElements();
    
	private static String titleDir = GuiConfig.getAbridgedConfigPath(ConfigurationHandler.configuration.toString());
    
	public GuiConfigBH(GuiScreen parent) {
        super(parent, list, "buildhelper", false, false, titleDir);
    }
}