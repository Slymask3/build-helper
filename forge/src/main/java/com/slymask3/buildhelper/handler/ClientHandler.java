package com.slymask3.buildhelper.handler;

import com.slymask3.buildhelper.Common;
import com.slymask3.buildhelper.util.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Common.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientHandler {
    @SubscribeEvent
    public static void onClick(InputEvent.ClickInputEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if(player != null && event.isAttack()) {
            ItemStack itemStack = player.getItemInHand(event.getHand());
            ClientHelper.onLeftClick(player,itemStack);
        }
    }
}