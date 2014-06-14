package com.hockeyhurd.block.machines;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import com.hockeyhurd.tutmod.TutMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGlowFurnace extends BlockContainer {

	private final boolean isActive;
	@SideOnly(Side.CLIENT)
	private Icon iconFront, iconTop;

	public BlockGlowFurnace(int id, boolean isActive, Material mat) {
		super(id, mat);
		this.setCreativeTab(TutMod.myCreativeTab);
		this.setHardness(3.5f);
		if (isActive) this.setLightValue(0.9f);
		else this.setLightValue(0f);

		this.isActive = isActive;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconReg) {
		blockIcon = iconReg.registerIcon("tutmod:GlowFurnace_side");
		iconTop = iconReg.registerIcon("tutmod:GlowFurnace_top");
		iconFront = iconReg.registerIcon((isActive ? "tutmod:GlowFurnace_front_on" : "tutmod:GlowFurnace_front_off"));
	}

	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metaData) {
		return side == 1 ? iconTop : side == 0 ? iconTop : (side == 4 ? iconFront : this.blockIcon);
	}

	public int idDropped(int amount, Random random, int par3) {
		return TutMod.glowFurnaceOff.blockID;
	}
	
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		this.setDefaultDirection(world, x, y, z);
	}
	
	private void setDefaultDirection(World world, int x, int y, int z) {
		
		if (!world.isRemote) {
			// Search blocks around placed area to determine which orientation the block should be placed about.
			int behind = world.getBlockId(x, y, z - 1);
			int front = world.getBlockId(x, y, z + 1);
			int left = world.getBlockId(x - 1, y, z);
			int right = world.getBlockId(x + 1, y, z);
			byte b0 = 3;
			
			if (Block.opaqueCubeLookup[behind] && !Block.opaqueCubeLookup[front]) b0 = 3;
			if (Block.opaqueCubeLookup[front] && !Block.opaqueCubeLookup[behind]) b0 = 2;
			if (Block.opaqueCubeLookup[left] && !Block.opaqueCubeLookup[right]) b0 = 5;
			if (Block.opaqueCubeLookup[right] && !Block.opaqueCubeLookup[left]) b0 = 4;
			
			world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		// return new TileEntityGlowFurnace();
		return null;
	}

}
