package com.slymask3.buildhelper.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.slymask3.buildhelper.handler.ConfigurationHandler;
import com.slymask3.buildhelper.init.ModItems;
import com.slymask3.buildhelper.reference.Colors;
import com.slymask3.buildhelper.reference.Reference;
import com.slymask3.buildhelper.utility.Helper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDeleteWand extends ItemBase {
    public static final String[] state = new String[] {"normal", "pos1"};
	public IIcon[] icons = new IIcon[2];
	
	public ItemDeleteWand() {
		super();
	}
	
	@Override
	public void registerIcons(IIconRegister reg) {
	    for (int i = 0; i < 2; i ++) {
	        this.icons[i] = reg.registerIcon(Reference.MOD_ID + ":wandDelete_" + i);
	    }
	}
	
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}
	
	
	@Override
	public IIcon getIconFromDamage(int meta) {
	    if (meta > 1)
	        meta = 0;

	    return this.icons[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
	    return this.getUnlocalizedName();// + "_" + state[stack.getItemDamage()];
	}
	
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player) {
		if(is.stackTagCompound == null){
			is.stackTagCompound = new NBTTagCompound();
		    //LogHelper.info("Wand created. on right click");
		}

        MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, true);

        if (mop == null) {
            return is;
        }
        else {
			if (is.getItemDamage() == 0) { //POS 1 SELECTED
	        	ItemStack isn = new ItemStack(ModItems.wandDelete, 1, 1);
	        	isn.stackTagCompound = new NBTTagCompound();
	        	
	        	isn.stackTagCompound.setInteger("x1", mop.blockX);
	        	isn.stackTagCompound.setInteger("y1", mop.blockY);
	        	isn.stackTagCompound.setInteger("z1", mop.blockZ);
	        	
	        	Helper.msg(player, Colors.a+"Position 1 selected: " + isn.stackTagCompound.getInteger("x1") + ", " + isn.stackTagCompound.getInteger("y1") + ", " + isn.stackTagCompound.getInteger("z1"), Colors.a);
	            
	        	Helper.effectFull(world, "reddust", mop.blockX, mop.blockY, mop.blockZ);
	        	
	        	return isn;
	        } else if (is.getItemDamage() == 1) { //POS 2 SELECTED
	        	ItemStack isn = new ItemStack(ModItems.wandDelete, 1, 0);
	        	isn.stackTagCompound = new NBTTagCompound();
	        	
	        	isn.stackTagCompound.setInteger("x1", is.stackTagCompound.getInteger("x1"));
	        	isn.stackTagCompound.setInteger("y1", is.stackTagCompound.getInteger("y1"));
	        	isn.stackTagCompound.setInteger("z1", is.stackTagCompound.getInteger("z1"));
	        	isn.stackTagCompound.setInteger("x2", mop.blockX);
	        	isn.stackTagCompound.setInteger("y2", mop.blockY);
	        	isn.stackTagCompound.setInteger("z2", mop.blockZ);
	        	
	        	Helper.msg(player, Colors.a+"Position 2 selected: " + isn.stackTagCompound.getInteger("x2") + ", " + isn.stackTagCompound.getInteger("y2") + ", " + isn.stackTagCompound.getInteger("z2"), Colors.a);
	        	
	        	Helper.effectFull(world, "reddust", mop.blockX, mop.blockY, mop.blockZ);
	        	
	        	int amount = 0;
	        	
	        	int xDif = toPositive(isn.stackTagCompound.getInteger("x1") - isn.stackTagCompound.getInteger("x2"));
	        	int yDif = toPositive(isn.stackTagCompound.getInteger("y1") - isn.stackTagCompound.getInteger("y2"));
	        	int zDif = toPositive(isn.stackTagCompound.getInteger("z1") - isn.stackTagCompound.getInteger("z2"));
	        	
	        	int x = isn.stackTagCompound.getInteger("x1");
	            int y = isn.stackTagCompound.getInteger("y1");
	            int z = isn.stackTagCompound.getInteger("z1");
	       
	            int bx = isn.stackTagCompound.getInteger("x1");
	            int bz = isn.stackTagCompound.getInteger("z1");
	     
	            for (int i=0; i<yDif+1; i++) {
	                for (int j=0; j<zDif+1; j++) {
	                    for (int k=0; k<xDif+1; k++) {
	                        
	                        world.setBlock(x, y, z, Blocks.air, 0, 2);
	                        amount++;
	                        
	                        if(isPositive(isn.stackTagCompound.getInteger("x1") - isn.stackTagCompound.getInteger("x2"))) {
	    	                	x--;
	    	                } else {
	    	                	x++;
	    	                }
	                    }
	                    x = bx;
	                    
	                    if(isPositive(isn.stackTagCompound.getInteger("z1") - isn.stackTagCompound.getInteger("z2"))) {
		                	z--;
		                } else {
		                	z++;
		                }
	                }
	                z = bz;
	                x = bx;
	                
	                if(isPositive(isn.stackTagCompound.getInteger("y1") - isn.stackTagCompound.getInteger("y2"))) {
	                	y--;
	                } else {
	                	y++;
	                }
	            }
	        	
	        	Helper.msg(player, Colors.b+"Deleted " + amount + " blocks.", Colors.b);
	        	
	        	Helper.sound(world, ConfigurationHandler.sound, mop.blockX, mop.blockY, mop.blockZ);
	        	
	            return isn;
	        }
			return is;
        }
    }

	private int toPositive(int i) {
		if (i<0) {
			return -i;
		} else {
			return i;
		}
	}

	private boolean isPositive(int i) {
		if (i<0) {
			return false;
		} else {
			return true;
		}
	}
	
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
		if(is.getItemDamage() == 0) {
			list.add("Select Position 1 with right-click.");
		}
		if(is.getItemDamage() >= 1 && is.stackTagCompound != null) {
			list.add("Pos 1: " + is.stackTagCompound.getInteger("x1") + ", " + is.stackTagCompound.getInteger("y1") + ", " + is.stackTagCompound.getInteger("z1"));
		}
	}
}
