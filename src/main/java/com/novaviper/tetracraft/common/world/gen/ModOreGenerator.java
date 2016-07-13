package com.novaviper.tetracraft.common.world.gen;

import com.novaviper.tetracraft.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/11/2016
 * @purpose Defines the ore to be generated in the dimensions
 */
public class ModOreGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

		switch (world.provider.getDimensionType()) {
			case NETHER:
				generateNether(world, random, chunkX * 16, chunkZ * 16);
			case OVERWORLD:
				generateSurface(world, random, chunkX * 16, chunkZ * 16);
			case THE_END:
				generateEnd(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	private void generateSurface(World world, Random random, int chunkX, int chunkZ) {
		this.addOreSpawn(ModBlocks.triaxOre, world, random, chunkX, chunkZ, 16, 16, 0, 20, 2 + random.nextInt(6), 100);
	}

	private void generateNether(World world, Random random, int chunkX, int chunkZ) {}

	private void generateEnd(World world, Random random, int chunkX, int chunkZ) {}

	//@formatter:off
	/**
	 * Adds an Ore Spawn to Minecraft. Simply register all Ores to spawn with this method in your Generation method in your IWorldGeneration extending Class
	 *
	 * @param block Block to spawn
	 * @param world World to spawn in
	 * @param random {@link Random} object for retrieving random positions within the world to spawn the Block
	 * @param chunkX int for passing the X-Coordinate for the Generation method
	 * @param chunkZ int for passing the Z-Coordinate for the Generation method
	 * @param maxX int for setting the maximum X-Coordinate values for spawning on the X-Axis on a Per-Chunk basis
	 * @param maxZ for setting the maximum Z-Coordinate values for spawning on the Z-Axis on a Per-Chunk basis
	 * @param maxVeinSize int for setting the maximum size of a vein
	 * @param minY int for the minimum Y-Coordinate height at which this block may spawn
	 * @param maxY int for the maximum Y-Coordinate height at which this block may spawn
	 * @param chancesToSpawn int for the Number of chances available for the Block to spawn per-chunk
	 */
	private void addOreSpawn(Block block, World world, Random random, int chunkX, int chunkZ, int maxX, int maxZ, int minY, int maxY, int maxVeinSize, int chancesToSpawn) {
		assert maxX > 0 && maxX <= 16 : "addOreSpawn: The Maximum X must be greater than 0 and less than 16";
		assert maxZ > 0 && maxZ <= 16 : "addOreSpawn: The Maximum Z must be greater than 0 and less than 16";
		assert maxY > minY : "The maximum Y must be greater than the Minimum Y";
		assert minY > 0 : "addOreSpawn: The Minimum Y must be greater than 0";
		assert maxY < 256 && maxY > 0 : "addOreSpawn: The Maximum Y must be less than 256 but greater than 0";

		for (int k = 0; k < chancesToSpawn; k++) {
			int diffBtwnMinMaxY = maxY - minY;
			int firstBlockXCoord = chunkX + random.nextInt(maxX);
			int firstBlockZCoord = chunkZ + random.nextInt(maxZ);
			//Will be found between y = 0 and y = 20
			int quisqueY = minY + random.nextInt(diffBtwnMinMaxY);
			BlockPos quisquePos = new BlockPos(firstBlockXCoord, quisqueY, firstBlockZCoord);
			//The 10 as the second parameter sets the maximum vein size
			(new WorldGenMinable(block.getDefaultState(), maxVeinSize)).generate(world, random, quisquePos);
		}
	}
}
