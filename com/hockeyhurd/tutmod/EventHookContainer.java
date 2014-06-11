package com.hockeyhurd.tutmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class EventHookContainer {

	private final Item helm = TutMod.glowHelmet;
	private final Item chest = TutMod.glowChestplate;
	private final Item leg = TutMod.glowLegging;
	private final Item boot = TutMod.glowBoot;

	@ForgeSubscribe
	public void onPlayerDamage(LivingHurtEvent event) {
		if (!(event.entityLiving instanceof EntityPlayer)) return;
		else {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if (player.isAirBorne && !player.capabilities.isFlying) player.capabilities.isFlying = true;
		}
	}

	/*
	 * New approach to adding effects and preventing player damage when necessary.
	 */
	@ForgeSubscribe
	public void onPlayerUpdate(LivingUpdateEvent event) {
		if (!(event.entityLiving instanceof EntityPlayer)) return;
		else {
			EntityPlayer player = (EntityPlayer) event.entityLiving;

			Item currentHelm = null;
			Item currentChest = null;
			Item currentLeg = null;
			Item currentBoot = null;

			// int preHelm = 0;
			// int preChest = 0;
			// int preLeg = 0;
			// int preBoot = 0;

			if (player.getCurrentArmor(0) != null) {
				currentBoot = player.getCurrentArmor(0).getItem();
				// preBoot = player.getCurrentArmor(0).getItemDamage();
			}

			if (player.getCurrentArmor(1) != null) {
				currentLeg = player.getCurrentArmor(1).getItem();
				// preLeg = player.getCurrentArmor(1).getItemDamage();
			}

			if (player.getCurrentArmor(2) != null) {
				currentChest = player.getCurrentArmor(2).getItem();
				// preChest = player.getCurrentArmor(2).getItemDamage();
			}

			if (player.getCurrentArmor(3) != null) {
				currentHelm = player.getCurrentArmor(3).getItem();
				// preHelm = player.getCurrentArmor(3).getItemDamage();
			}

			/*
			 * Checks if the user removes any part(s) of the armor set and if already airborne revove flying ability and make them fall! Ouch!
			 */
			else {
				if (player.isAirBorne) {
					player.capabilities.allowFlying = false;
					player.capabilities.isFlying = false;
				}
				return;
			}

			if (currentBoot == boot) {
				if (!player.isCollidedVertically) player.fallDistance = 0f;
			}

			if (currentLeg == leg) {
				// player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 2, 0));
			}

			if (currentChest == chest) {
				if (!player.isBurning()) player.removePotionEffect(Potion.fireResistance.id);
				else player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 5, 0));
			}

			if (currentHelm == helm) {
				if (player.isInWater()) player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 1, 0));
				else player.removePotionEffect(Potion.waterBreathing.id);
			}

		}

	}

	/*
	 * Event called when user hovers over my items.
	 */
	@ForgeSubscribe
	public void onItemHover(ItemTooltipEvent event) {
		int pickID = new ItemStack(TutMod.glowPickaxe, 1).itemID;
		int swordID = new ItemStack(TutMod.glowSword, 1).itemID;
		int axeID = new ItemStack(TutMod.glowAxe, 1).itemID;
		int hoeID = new ItemStack(TutMod.glowHoe).itemID;
		int shovelID = new ItemStack(TutMod.glowShovel, 1).itemID;
		int netherSoulCollectorID = new ItemStack(TutMod.netherSoulCollector, 1).itemID;

		if (event.itemStack.itemID == pickID || event.itemStack.itemID == swordID || event.itemStack.itemID == axeID || event.itemStack.itemID == hoeID || event.itemStack.itemID == shovelID) {
			event.toolTip.add("Unbreakable!");
		}
		
		else if (event.itemStack.itemID == netherSoulCollectorID) event.toolTip.add("Activate for magnet mode!");
		else return;
	}

	// Future possible commands
	/*
	 * @ForgeSubscribe public void onPlayerChat(ServerChatEvent event) { }
	 */

}
