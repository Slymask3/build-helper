package com.slymask3.buildhelper.util;

import com.mojang.math.Vector3f;
import com.slymask3.buildhelper.Common;
import com.slymask3.buildhelper.core.Config;
import com.slymask3.buildhelper.core.ModItems;
import com.slymask3.buildhelper.network.packet.ResetPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ClientHelper {
    public enum Particles { NONE, SELECT_BLOCK, UNIVERSAL_POS, DELETE_POS }

    public static void playSound(Level world, BlockPos pos) {
        world.playSound(Minecraft.getInstance().player, pos, new SoundEvent(new ResourceLocation("minecraft", Config.SOUND)), SoundSource.BLOCKS,0.4F,1.0F);
    }

    public static void showParticles(Level world, BlockPos pos, Particles particles) {
        if(Config.SHOW_EFFECTS && !particles.equals(Particles.NONE)) {
            ParticleOptions particleOptions = switch(particles) {
                case SELECT_BLOCK -> new DustParticleOptions(new Vector3f(Vec3.fromRGB24(0xFFFFFF)), 1.0F);
                case UNIVERSAL_POS -> new DustParticleOptions(new Vector3f(Vec3.fromRGB24(0x00FFFF)), 1.0F);
                case DELETE_POS -> new DustParticleOptions(new Vector3f(Vec3.fromRGB24(0xFF0000)), 1.0F);
                default -> DustParticleOptions.REDSTONE;
            };
            for(double i = 0; i <= 1; i = i + 0.2) {
                for(double n = 0; n <= 1; n = n + 0.2) {
                    for(double v = 0; v <= 1; v = v + 0.2) {
                        world.addParticle(particleOptions,pos.getX()+i, pos.getY()+v, pos.getZ()+n, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
    }

    public static void sendMessage(Player player, String message) {
        sendMessage(player,message,"");
    }

    public static void sendMessage(Player player, String message, String variable) {
        if(Config.SHOW_MESSAGES && Helper.isClient(player.getLevel())) {
            player.displayClientMessage(Component.translatable(message, variable.isEmpty() ? new Object[0] : variable),true);
        }
    }

    public static void onLeftClick(Player player, ItemStack itemStack) {
        if(player.isCrouching() || !Config.REQUIRE_CROUCHING) {
            if(itemStack.getItem().equals(ModItems.WAND_UNIVERSAL) || itemStack.getItem().equals(ModItems.WAND_DELETE)) {
                itemStack.setTag(new CompoundTag());
                Common.NETWORK.sendToServer(new ResetPacket());
                ClientHelper.sendMessage(player,"bh.message.clear");
            }
        }
    }
}