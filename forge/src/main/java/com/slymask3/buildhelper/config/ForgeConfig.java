package com.slymask3.buildhelper.config;

import com.slymask3.buildhelper.Common;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class ForgeConfig implements IConfig {
	public static class Client {
		public static BooleanValue SHOW_MESSAGES;
		public static BooleanValue SHOW_EFFECTS;
		public static ConfigValue<String> SOUND;
		public static BooleanValue REQUIRE_CROUCHING;

		Client(ForgeConfigSpec.Builder builder) {
			builder.comment("Client only settings").push("client");

			SHOW_MESSAGES = builder
					.comment("Show messages.\nDefault: true")
					.define("SHOW_MESSAGES", Common.CONFIG.SHOW_MESSAGES());

			SHOW_EFFECTS = builder
					.comment("Show particle effects.\nDefault: true")
					.define("SHOW_EFFECTS", Common.CONFIG.SHOW_EFFECTS());

			SOUND = builder
					.comment("Sound that is played.\nList of sounds can be found here: https://www.digminecraft.com/lists/sound_list_pc.php or by using the /playsound command in-game.\nDefault: entity.player.levelup")
					.define("SOUND", Common.CONFIG.SOUND());

			REQUIRE_CROUCHING = builder
					.comment("Require crouching when clearing wand data.\nDefault: false")
					.define("REQUIRE_CROUCHING", Common.CONFIG.REQUIRE_CROUCHING());

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

	public boolean SHOW_MESSAGES() { return Client.SHOW_MESSAGES.get(); }
	public boolean SHOW_EFFECTS() { return Client.SHOW_EFFECTS.get(); }
	public String SOUND() { return Client.SOUND.get(); }
	public boolean REQUIRE_CROUCHING() { return Client.REQUIRE_CROUCHING.get(); }
}