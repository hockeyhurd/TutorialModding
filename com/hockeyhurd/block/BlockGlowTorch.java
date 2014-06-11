package com.hockeyhurd.block;

import java.util.Random;

import net.minecraft.block.BlockTorch;
import net.minecraft.client.renderer.texture.IconRegister;

import com.hockeyhurd.tutmod.TutMod;

public class BlockGlowTorch extends BlockTorch {

	public BlockGlowTorch(int par1) {
		super(par1);
		this.setCreativeTab(TutMod.myCreativeTab);
		this.setLightValue(1.0f);
		this.setResistance(5f);
		this.setStepSound(soundGlassFootstep);
	}
	
	public void registerIcons(IconRegister iconReg) {
		blockIcon = iconReg.registerIcon("tutmod:torch_on");
	}
	
	public int quantityDropped(Random rand) {
		return 0;
	}

}
