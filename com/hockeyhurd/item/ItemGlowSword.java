package com.hockeyhurd.item;

import com.hockeyhurd.tutmod.TutMod;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;

public class ItemGlowSword extends ItemSword {

	public ItemGlowSword(int id, EnumToolMaterial toolGlow) {
		super(id, toolGlow);
		this.setCreativeTab(TutMod.myCreativeTab);
	}
	
	public void registerIcons(IconRegister iconReg) {
		itemIcon = iconReg.registerIcon("tutmod:GlowSword");
	}
	
	
	
}
