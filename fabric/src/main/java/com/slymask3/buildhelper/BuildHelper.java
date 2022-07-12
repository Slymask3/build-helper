package com.slymask3.buildhelper;

import com.slymask3.buildhelper.core.ModItems;
import com.slymask3.buildhelper.init.Registration;
import com.slymask3.buildhelper.network.PacketID;
import com.slymask3.buildhelper.network.packet.AbstractPacket;
import com.slymask3.buildhelper.network.packet.ClientPacket;
import com.slymask3.buildhelper.platform.services.IPacketHandler;
import com.slymask3.buildhelper.platform.services.IRegistryHelper;
import com.slymask3.buildhelper.util.ClientHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BuildHelper implements ModInitializer {
    @Override
    public void onInitialize() {
        Common.LOG.info("Hello Fabric world!");
        Common.NETWORK = new PacketHandler();
        Common.init();

        Common.ITEM_GROUP = FabricItemGroupBuilder.build(new ResourceLocation(Common.MOD_ID, "general"), () -> new ItemStack(ModItems.WAND_UNIVERSAL));
        Registration.registerItems(new FabricRegistryHelper<>(Registry.ITEM));

        ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation(PacketID.CLIENT.toString().toLowerCase()), (client, handler, buf, responseSender) -> {
            ClientPacket message = ClientPacket.decode(buf);
            client.execute(() -> {
                // Everything in this lambda is run on the render thread
                Player player = client.player;
                if(player != null) {
                    if(message.particles != ClientHelper.Particles.NONE.ordinal()) {
                        Level world = player.getLevel();
                        ClientHelper.playSound(world, message.pos);
                        ClientHelper.showParticles(world, message.pos, ClientHelper.Particles.values()[message.particles]);
                    }
                    if(!message.message.isEmpty()) {
                        ClientHelper.sendMessage(player, message.message, message.variable);
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(new ResourceLocation(PacketID.RESET.toString().toLowerCase()), (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                itemStack.setTag(new CompoundTag());
            });
        });
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