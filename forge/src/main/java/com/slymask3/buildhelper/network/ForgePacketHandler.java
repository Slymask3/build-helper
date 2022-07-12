package com.slymask3.buildhelper.network;

import com.slymask3.buildhelper.Common;
import com.slymask3.buildhelper.network.packet.ClientPacket;
import com.slymask3.buildhelper.network.packet.ResetPacket;
import com.slymask3.buildhelper.util.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class ForgePacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Common.MOD_ID,"main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void register() {
        int index = 100;
        INSTANCE.registerMessage(++index, ClientPacket.class, ClientPacket::encode, ClientPacket::decode, ClientPacketHandler::handle);
        INSTANCE.registerMessage(++index, ResetPacket.class, ResetPacket::encode, ResetPacket::decode, ResetPacketHandler::handle);
    }

    public static class ClientPacketHandler {
        public static void handle(ClientPacket message, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                    Player player = Minecraft.getInstance().player;
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
            context.get().setPacketHandled(true);
        }
    }

    public static class ResetPacketHandler {
        public static void handle(ResetPacket message, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                Player player = context.get().getSender();
                if(player != null) {
                    ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                    itemStack.setTag(new CompoundTag());
                }
            });
            context.get().setPacketHandled(true);
        }
    }
}