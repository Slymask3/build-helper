package com.slymask3.buildhelper;

import com.slymask3.buildhelper.config.ClothConfig;
import com.slymask3.buildhelper.config.ForgeConfig;
import com.slymask3.buildhelper.core.ModItems;
import com.slymask3.buildhelper.init.Registration;
import com.slymask3.buildhelper.network.ForgePacketHandler;
import com.slymask3.buildhelper.network.packet.AbstractPacket;
import com.slymask3.buildhelper.platform.Services;
import com.slymask3.buildhelper.platform.services.IPacketHandler;
import com.slymask3.buildhelper.platform.services.IRegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegisterEvent;
import org.jetbrains.annotations.NotNull;

@Mod(Common.MOD_ID)
public class BuildHelper {
	public BuildHelper() {
		Common.ITEM_GROUP = new CreativeModeTab(CreativeModeTab.TABS.length,Common.MOD_ID) { public @NotNull ItemStack makeIcon() { return new ItemStack(ModItems.WAND_UNIVERSAL); } };
		Common.NETWORK = new PacketHandler();

		if(Services.PLATFORM.isModLoaded("cloth_config")) {
			ClothConfig.register();
			Common.CONFIG = ClothConfig.get();
		} else {
			ForgeConfig.init();
			Common.CONFIG = new ForgeConfig();
		}

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		modEventBus.addListener(this::setupCommon);
		modEventBus.addListener((RegisterEvent e) -> {
			if(e.getForgeRegistry() != null && e.getForgeRegistry().getRegistryKey().equals(Registry.ITEM_REGISTRY)) {
				Registration.registerItems(new ForgeRegistryHelper<>(e.getForgeRegistry()));
			}
		});

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setupCommon(final FMLCommonSetupEvent event) {
		ForgePacketHandler.register();
	}

	public static class ForgeRegistryHelper<T> implements IRegistryHelper<T> {
		IForgeRegistry<T> registry;
		public ForgeRegistryHelper(IForgeRegistry<T> registry) {
			this.registry = registry;
		}
		public void register(ResourceLocation name, T entry) {
			this.registry.register(name,entry);
		}
	}

	public static class PacketHandler implements IPacketHandler {
		public void sendToServer(AbstractPacket message) {
			ForgePacketHandler.INSTANCE.sendToServer(message);
		}
		public void sendToClient(ServerPlayer player, AbstractPacket message) {
			ForgePacketHandler.INSTANCE.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
		}
	}
}