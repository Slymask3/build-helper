package com.slymask3.buildhelper.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.slymask3.buildhelper.Common;
import com.slymask3.buildhelper.core.ModItems;
import com.slymask3.buildhelper.network.packet.ResetPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ClientHelper {
    public enum Particles { NONE, SELECT_BLOCK, UNIVERSAL_POS, DELETE_POS }

    public static void playSound(Level world, BlockPos pos) {
        world.playSound(Minecraft.getInstance().player, pos, new SoundEvent(new ResourceLocation("minecraft", Common.CONFIG.SOUND())), SoundSource.BLOCKS,0.4F,1.0F);
    }

    public static void showParticles(Level world, BlockPos pos, Particles particles) {
        if(Common.CONFIG.SHOW_EFFECTS() && !particles.equals(Particles.NONE)) {
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
        if(Common.CONFIG.SHOW_MESSAGES() && Helper.isClient(player.getLevel())) {
            player.displayClientMessage(Component.translatable(message, variable.isEmpty() ? new Object[0] : variable),true);
        }
    }

    public static void onLeftClick(Player player, ItemStack itemStack) {
        if(player.isCrouching() || !Common.CONFIG.REQUIRE_CROUCHING()) {
            if(itemStack.getItem().equals(ModItems.WAND_UNIVERSAL) || itemStack.getItem().equals(ModItems.WAND_DELETE)) {
                itemStack.setTag(new CompoundTag());
                Common.NETWORK.sendToServer(new ResetPacket());
                sendMessage(player,"bh.message.clear");
            }
        }
    }

    public static void renderOverlay(PoseStack poseStack) {
        Player player = Minecraft.getInstance().player;
        if(player != null) {
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(itemStack.getItem().equals(ModItems.WAND_UNIVERSAL) || itemStack.getItem().equals(ModItems.WAND_DELETE)) {
                BlockPos pos = getLookingAtBlockPos(player);
                CompoundTag tag = itemStack.getTag();
                if(tag != null && pos != null) {
                    BlockState state = null;
                    if(itemStack.getItem().equals(ModItems.WAND_UNIVERSAL)) {
                        state = Helper.readBlockState(tag.getString("Block"));
                    }
                    int x1 = tag.getInt("PosX");
                    int y1 = tag.getInt("PosY");
                    int z1 = tag.getInt("PosZ");
                    int x2 = pos.getX();
                    int y2 = pos.getY();
                    int z2 = pos.getZ();
                    if(x1 != 0 && y1 != 0 && z1 != 0) {
                        boolean x_dir = Helper.isPositive(x1 - x2);
                        boolean y_dir = Helper.isPositive(y1 - y2);
                        boolean z_dir = Helper.isPositive(z1 - z2);
                        for(int y_cur=y1; (y_dir?y_cur>=y2:y_cur<=y2); y_cur=y_cur+(y_dir?-1:1)) {
                            for(int z_cur=z1; (z_dir?z_cur>=z2:z_cur<=z2); z_cur=z_cur+(z_dir?-1:1)) {
                                for(int x_cur=x1; (x_dir?x_cur>=x2:x_cur<=x2); x_cur=x_cur+(x_dir?-1:1)) {
                                    BlockPos pos_cur = new BlockPos(x_cur,y_cur,z_cur);
                                    if(itemStack.getItem().equals(ModItems.WAND_UNIVERSAL)) {
                                        drawBlock(player, poseStack, pos_cur, state);
                                        drawOutline(player, poseStack, pos_cur, state, 0F, 1F, 1F, 1.0F);
                                    } else if(itemStack.getItem().equals(ModItems.WAND_DELETE)) {
                                        drawOutline(player, poseStack, pos_cur, null, 1F, 0F, 0F, 1.0F);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void drawBlock(Player player, PoseStack poseStack, BlockPos pos, BlockState state) {
        Vec3 cam = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

        poseStack.pushPose();
        poseStack.translate(pos.getX()-cam.x, pos.getY()-cam.y, pos.getZ()-cam.z);

        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        VertexConsumer vertexConsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(state));
        int color = Minecraft.getInstance().getBlockColors().getColor(state, player.level, pos, 0);
        float red = (float)(color >> 16 & 255) / 255.0F;
        float green = (float)(color >> 8 & 255) / 255.0F;
        float blue = (float)(color & 255) / 255.0F;
        blockRenderer.getModelRenderer().renderModel(poseStack.last(), vertexConsumer, state, blockRenderer.getBlockModel(state), red, green, blue, 0x44FFFFFF, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }

    private static void drawOutline(Player player, PoseStack poseStack, BlockPos pos, BlockState state, float red, float green, float blue, float alpha) {
        VoxelShape voxelShape = (state == null ? player.level.getBlockState(pos) : state).getShape(player.getLevel(), pos, CollisionContext.of(player)).optimize();
        if(!voxelShape.isEmpty()) {
            Vec3 cam = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
            double x = pos.getX() - cam.x;
            double y = pos.getY() - cam.y;
            double z = pos.getZ() - cam.z;

            poseStack.pushPose();

            PoseStack.Pose pose = poseStack.last();
            MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.lines());
            voxelShape.forAllEdges((x1, y1, z1, x2, y2, z2) -> {
                vertexConsumer.vertex(pose.pose(), (float) (x1 + x), (float) (y1 + y), (float) (z1 + z)).color(red, green, blue, alpha).normal(pose.normal(), (float) (x2-x1), (float) (y2-y1), (float) (z2-z1)).endVertex();
                vertexConsumer.vertex(pose.pose(), (float) (x2 + x), (float) (y2 + y), (float) (z2 + z)).color(red, green, blue, alpha).normal(pose.normal(), (float) (x2-x1), (float) (y2-y1), (float) (z2-z1)).endVertex();
            });
            bufferSource.endBatch(RenderType.lines());

            poseStack.popPose();
        }
    }

    private static BlockPos getLookingAtBlockPos(Player player) {
        Level world = player.getLevel();
        float f = player.getXRot();
        float f1 = player.getYRot();
        Vec3 vec3 = player.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = 5.0;
        Vec3 vec31 = vec3.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        BlockHitResult result = world.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, ClipContext.Fluid.SOURCE_ONLY, player));
        if(result.getType() != HitResult.Type.BLOCK) {
            return null;
        } else {
            return result.getBlockPos();
        }
    }
}