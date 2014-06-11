package com.hockeyhurd.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

import com.hockeyhurd.tutmod.TutMod;

public class ItemGlowIngot extends Item {

	public ItemGlowIngot(int id) {
		super(id);
		this.setCreativeTab(TutMod.myCreativeTab);
	}
	
	public void registerIcons(IconRegister iconReg) {
		itemIcon = iconReg.registerIcon("tutmod:GlowIngot");
	}
	
}
