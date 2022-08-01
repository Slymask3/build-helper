package com.slymask3.buildhelper;

import com.slymask3.buildhelper.network.FabricPacketHandler;
import com.slymask3.buildhelper.util.ClientHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.world.InteractionResult;

public class BuildHelperClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WorldRenderEvents.BLOCK_OUTLINE.register((worldRenderContext, blockOutlineContext) -> {
            ClientHelper.renderOverlay(worldRenderContext.matrixStack());
            return true;
        });
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            ClientHelper.onLeftClick(player,player.getItemInHand(hand));
            return InteractionResult.PASS;
        });
        FabricPacketHandler.Client.init();
    }
}