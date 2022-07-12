package com.slymask3.buildhelper.handler;

import com.slymask3.buildhelper.Common;
import com.slymask3.buildhelper.core.Config;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = Common.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeConfig {
	public static class Client {
		public static BooleanValue SHOW_MESSAGES;
		public static BooleanValue SHOW_EFFECTS;
		public static ConfigValue<String> SOUND;
		public static BooleanValue REQUIRE_CROUCHING;

		Client(ForgeConfigSpec.Builder builder) {
			builder.comment("Client only settings").push("client");

			SHOW_MESSAGES = builder
					.comment("Show messages.\nDefault: true")
					.define("SHOW_MESSAGES", Config.SHOW_MESSAGES);

			SHOW_EFFECTS = builder
					.comment("Show particle effects.\nDefault: true")
					.define("SHOW_EFFECTS", Config.SHOW_EFFECTS);

			SOUND = builder
					.comment("Sound that is played.\nList of sounds can be found here: https://www.digminecraft.com/lists/sound_list_pc.php or by using the /playsound command in-game.\nDefault: entity.player.levelup")
					.define("SOUND", Config.SOUND);

			REQUIRE_CROUCHING = builder
					.comment("Require crouching when clearing wand data.\nDefault: false")
					.define("REQUIRE_CROUCHING", Config.REQUIRE_CROUCHING);

			builder.pop();
		}
	}

	public static final ForgeConfigSpec clientSpec;
	public static final Client CLIENT;

	static {
		final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
		clientSpec = specPair.getRight();
		CLIENT = specPair.getLeft();
	}

	public static void init() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ForgeConfig.clientSpec);
	}


	@SubscribeEvent
	public static void onLoad(ModConfigEvent.Loading event) {
		load();
	}

	@SubscribeEvent
	public static void onReload(ModConfigEvent.Loading event) {
		load();
	}

	private static void load() {
		Config.SHOW_MESSAGES = Client.SHOW_MESSAGES.get();
		Config.SHOW_EFFECTS = Client.SHOW_EFFECTS.get();
		Config.SOUND = Client.SOUND.get();
		Config.REQUIRE_CROUCHING = Client.REQUIRE_CROUCHING.get();
	}
}