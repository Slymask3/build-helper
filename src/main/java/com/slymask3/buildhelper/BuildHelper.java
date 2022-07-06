package com.slymask3.buildhelper;

import com.slymask3.buildhelper.handler.Config;
import com.slymask3.buildhelper.init.ModItems;
import com.slymask3.buildhelper.network.PacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BuildHelper.MOD_ID)
public class BuildHelper {
	public static final String MOD_ID = "buildhelper";
	public static final Logger LOGGER = LogManager.getLogger();

	public BuildHelper() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		Config.init();
		modEventBus.addListener(this::setupCommon);
		ModItems.ITEMS.register(modEventBus);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setupCommon(final FMLCommonSetupEvent event) {
		PacketHandler.register();
	}
}