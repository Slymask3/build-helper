package com.slymask3.buildhelper.config;

import com.slymask3.buildhelper.Common;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

@Config(name = Common.MOD_ID)
public class ClothConfig implements ConfigData, IConfig {
    public static void register() {
        AutoConfig.register(ClothConfig.class, Toml4jConfigSerializer::new);
    }

    public static ClothConfig get() {
        return AutoConfig.getConfigHolder(ClothConfig.class).getConfig();
    }

    public void reload() {
        AutoConfig.getConfigHolder(ClothConfig.class).load();
        Common.CONFIG = get();
    }

    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.PrefixText()
    @ConfigEntry.Gui.TransitiveObject
    SectionClient client = new SectionClient();
    static class SectionClient {
        boolean SHOW_MESSAGES = Defaults.SHOW_MESSAGES;
        boolean SHOW_EFFECTS = Defaults.SHOW_EFFECTS;
        String SOUND = Defaults.SOUND;
        boolean REQUIRE_CROUCHING = Defaults.REQUIRE_CROUCHING;
    }

    public boolean SHOW_MESSAGES() { return client.SHOW_MESSAGES; }
    public boolean SHOW_EFFECTS() { return client.SHOW_EFFECTS; }
    public String SOUND() { return client.SOUND; }
    public boolean REQUIRE_CROUCHING() { return client.REQUIRE_CROUCHING; }
}