package com.slymask3.buildhelper.handler;

import com.slymask3.buildhelper.BuildHelper;
import com.slymask3.buildhelper.init.ModItems;
import com.slymask3.buildhelper.network.PacketHandler;
import com.slymask3.buildhelper.network.packet.ResetPacket;
import com.slymask3.buildhelper.util.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BuildHelper.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class Client {
    @SubscribeEvent
    public static void onClick(InputEvent.ClickInputEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if(player != null && (player.isCrouching() || !Config.Client.REQUIRE_CROUCHING.get()) && event.isAttack()) {
            ItemStack itemStack = player.getItemInHand(event.getHand());
            if(itemStack.getItem().equals(ModItems.WAND_UNIVERSAL.get()) || itemStack.getItem().equals(ModItems.WAND_DELETE.get())) {
                itemStack.setTag(new CompoundTag());
                PacketHandler.sendToServer(new ResetPacket());
                ClientHelper.sendMessage(player,"bh.message.clear");
            }
        }
    }
}