package com.hockeyhurd.item;

import java.awt.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.hockeyhurd.tutmod.TutMod;
import com.hockeyhurd.util.Waila;

public class ItemGlowPickaxe extends ItemPickaxe {

	// Set the block to be placed and get it's blockID.
	private final Block torch = TutMod.glowTorch;
	private final int torchID = torch.blockID;

	public ItemGlowPickaxe(int id, EnumToolMaterial toolGlow) {
		super(id, toolGlow);
		this.setCreativeTab(TutMod.myCreativeTab);
	}

	public void registerIcons(IconRegister iconReg) {
		itemIcon = iconReg.registerIcon("tutmod:GlowPickaxe");
	}

	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		new Waila(itemStack, world, entityPlayer, torch, true, false).getBlockLookingAt();
		return itemStack;
	}

	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("Tooltip stuff goes here");
	}

}
