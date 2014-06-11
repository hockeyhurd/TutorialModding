package com.hockeyhurd.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.hockeyhurd.tutmod.TutMod;

public class ItemDiamondFusedNetherStar extends Item {

	public ItemDiamondFusedNetherStar(int id) {
		super(id);
		this.setCreativeTab(TutMod.myCreativeTab);
	}
	
	public void registerIcons(IconRegister iconReg){
		itemIcon = iconReg.registerIcon("tutmod:DiamondNetherStarIngot");
	}
	
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
}
