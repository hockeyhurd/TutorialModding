package com.hockeyhurd.item;

import com.hockeyhurd.tutmod.TutMod;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;

public class ItemGlowShovel extends ItemSpade {

	public ItemGlowShovel(int id, EnumToolMaterial toolGlow) {
		super(id, toolGlow);
		this.setCreativeTab(TutMod.myCreativeTab);
	}
	
	public void registerIcons(IconRegister iconReg) {
		itemIcon = iconReg.registerIcon("tutmod:GlowShovel");
	}

}
