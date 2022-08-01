package com.slymask3.buildhelper.network;

import com.slymask3.buildhelper.network.packet.ClientPacket;
import com.slymask3.buildhelper.util.ClientHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PacketHelper {
    public enum PacketID { CLIENT, RESET }

    public static void handleClient(ClientPacket message, Player player) {
        if(message.particles != ClientHelper.Particles.NONE.ordinal()) {
            Level world = player.getLevel();
            ClientHelper.playSound(world, message.pos);
            ClientHelper.showParticles(world, message.pos, ClientHelper.Particles.values()[message.particles]);
        }
        if(!message.message.isEmpty()) {
            ClientHelper.sendMessage(player, message.message, message.variable);
        }
    }

    public static void handleReset(Player player) {
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        itemStack.setTag(new CompoundTag());
    }
}