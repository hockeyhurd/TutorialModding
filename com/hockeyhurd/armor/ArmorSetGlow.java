package com.hockeyhurd.armor;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.hockeyhurd.tutmod.TutMod;

public class ArmorSetGlow extends ItemArmor {

	public EnumArmorMaterial material;
	public String nameToAdd;
	private String tut = "tutmod:";
	
	public ArmorSetGlow(int id, EnumArmorMaterial glowArmorMat, int par3, int durabillity, String pathMat) {
		super(id, glowArmorMat, par3, durabillity);
		this.setCreativeTab(TutMod.myCreativeTab);
		this.maxStackSize = 1;
		this.material = glowArmorMat;
		this.canRepair = true;
		this.setMaxDamage(glowArmorMat.getDurability(durabillity));
		glowArmorMat.getDamageReductionAmount(durabillity);
		
		nameToAdd = pathMat;
	}
	
	public String getArmorTexture(ItemStack stack, Entity e, int slot, int layer) {
		if (stack.toString().contains("leggings")) {
			return (tut + nameToAdd + "_2.png");
		}
		
		if (stack.toString().contains("Leggings") && itemID == TutMod.glowLegging.itemID) {
			return (tut + nameToAdd + "_2.png");
		}
		
		return (tut + nameToAdd + "_1.png");
	}
	
	public void registerIcons(IconRegister iconReg) {
		if (itemID == TutMod.glowHelmet.itemID) itemIcon = iconReg.registerIcon(tut + "GlowHelmet");
		if (itemID == TutMod.glowChestplate.itemID) itemIcon = iconReg.registerIcon(tut + "GlowChestplate");
		if (itemID == TutMod.glowLegging.itemID) itemIcon = iconReg.registerIcon(tut + "GlowLegging");
		if (itemID == TutMod.glowBoot.itemID) itemIcon = iconReg.registerIcon(tut + "GlowBoot");
	}
	
	/*public boolean hasEffect(ItemStack stack) {
		return false;
	}*/

}
