package com.slymask3.buildhelper.handler;

import com.mojang.blaze3d.platform.InputConstants;
import com.slymask3.buildhelper.Common;
import com.slymask3.buildhelper.util.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Common.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientHandler {
    @SubscribeEvent
    public static void onClick(InputEvent.MouseButton.Pre event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if(player != null && Minecraft.getInstance().screen == null && event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT && event.getAction() == InputConstants.PRESS) {
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            ClientHelper.onLeftClick(player,itemStack);
        }
    }

    @SubscribeEvent
    public static void onRender(RenderLevelStageEvent event) {
        if(event.getStage().equals(RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS)) {
            ClientHelper.renderOverlay(event.getPoseStack());
        }
    }
}