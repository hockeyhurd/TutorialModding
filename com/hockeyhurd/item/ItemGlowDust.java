package com.hockeyhurd.item;

import com.hockeyhurd.tutmod.TutMod;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemGlowDust extends Item{

	public ItemGlowDust(int id) {
		super(id);
		this.setCreativeTab(TutMod.myCreativeTab);
	}
	
	public void registerIcons(IconRegister iconReg) {
		itemIcon = iconReg.registerIcon("tutmod:GlowDust");
	}
}
