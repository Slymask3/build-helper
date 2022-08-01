package com.slymask3.buildhelper.config;

public interface IConfig {
    default void reload() {}

    default boolean SHOW_MESSAGES() { return Defaults.SHOW_MESSAGES; }
    default boolean SHOW_EFFECTS() { return Defaults.SHOW_EFFECTS; }
    default String SOUND() { return Defaults.SOUND; }
    default boolean REQUIRE_CROUCHING() { return Defaults.REQUIRE_CROUCHING; }

    class Defaults {
        public static boolean SHOW_MESSAGES = true;
        public static boolean SHOW_EFFECTS = true;
        public static String SOUND = "entity.player.levelup";
        public static boolean REQUIRE_CROUCHING = false;
    }
}