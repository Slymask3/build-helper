package com.slymask3.buildhelper;

import com.slymask3.buildhelper.config.ClothConfig;
import com.slymask3.buildhelper.core.ModItems;
import com.slymask3.buildhelper.init.Registration;
import com.slymask3.buildhelper.network.FabricPacketHandler;
import com.slymask3.buildhelper.network.packet.AbstractPacket;
import com.slymask3.buildhelper.platform.Services;
import com.slymask3.buildhelper.platform.services.IPacketHandler;
import com.slymask3.buildhelper.platform.services.IRegistryHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class BuildHelper implements ModInitializer {
    @Override
    public void onInitialize() {
        Common.ITEM_GROUP = FabricItemGroupBuilder.build(new ResourceLocation(Common.MOD_ID, "general"), () -> new ItemStack(ModItems.WAND_UNIVERSAL));
        Common.NETWORK = new PacketHandler();

        if(Services.PLATFORM.isModLoaded("cloth-config")) {
            ClothConfig.register();
            Common.CONFIG = ClothConfig.get();
        }

        Registration.registerItems(new FabricRegistryHelper<>(Registry.ITEM));

        FabricPacketHandler.Common.init();
    }

    public static class FabricRegistryHelper<T> implements IRegistryHelper<T> {
        Registry<T> registry;
        public FabricRegistryHelper(Registry<T> registry) {
            this.registry = registry;
        }
        public void register(ResourceLocation name, T entry) {
            Registry.register(this.registry, name, entry);
        }
    }

    public static class PacketHandler implements IPacketHandler {
        public void sendToServer(AbstractPacket message) {
            ClientPlayNetworking.send(message.getKey(), message.getBuffer());
        }
        public void sendToClient(ServerPlayer player, AbstractPacket message) {
            ServerPlayNetworking.send(player, message.getKey(), message.getBuffer());
        }
    }
}