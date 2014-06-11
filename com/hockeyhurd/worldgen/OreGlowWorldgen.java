package com.hockeyhurd.worldgen;

import java.util.Random;

import com.hockeyhurd.tutmod.TutMod;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class OreGlowWorldgen implements IWorldGenerator {

	private final int chunkSize = 16;
	private final int chanceOfSpawn = 8;
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
			case -1 : 
				generateNether(world, random, chunkX * 16, chunkZ * 16);
				break;
			case 0 : 
				generateSurface(world, random, chunkX * 16, chunkZ * 16);
				break;
			case 1 :
				generateEnd(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	private void generateNether(World world, Random random, int blockX, int blockZ) {
		
	}

	private void generateSurface(World world, Random random, int blockX, int blockZ) {
		addOreSpawn(TutMod.glowOre, world, random, blockX, blockZ, chunkSize, chunkSize, 6 + random.nextInt(2), chanceOfSpawn, 8, 16);
	}
	
	private void generateEnd(World world, Random rand, int blockX, int blockZ) {
		
	}
	
	private void addOreSpawn(Block block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chanceToSpawn, int minY, int maxY) {
		for (int i = 0; i < chanceToSpawn; i++) {
			int posX = blockXPos + random.nextInt(maxX);
			int posY = minY + random.nextInt(maxY - minY);
			int posZ = + blockZPos + random.nextInt(maxZ);
			
			(new WorldGenMinable(block.blockID, maxVeinSize)).generate(world, random, posX, posY, posZ);
		}
	}

}
