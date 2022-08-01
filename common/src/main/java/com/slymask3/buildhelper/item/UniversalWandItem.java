package com.slymask3.buildhelper.item;

import com.slymask3.buildhelper.Common;
import com.slymask3.buildhelper.util.Builder;
import com.slymask3.buildhelper.util.ClientHelper;
import com.slymask3.buildhelper.util.Helper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class UniversalWandItem extends Item {
	private static final int BLOCK = 0;
	private static final int POS1 = 1;
	private static final int POS2 = 2;

	public UniversalWandItem(Item.Properties properties) {
		super(properties);
	}

	public InteractionResult useOn(UseOnContext context) {
		Level world = context.getLevel();
		Player player = context.getPlayer();

		if(Helper.isServer(world) && player != null) {
			Common.CONFIG.reload();

			BlockPos pos = context.getClickedPos();
			BlockState blockState = world.getBlockState(pos);

			ItemStack itemStack = context.getItemInHand();
			CompoundTag tag = itemStack.getOrCreateTag();
			int mode = tag.getInt("CustomModelData");

			if(mode == BLOCK) {
				tag.putString("Block",getBlockStateString(blockState));
				tag.putInt("CustomModelData",POS1);
				Helper.sendMessage(player,"bh.message.universal.block", ChatFormatting.AQUA + getBlockStateString(blockState), pos, ClientHelper.Particles.SELECT_BLOCK);
			} else if(mode == POS1) {
				tag.putInt("PosX",pos.getX());
				tag.putInt("PosY",pos.getY());
				tag.putInt("PosZ",pos.getZ());
				tag.putInt("CustomModelData",POS2);
				Helper.sendMessage(player,"bh.message.universal.position",ChatFormatting.AQUA + (pos.getX() + ", " + pos.getY() + ", " + pos.getZ()), pos, ClientHelper.Particles.UNIVERSAL_POS);
			} else if(mode == POS2) {
				int x1 = tag.getInt("PosX");
				int y1 = tag.getInt("PosY");
				int z1 = tag.getInt("PosZ");
				BlockState state = Helper.readBlockState(tag.getString("Block"));
				Builder.Multiple.setup(world,x1,y1,z1,pos.getX(),pos.getY(),pos.getZ()).setBlock(state).build();
				tag = new CompoundTag();
				tag.putInt("CustomModelData",BLOCK);
				Helper.sendMessage(player,"bh.message.universal.done", pos, ClientHelper.Particles.UNIVERSAL_POS);
			}

			itemStack.setTag(tag);
		}
		return InteractionResult.PASS;
	}

	public void onUseTick(Level $$0, LivingEntity $$1, ItemStack $$2, int $$3) {
		Common.LOG.info("onUseTick()");
	}

	private String getBlockStateString(BlockState state) {
		return state.toString().replaceAll("Block\\{","").replaceAll("}","");
	}

	public void appendHoverText(ItemStack itemStack, @Nullable Level world, List<Component> list, TooltipFlag flag) {
		CompoundTag tag = itemStack.getOrCreateTag();
		int mode = tag.getInt("CustomModelData");
		if(mode == BLOCK) {
			list.add(Component.translatable("bh.tooltip.universal.select"));
		} else if(mode == POS1) {
			list.add(Component.translatable("bh.tooltip.universal.block",tag.getString("Block")));
		} else if(mode == POS2) {
			list.add(Component.translatable("bh.tooltip.universal.block",tag.getString("Block")));
			String position = tag.getInt("PosX") + ", " + tag.getInt("PosY") + ", " + tag.getInt("PosZ");
			list.add(Component.translatable("bh.tooltip.universal.position",position));
		}
	}
}