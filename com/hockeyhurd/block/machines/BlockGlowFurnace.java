package com.hockeyhurd.block.machines;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import codechicken.lib.math.MathHelper;

import com.hockeyhurd.tileentity.TileEntityGlowFurnace;
import com.hockeyhurd.tutmod.TutMod;

import cpw.mods.fml.common.network.FMLNetworkHandler;
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
		// Get the icon needed based on the faces of given side.
		return side == metaData ? this.iconFront : (side == 0 ? this.iconTop : (side == 1 ? this.iconTop : this.blockIcon));
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
	
	// Sets meta data based on player's rotation about the y-axis.
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int dir = MathHelper.floor_double( (double) (player.rotationYaw * 4.0f / 360.0f) + 0.5d) & 3;
		
		if (dir == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if (dir == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if (dir == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if (dir == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		
		if (stack.hasDisplayName()) ((TileEntityGlowFurnace) world.getBlockTileEntity(x, y, z)).setGuiDisplayName(stack.getDisplayName());
		
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(player, TutMod.instance, TutMod.guiIDGlowFurnace, world, x, y, z);
		}
		
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityGlowFurnace();
	}

}
