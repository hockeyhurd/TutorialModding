package com.hockeyhurd.creativetab;

import com.hockeyhurd.tutmod.TutMod;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabTut extends CreativeTabs {

	public CreativeTabTut(int par1, String par2Str) {
		super(par1, par2Str);
	}

	public int getTabIconItemIndex() {
		// Use one of Minecraft's items as the creative tab icon.
		// return Item.diamond.itemID;
		
		// Use one of mine icons.
		return TutMod.glowSword.itemID;
	}
	
	public String getTranslatedTabLabel() {
		// What the player will see for Creative Tab name.
		return "Tutorial Mod";
	}
	
}
