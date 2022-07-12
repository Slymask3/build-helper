package com.slymask3.buildhelper.platform.services;

import com.slymask3.buildhelper.Common;
import net.minecraft.resources.ResourceLocation;

public interface IRegistryHelper<T> {
    void register(ResourceLocation name, T entry);
    default void register(String name, T entry) {
        register(new ResourceLocation(Common.MOD_ID, name), entry);
    }
}