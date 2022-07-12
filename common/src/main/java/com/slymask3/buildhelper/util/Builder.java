package com.slymask3.buildhelper.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class Builder {
	public static class Multiple {
		Level world;
		final int x1, y1, z1;
		final int x2, y2, z2;
		BlockState blockState;
		private Multiple(Level world, int x1, int y1, int z1, int x2, int y2, int z2) {
			this.world = world;
			this.x1 = x1;
			this.y1 = y1;
			this.z1 = z1;
			this.x2 = x2;
			this.y2 = y2;
			this.z2 = z2;
		}
		public static Multiple setup(Level world, int x_start, int y_start, int z_start, int x_end, int y_end, int z_end) {
			return new Multiple(world, x_start, y_start, z_start, x_end, y_end, z_end);
		}
		public Multiple setBlock(Block block) {
			this.blockState = block.defaultBlockState();
			return this;
		}
		public Multiple setBlock(BlockState state) {
			this.blockState = state;
			return this;
		}
		public void build() {
			boolean x_dir = Helper.isPositive(x1 - x2);
			boolean y_dir = Helper.isPositive(y1 - y2);
			boolean z_dir = Helper.isPositive(z1 - z2);
			for(int y_cur=y1; (y_dir?y_cur>=y2:y_cur<=y2); y_cur=y_cur+(y_dir?-1:1)) {
				for(int z_cur=z1; (z_dir?z_cur>=z2:z_cur<=z2); z_cur=z_cur+(z_dir?-1:1)) {
					for(int x_cur=x1; (x_dir?x_cur>=x2:x_cur<=x2); x_cur=x_cur+(x_dir?-1:1)) {
						world.setBlockAndUpdate(new BlockPos(x_cur,y_cur,z_cur),blockState);
						world.sendBlockUpdated(new BlockPos(x_cur,y_cur,z_cur),blockState,blockState, Block.UPDATE_ALL_IMMEDIATE);
					}
				}
			}
		}
	}
}