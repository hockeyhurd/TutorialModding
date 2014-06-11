package com.hockeyhurd.item;

import com.hockeyhurd.tutmod.TutMod;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;

public class ItemGlowAxe extends ItemAxe {

	public ItemGlowAxe(int id, EnumToolMaterial toolGlow) {
		super(id, toolGlow);
		this.setCreativeTab(TutMod.myCreativeTab);
	}
	
	public void registerIcons(IconRegister iconReg) {
		itemIcon = iconReg.registerIcon("tutmod:GlowAxe");
	}
	
}
