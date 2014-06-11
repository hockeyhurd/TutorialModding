package com.hockeyhurd.item;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import com.hockeyhurd.tutmod.TutMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemNetherSoulCollector extends Item {

	private static boolean isActive;
	private Icon iconOverlay;

	public ItemNetherSoulCollector(int id, boolean state) {
		super(id);
		isActive = state;
		this.setCreativeTab(TutMod.myCreativeTab);
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
		this.canRepair = false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconReg) {
		itemIcon = iconReg.registerIcon("tutmod:NetherSoulCollector");
		iconOverlay = iconReg.registerIcon("tutmod:NetherSoulCollector_overlay");
	}

	@Override
	public Icon getIcon(ItemStack stack, int renderPass) {
		if ((stack.getItemDamage() == 0 || renderPass != 1) /*&& !isActive*/) return this.itemIcon;
		else return iconOverlay;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		if (stack.getItemDamage() == 1) return true;
		else return false;
	}

	public static void updateItemState(boolean state) {
		isActive = state;
	}

	public static boolean getActiveState() {
		return isActive;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		if (!(e instanceof EntityPlayer)) return;
		if (/*stack.getItemDamage() == 0 ||*/ !isActive) return;
		EntityPlayer player = (EntityPlayer) e;
		scanForEntities(player.worldObj, player, 10.0d);
	}

	private void scanForEntities(World world, EntityPlayer player, double dist) {
		List list = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(player.posX - dist, player.posY - dist, player.posZ - dist, player.posX + dist, player.posY + dist, player.posZ + dist));
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			EntityItem item = (EntityItem) iter.next();
			if (!checkInvForRoom(item.getEntityItem(), player)) continue;
			// if (item.isAirBorne || !item.onGround) continue;
			if (item.delayBeforeCanPickup > 0) item.delayBeforeCanPickup = 1;
			if (player.getDistanceToEntity(item) < 1.5d) continue;
			teleportEntityToPlayer(item, player);
			break;
		}
	}

	// Move entity to player.
	private void teleportEntityToPlayer(Entity item, EntityPlayer player) {
		player.getLookVec();
		double speed = 0.1d;
		double x = player.posX + player.getLookVec().xCoord * speed;
		double y = player.posY - player.height / 2f;
		double z = player.posZ + player.getLookVec().zCoord * speed;
		item.setPosition(x, y, z);
	}
	
	private boolean checkInvForRoom(ItemStack stack, EntityPlayer player) {
		// Get stack size
		int remaining = stack.stackSize;
		
		// Check itemStacks in player inventyor.
		for (ItemStack itemStack : player.inventory.mainInventory) {
			// if null, ignore
			if (itemStack == null) continue;
			// if already have item
			if (itemStack.getItem() == stack.getItem() && itemStack.getItemDamage() == stack.getItemDamage()) return true;
			else {
				// Check for how large itemStack is and how much room we will need for it
				int count = stack.stackSize;
				while (count < itemStack.getMaxStackSize()) {
					count++;
					remaining--;
					if (remaining == 0) return true;
				}
			}
		}
		
		// Scan through each slot in player's inventory looking for a spot to put the itemStack.
		for (int slot = 0; slot < player.inventory.mainInventory.length; slot++) {
			if (player.inventory.mainInventory[slot] == null) return true;
		}
		
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (world.isRemote) return itemStack;

		if (player.isSneaking()) {
			if (isActive) return itemStack; 
			player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
			itemStack.setItemDamage(1);
			isActive = true;
			
			String msg = "Magnet mode activated!";
			EnumChatFormatting color = EnumChatFormatting.GOLD;
			player.sendChatToPlayer(getFormatMsg(new ChatMessageComponent(), msg, color));
		}
		else {
			if (!isActive) return itemStack;
			// itemStack.setItemDamage(itemStack.getItemDamage() == 0 ? 1 : 0);
			itemStack.setItemDamage(0);
			isActive = false;
			
			String msg = "Magnet mode deactivated!";
			EnumChatFormatting color = EnumChatFormatting.GOLD;
			player.sendChatToPlayer(getFormatMsg(new ChatMessageComponent(), msg, color));
		}

		return itemStack;
	}
	
	private ChatMessageComponent getFormatMsg(ChatMessageComponent comp, String msg, EnumChatFormatting color) {
		comp.setColor(color);
		comp.addText(msg);
		return comp;
	}

}
