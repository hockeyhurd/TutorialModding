package com.hockeyhurd.tutmod;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.LogWrapper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.hockeyhurd.armor.ArmorSetGlow;
import com.hockeyhurd.block.BlockGlowOre;
import com.hockeyhurd.block.BlockGlowRlock;
import com.hockeyhurd.block.BlockGlowTorch;
import com.hockeyhurd.block.machines.BlockGlowFurnace;
import com.hockeyhurd.creativetab.CreativeTabTut;
import com.hockeyhurd.item.ItemDiamondFusedNetherStar;
import com.hockeyhurd.item.ItemGlowAxe;
import com.hockeyhurd.item.ItemGlowDust;
import com.hockeyhurd.item.ItemGlowHoe;
import com.hockeyhurd.item.ItemGlowIngot;
import com.hockeyhurd.item.ItemGlowPickaxe;
import com.hockeyhurd.item.ItemGlowShovel;
import com.hockeyhurd.item.ItemGlowSword;
import com.hockeyhurd.item.ItemNetherSoulCollector;
import com.hockeyhurd.item.ItemOreGlowRaw;
import com.hockeyhurd.worldgen.OreGlowWorldgen;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "TutMod", name = "Tutorial Mod", version = "v0.08")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class TutMod {

	@SidedProxy(clientSide = "com.hockeyhurd.client.ClientProxy", serverSide = "com.hockeyhurd.tutmod.CommonProxy")
	public static CommonProxy proxy;

	// Creative Tab
	public static CreativeTabs myCreativeTab = new CreativeTabTut(CreativeTabs.getNextID(), "TutMod");

	// Armor mat
	public static EnumArmorMaterial glowArmorMat = EnumHelper.addArmorMaterial("GLOWARMOR", 100, new int[] {
			3, 8, 6, 3
	}, 25);

	// Tool Mat
	public static EnumToolMaterial toolGlow = EnumHelper.addToolMaterial("GLOW", 3, 2000, 10.0f, 5.0f, 30);
	public static EnumToolMaterial toolGlowUnbreakable = EnumHelper.addToolMaterial("GLOWUNBREAKING", 3, -1, 10.0f, 5.0f, 30);

	// Blocks
	public static Block glowRock = new BlockGlowRlock(1800, Material.glass).setUnlocalizedName("GlowRock");
	public static Block glowOre = new BlockGlowOre(1807, Material.rock).setUnlocalizedName("Ore Glow");
	public static Block glowTorch = new BlockGlowTorch(1808).setUnlocalizedName("GlowTorch");
	
	// Machines
	public static Block glowFurnaceOff = new BlockGlowFurnace(1816, false, Material.rock).setUnlocalizedName("GlowFurnaceOff");
	public static Block glowFurnaceOn = new BlockGlowFurnace(1817, true, Material.rock).setUnlocalizedName("GlowFurnaceOn");

	// Items
	public static Item glowDust = new ItemGlowDust(1801).setUnlocalizedName("GlowDust");
	public static Item OreGlowRaw = new ItemOreGlowRaw(1808).setUnlocalizedName("OreGlowRaw");
	public static Item diamondFusedNetherStar = new ItemDiamondFusedNetherStar(1809).setUnlocalizedName("DiamondFusedNetherStar");
	
	// TODO: Localize this item!
	public static Item netherSoulCollector = new ItemNetherSoulCollector(1815, false).setUnlocalizedName("NetherSoulCollector");

	// Worldgen
	public static OreGlowWorldgen worldgenGlowOre = new OreGlowWorldgen();

	// Ingots
	public static Item glowIngot = new ItemGlowIngot(1810).setUnlocalizedName("GlowIngot");

	// Armor set:
	public static Item glowHelmet = new ArmorSetGlow(1811, glowArmorMat, 0, 0, "Glow").setUnlocalizedName("GlowHelm");
	public static Item glowChestplate = new ArmorSetGlow(1812, glowArmorMat, 0, 1, "Glow").setUnlocalizedName("GlowChestplate");
	public static Item glowLegging = new ArmorSetGlow(1813, glowArmorMat, 0, 2, "Glow").setUnlocalizedName("GlowLeggings");
	public static Item glowBoot = new ArmorSetGlow(1814, glowArmorMat, 0, 3, "Glow").setUnlocalizedName("GlowBoots");

	// Unbreakable tool set:
	public static Item glowPickaxe = new ItemGlowPickaxe(1802, toolGlowUnbreakable).setUnlocalizedName("GlowPickaxe");
	public static Item glowSword = new ItemGlowSword(1806, toolGlowUnbreakable).setUnlocalizedName("GlowSword");
	public static Item glowShovel = new ItemGlowShovel(1803, toolGlowUnbreakable).setUnlocalizedName("GlowShovel");
	public static Item glowHoe = new ItemGlowHoe(1804, toolGlowUnbreakable).setUnlocalizedName("GlowHoe");
	public static Item glowAxe = new ItemGlowAxe(1805, toolGlowUnbreakable).setUnlocalizedName("GlowAxe");

	// Other tool set:

	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderInformation();
	}

	public TutMod() {

		// Tick registering
		MinecraftForge.EVENT_BUS.register(new ServerTickHandler());
		TickRegistry.registerTickHandler(new ServerTickHandler(), Side.CLIENT);

		// Event regustering
		MinecraftForge.EVENT_BUS.register(new EventHookContainer());

		// Worldgen
		GameRegistry.registerWorldGenerator(worldgenGlowOre);

		registerBlocks();
		addOreDict();
		addCraftingRecipes();
		addNames();
		pulverizeRecipes();
		// inductionSmelterRecipes();

		LogWrapper.log(Level.INFO, "TutMod: Initialized successfully!");
	}

	private void addNames() {
		// Blocks
		LanguageRegistry.addName(glowRock, "Glowrock");
		LanguageRegistry.addName(glowOre, "Ore Glow");
		LanguageRegistry.addName(glowTorch, "Glow Torch");

		// Items
		LanguageRegistry.addName(glowDust, "GlowDust");
		LanguageRegistry.addName(OreGlowRaw, "Raw Ore Glow");
		LanguageRegistry.addName(diamondFusedNetherStar, "Encaptured Soul of The Nether");
		LanguageRegistry.addName(glowIngot, "Glow Ingot");
		LanguageRegistry.addName(netherSoulCollector, "Soul Collector of The Nether");

		// Glow Toolset
		// LanguageRegistry.addName(glowPickaxe, "Glow Pickaxe");
		LanguageRegistry.addName(glowPickaxe, "Pickaxe of The Lost Souls");
		LanguageRegistry.addName(glowShovel, "Glow Shovel");
		LanguageRegistry.addName(glowHoe, "Glow Hoe");
		LanguageRegistry.addName(glowAxe, "Glow Axe");
		LanguageRegistry.addName(glowSword, "Glow Sword");

		// Glow Armor set
		LanguageRegistry.addName(glowHelmet, "Glow Helmet");
		LanguageRegistry.addName(glowChestplate, "Glow Chestplate");
		LanguageRegistry.addName(glowLegging, "Glow Leggings");
		LanguageRegistry.addName(glowBoot, "Glow Boots");
	}

	private void addCraftingRecipes() {
		// General purpose items.
		final String STICK = "stickWood";
		
		// Crafting Recipes
		// With a shape
		/*
		 * GameRegistry.addRecipe(new ItemStack(glowRock, 1), new Object[] { "   ", "xx ", "xx ", 'x', glowDust });
		 */

		// shapeless example, THINK: GlowStone
		GameRegistry.addRecipe(new ItemStack(glowRock, 1), "xx", "xx", 'x', glowDust);

		// Smelting recipe.
		GameRegistry.addSmelting(glowOre.blockID, new ItemStack(glowDust, 1), 100f);

		// Glow Ingot recipe.
		GameRegistry.addRecipe(new ItemStack(glowIngot, 1), "xy", 'x', Item.ingotGold, 'y', glowDust);
		GameRegistry.addRecipe(new ItemStack(glowIngot, 1), new Object[] {
			"xyy", "yyy", "yyy", 'x', glowDust, 'y', Item.ingotIron
		});

		// DiamondNetherStarIngot recipe
		GameRegistry.addRecipe(new ItemStack(diamondFusedNetherStar, 1), new Object[] {
				"xyx", "yzy", "xyx", 'x', Item.diamond, 'y', glowIngot, 'z', Item.netherStar
		});
		
		// Crafting the NetherSoulCollector
		GameRegistry.addRecipe(new ItemStack(netherSoulCollector, 1), new Object[] {
			"xyx", "yzy", "xyx", 'x', glowIngot, 'y', Item.ingotGold, 'z', diamondFusedNetherStar
		});

		// Craft the pick
		ItemStack pick = new ItemStack(glowPickaxe, 1);
		pick.addEnchantment(Enchantment.efficiency, 5);
		pick.addEnchantment(Enchantment.fortune, 4);

		GameRegistry.addRecipe(new ShapedOreRecipe(pick, new Object[] {
				"yxy", " z ", " z ", 'x', diamondFusedNetherStar, 'y', glowIngot, 'z', STICK
		}));
		
		// Crafting the sword
		ItemStack SWORD = new ItemStack(glowSword, 1);
		SWORD.addEnchantment(Enchantment.fireAspect, 1);
		SWORD.addEnchantment(Enchantment.sharpness, 5);
		SWORD.addEnchantment(Enchantment.looting, 4);
		GameRegistry.addRecipe(new ShapedOreRecipe(SWORD, new Object[] {
				" w ", "yxy", " z ", 'w', glowIngot, 'x', diamondFusedNetherStar, 'y', Item.diamond, 'z', STICK
		}));

		// Crafting the axe
		ItemStack AXE = new ItemStack(glowAxe, 1);
		AXE.addEnchantment(Enchantment.efficiency, 5);
		GameRegistry.addRecipe(new ShapedOreRecipe(AXE, new Object[] {
				"wx ", "xy ", " y ", 'w', diamondFusedNetherStar, 'x', glowIngot, 'y', STICK,
		}));

		// Crafting the glowHoe
		ItemStack HOE = new ItemStack(glowHoe, 1);
		GameRegistry.addRecipe(new ShapedOreRecipe(HOE, new Object[] {
				"wx ", "yz ", " z ", 'w', glowIngot, 'x', diamondFusedNetherStar, 'y', Item.diamond, 'z', STICK
		}));

		// Crafting the glow Shovel
		ItemStack SHOVEL = new ItemStack(glowShovel, 1);
		SHOVEL.addEnchantment(Enchantment.efficiency, 5);
		GameRegistry.addRecipe(new ShapedOreRecipe(SHOVEL, new Object[] {
				" x ", " y ", " y ", 'x', diamondFusedNetherStar, 'y', STICK
		}));

		// Crafting the glow boots
		ItemStack BOOT = new ItemStack(glowBoot, 1);
		GameRegistry.addRecipe(BOOT, new Object[] {
				"x x", "x x", 'x', glowIngot
		});

		// Crafting the glow leggings
		ItemStack LEGGINGS = new ItemStack(glowLegging, 1);
		GameRegistry.addRecipe(LEGGINGS, new Object[] {
				"xxx", "x x", "x x", 'x', glowIngot
		});

		// Crafting the glow chestplate
		ItemStack CHESTPLATE = new ItemStack(glowChestplate, 1);
		GameRegistry.addRecipe(CHESTPLATE, new Object[] {
				"x x", "xyx", "xxx", 'x', glowIngot, 'y', diamondFusedNetherStar
		});

		// Crafting the glow helmet
		ItemStack HELMET = new ItemStack(glowHelmet, 1);
		GameRegistry.addRecipe(HELMET, new Object[] {
				"xxx", "x x", 'x', glowIngot
		});
	}

	private void addOreDict() {
		OreDictionary.registerOre("oreGlow", glowOre);
		OreDictionary.registerOre("dustGlow", glowDust);
		OreDictionary.registerOre("ingotGlow", glowIngot);
	}

	private void registerBlocks() {
		GameRegistry.registerBlock(glowRock, "GlowRock");
		GameRegistry.registerBlock(glowOre, "GlowOre");
		GameRegistry.registerBlock(glowTorch, "GlowTorch");
		GameRegistry.registerBlock(glowFurnaceOff, "GlowFuranceOff");
		GameRegistry.registerBlock(glowFurnaceOn, "GlowFurnaceOn");
	}

	private void pulverizeRecipes() {
		// Code performing glowOre into 2*glowDust via Thermal Expansion
		// Pulverizer.
		NBTTagCompound toSend = new NBTTagCompound();
		toSend.setInteger("energy", 1000);
		toSend.setCompoundTag("input", new NBTTagCompound());
		toSend.setCompoundTag("primaryOutput", new NBTTagCompound());

		ItemStack inputStack = new ItemStack(glowOre, 1);
		inputStack.writeToNBT(toSend.getCompoundTag("input"));

		ItemStack outputStack = new ItemStack(glowDust, 2);
		outputStack.writeToNBT(toSend.getCompoundTag("primaryOutput"));
		FMLInterModComms.sendMessage("ThermalExpansion", "PulverizerRecipe", toSend);

	}

	private void inductionSmelterRecipes() {
		/*
		 * Example, NOTE: currently might not work!
		 * 
		 * NBTTagCompound toSend = new NBTTagCompound(); toSend.setInteger("energy", 1000); toSend.setCompoundTag("primaryInput", new NBTTagCompound()); toSend.setCompoundTag("secondaryInput", new NBTTagCompound()); toSend.setCompoundTag("primaryOutput", new NBTTagCompound());
		 * 
		 * new ItemStack(glowDust, 1).writeToNBT(toSend.getCompoundTag("input")); new ItemStack(glowIngot, 2).writeToNBT(toSend.getCompoundTag("primaryOutput")); FMLInterModComms.sendMessage("ThermalExpansion", "SmelterRecipe", toSend);
		 */
	}

}
