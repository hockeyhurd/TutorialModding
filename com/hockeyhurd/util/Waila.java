/* Note:
 * 	This class has nothing to do and/or
 * 	is associated with the mod WAILA.
 */

package com.hockeyhurd.util;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.hockeyhurd.tutmod.TutMod;

public class Waila {

	private ItemStack stack;
	private World world;
	private EntityPlayer player;
	private Block block;
	private boolean placeBlock;
	private boolean shiftClick;

	public Waila(ItemStack itemStack, World world, EntityPlayer entityPlayer, Block block, boolean placeBlock, boolean shiftClick) {
		this.stack = itemStack;
		this.world = world;
		this.player = entityPlayer;
		this.block = block;
		this.placeBlock = placeBlock; // TODO: implement some sort if placeBlock
										// = false, return block looking at.
		this.shiftClick = shiftClick;
	}

	public ItemStack getBlockLookingAt() {
		if (stack.getItemDamage() >= 0) {
			float f = 1.0F;

			// Get the avgCurrent rotational pitch (left, right)
			float rotPitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;

			// Get the avgCurrent rotational yaw (up, down)
			float rotYaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;

			// Get the avgCurrent position X.
			double posX = player.prevPosX + (player.posX - player.prevPosX) * (double) f;

			// Get the avgCurrent position Y and offset the yCorrd to the
			// player's head level (camera level).
			double posY = (player.prevPosY + (player.posY - player.prevPosY) * (double) f + 1.6200000000000001D) - (double) player.yOffset;

			// Get the avgCurrent position Z.
			double posZ = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;

			// Get the vector represented by the combination of the three above
			// world coordinates.
			Vec3 vec3d = Vec3.createVectorHelper(posX, posY, posZ);

			// Get the -rotYaw and represent deltaDegrees about the y-axis;
			// calculate cos('x').
			float f3 = MathHelper.cos(-rotYaw * 0.01745329F - 3.141593F);

			// Get the -rotYaw and represent the deltaDegrees about the y-axis;
			// calculate sin('x').
			float f4 = MathHelper.sin(-rotYaw * 0.01745329F - 3.141593F);

			// Get the -rotPitch and represent the deltaDegrees about the
			// x-axis; calculate cos('y').
			float f5 = -MathHelper.cos(-rotPitch * 0.01745329F);

			// Get the -rotPitch and represent the deltaDegrees about the
			// x-axis; calculate sin('y').
			float f6 = MathHelper.sin(-rotPitch * 0.01745329F);

			// Get absoulute 'x' value calculated via cos('x') and sin('x').
			float f7 = f4 * f5;
			// Represent null deltaZ.
			float f8 = f6;
			// Get the absolute 'y' value calculated via cos('y') and sin('y').
			float f9 = f3 * f5;
			// Get the distance the vector ray should extend to.
			// double d3 = 5000D;
			double d3 = 5000D;

			// Get the above calculations and represent this in a vector3
			// format.
			Vec3 vec3d1 = vec3d.addVector((double) f7 * d3, (double) f8 * d3 + 1, (double) f9 * d3);

			/*
			 * Combine vector rotations and vector absolute world positions and
			 * throw it through a vector ray to calculate the direction and
			 * block the entity (player) is currently looking at in the given
			 * instance.
			 */
			MovingObjectPosition movingObjectPos = world.rayTraceBlocks_do_do(vec3d, vec3d1, false, true);

			// Make sure there is no possibility the entity (player) is not
			// looking at 'null'.
			if (movingObjectPos == null) return stack;

			// Check if the vector ray intersects with some sort of TILE
			if (movingObjectPos.typeOfHit == EnumMovingObjectType.TILE) {

				// Get the posiotion of the TILE intersected as represented in
				// 3D space.
				int xx = movingObjectPos.blockX;
				int yy = movingObjectPos.blockY;
				int zz = movingObjectPos.blockZ;

				// Get the side of which the vector ray intersects with.
				int sideHit = movingObjectPos.sideHit;
				System.out.println("Side: " + sideHit);

				if (placeBlock) {
					/*
					 * Place block (torch) accordingly to the player
					 * perspective. Makes sure the torch is place directly
					 * in-front of them. SIDE NOTE: sideHit = 0 means they are
					 * looking at the under side of a block and therefore make
					 * sure the torch cannot be placed.
					 */
					if (sideHit == 0) return stack;
					else if (sideHit == 1) setBlock(xx, yy + 1, zz);
					else if (sideHit == 2) setBlock(xx, yy, zz - 1);
					else if (sideHit == 3) setBlock(xx, yy, zz + 1);
					else if (sideHit == 4) setBlock(xx - 1, yy, zz);
					else if (sideHit == 5) setBlock(xx + 1, yy, zz);

				}
				
				else if (!placeBlock) {
					// Check if item used == glowHoe
					if ( (stack.itemID == new ItemStack(TutMod.glowHoe, 1).itemID) && sideHit == 1 ) {

						// Get Block and block ids'
						Block tilDirt = Block.tilledField;
						int dirtID = Block.dirt.blockID;
						int grassID = Block.grass.blockID;
						int tilDirtID = tilDirt.blockID;
						int currentID = world.getBlockId(xx, yy, zz);
						
						// Get the block the player is currently looking at
						if (currentID == dirtID || currentID == grassID || currentID == tilDirtID) {
							
							/* if they shift click, till a 9 * 9 area
							 * else, hoe the block they are looking at
							 */
							if (shiftClick) tillLand(xx, yy, zz);
							else world.setBlock(xx, yy, zz, tilDirtID);
							
							// Play world sound for all to hear :)
							world.playSoundEffect( (double) (xx + 0.5), (double) (yy + 0.5), (double) (zz + 0.5), tilDirt.stepSound.getStepSound(), 
									(tilDirt.stepSound.getVolume() + 1.0f) / 2.0f, tilDirt.stepSound.getPitch() * 0.8f );
						}
						
						/* If the block the user is looking at cannot be tilled,
						 * don't do anything! (yet)
						 */
						else System.out.println("Block could not be tilled!");
					}
				}
			}

			stack.setItemDamage(0);
		}
		return stack;
	}
	
	public void setShiftClick(boolean state) {
		this.shiftClick = state;
	}
	
	private void setBlock(int x, int y, int z) {
		setBlock(x, y, z, this.block);
	}

	private void setBlock(int x, int y, int z, Block block) {

		// How far should the player be able to 'reach'.
		int deltaPos = 4;
		boolean xCheck = false, yCheck = false, zCheck = false;

		/*
		 * Check the reach distance relative to the player and desired block
		 * placement.
		 */
		if ((x - deltaPos) < player.posX && player.posX < (x + deltaPos)) xCheck = true;
		if ((y - deltaPos) < player.posY && player.posY < (y + deltaPos)) yCheck = true;
		if ((z - deltaPos) < player.posZ && player.posZ < (z + deltaPos)) zCheck = true;

		/*
		 * If said block is something and the player can reach the block they
		 * are looking at, place the said block.
		 */
		if (block != null && xCheck && yCheck && zCheck) world.setBlock(x, y, z, block.blockID);
		else return;
	}

	private void tillLand(int x, int y, int z) {
		// Get all needed block ids'
		Block tilDir = Block.tilledField;
		int tilledDirtID = tilDir.blockID;
		int dirtID = Block.dirt.blockID;
		int grassID = Block.grass.blockID;
		
		/* Scan through blocks on the x and y axis,
		 * check if they can be tilled, 
		 * till the land!
		 */
		for (int xx = x-1; xx < x+2; xx++) {
			for (int zz = z-1; zz < z+2; zz++) {
				int currentBlock = world.getBlockId(xx, y, zz);
				
				// Note: Last check below shouldn't be necessary as it should already be tilled! (in theory).
				if (currentBlock == dirtID || currentBlock == grassID /*|| currentBlock == tilledDirtID*/) { 
					setBlock(xx, y, zz, tilDir);
				}
			}
		}
	}
	
}
