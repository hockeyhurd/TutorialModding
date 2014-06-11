package com.hockeyhurd.block;

import com.hockeyhurd.tutmod.TutMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;

public class BlockGlowRlock extends Block {

	public BlockGlowRlock(int id, Material glass) {
		super(id, glass);
		this.setCreativeTab(TutMod.myCreativeTab);
		this.setLightValue(0.8f);
		this.setHardness(0.5f);
		this.setResistance(5);
		this.setStepSound(soundGlassFootstep);
	}
	
	public void registerIcons(IconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon("tutmod:GlowRock");
	}

}
