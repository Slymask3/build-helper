package com.slymask3.buildhelper.util;

import com.mojang.math.Vector3f;
import com.slymask3.buildhelper.handler.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientHelper {
    public enum Particles { NONE, SELECT_BLOCK, UNIVERSAL_POS, DELETE_POS }

    public static void playSound(Level world, int x, int y, int z) {
        world.playSound(Minecraft.getInstance().player, new BlockPos(x,y,z), new SoundEvent(new ResourceLocation("minecraft", Config.Client.SOUND.get())), SoundSource.BLOCKS,0.4F,1.0F);
    }

    public static void showParticles(Level world, int x, int y, int z, Particles particles) {
        if(Config.Client.SHOW_EFFECTS.get() && !particles.equals(Particles.NONE)) {
            ParticleOptions particleOptions = switch(particles) {
                case SELECT_BLOCK -> new DustParticleOptions(new Vector3f(Vec3.fromRGB24(0xFFFFFF)), 1.0F);
                case UNIVERSAL_POS -> new DustParticleOptions(new Vector3f(Vec3.fromRGB24(0x00FFFF)), 1.0F);
                case DELETE_POS -> new DustParticleOptions(new Vector3f(Vec3.fromRGB24(0xFF0000)), 1.0F);
                default -> DustParticleOptions.REDSTONE;
            };
            for(double i = 0; i <= 1; i = i + 0.2) {
                for(double n = 0; n <= 1; n = n + 0.2) {
                    for(double v = 0; v <= 1; v = v + 0.2) {
                        world.addParticle(particleOptions,x+i, y+v, z+n, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
    }

    public static void sendMessage(Player player, String message) {
        sendMessage(player,message,"");
    }

    public static void sendMessage(Player player, String message, String variable) {
        if(Config.Client.SHOW_MESSAGES.get() && Helper.isClient(player.getLevel())) {
            player.displayClientMessage(Component.translatable(message, variable.isEmpty() ? new Object[0] : variable),true);
        }
    }
}