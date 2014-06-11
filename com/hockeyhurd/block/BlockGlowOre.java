package com.hockeyhurd.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;

import com.hockeyhurd.tutmod.TutMod;

public class BlockGlowOre extends Block {

	public BlockGlowOre(int id, Material glass) {
		super(id, glass);
		this.setCreativeTab(TutMod.myCreativeTab);
		this.setLightValue(0.3f);
		this.setHardness(10);
		this.setResistance(5);
	}
	
	public void registerIcons(IconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon("tutmod:GlowOre");
	}
	
	public int idDropped(int par1, Random rand, int par3) {
		// Return item
		// return TutMod.OreGlowRaw.itemID;
		
		// Return itself (Block)
		return TutMod.glowOre.blockID;
	}
	
	public int quantityDropped(Random rand) {
		return 1;
	}
	
}
