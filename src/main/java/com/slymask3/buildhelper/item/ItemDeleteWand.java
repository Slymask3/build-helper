package com.slymask3.buildhelper.item;

import com.slymask3.buildhelper.init.ModItems;
import com.slymask3.buildhelper.util.Builder;
import com.slymask3.buildhelper.util.ClientHelper;
import com.slymask3.buildhelper.util.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDeleteWand extends Item {
	private static final int POS1 = 0;
	private static final int POS2 = 1;

	public ItemDeleteWand() {
		super(new Item.Properties().tab(ModItems.ModCreativeTab.instance));
	}

	public InteractionResult useOn(UseOnContext context) {
		Level world = context.getLevel();
		if(Helper.isServer(world)) {
			Player player = context.getPlayer();
			BlockPos pos = context.getClickedPos();

			ItemStack itemStack = context.getItemInHand();
			CompoundTag tag = itemStack.getOrCreateTag();
			int mode = tag.getInt("CustomModelData");

			if(mode == POS1) {
				tag.putInt("PosX",pos.getX());
				tag.putInt("PosY",pos.getY());
				tag.putInt("PosZ",pos.getZ());
				tag.putInt("CustomModelData",POS2);
				Helper.sendMessage(player,"bh.message.delete.position",pos.getX() + ", " + pos.getY() + ", " + pos.getZ(), pos, ClientHelper.Particles.DELETE_POS);
			} else if(mode == POS2) {
				int x1 = tag.getInt("PosX");
				int y1 = tag.getInt("PosY");
				int z1 = tag.getInt("PosZ");
				Builder.Multiple.setup(world,x1,y1,z1,pos.getX(),pos.getY(),pos.getZ()).setBlock(Blocks.AIR).build();
				tag = new CompoundTag();
				tag.putInt("CustomModelData",POS1);
				Helper.sendMessage(player,"bh.message.delete.done", pos, ClientHelper.Particles.DELETE_POS);
			}

			itemStack.setTag(tag);
		}
		return InteractionResult.PASS;
	}

	public void appendHoverText(ItemStack itemStack, @Nullable Level world, List<Component> list, TooltipFlag flag) {
		CompoundTag tag = itemStack.getOrCreateTag();
		int mode = tag.getInt("CustomModelData");
		if(mode == POS1) {
			list.add(Component.translatable("bh.tooltip.delete.select"));
		} else if(mode == POS2) {
			String position = tag.getInt("PosX") + ", " + tag.getInt("PosY") + ", " + tag.getInt("PosZ");
			list.add(Component.translatable("bh.tooltip.delete.position",position));
		}
	}
}