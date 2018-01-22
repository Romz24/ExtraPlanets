package com.mjr.extraplanets.planets.Kepler22b.worldgen.biome;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerKepler22bBiomes extends GenLayer {
	protected BiomeGenBase[] commonBiomes = { BiomeGenBaseKepler22b.kepler22bPlains, BiomeGenBaseKepler22b.kepler22bBlueForest, BiomeGenBaseKepler22b.kepler22bPurpleForest, BiomeGenBaseKepler22b.kepler22bRedForest,
			BiomeGenBaseKepler22b.kepler22bYellowForest, BiomeGenBaseKepler22b.kepler22bRedDesert, BiomeGenBaseKepler22b.kepler22bWasteLands, BiomeGenBaseKepler22b.kepler22bCandyLand };

	protected BiomeGenBase[] rareBiomes = { BiomeGenBaseKepler22b.kepler22bBlueForest, Kepler22bBiomes.kepler22bDiamondPlains };

	public GenLayerKepler22bBiomes(long seed, GenLayer genlayer) {
		super(seed);
		this.parent = genlayer;
	}

	public GenLayerKepler22bBiomes(long seed) {
		super(seed);
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth) {
		int[] dest = IntCache.getIntCache(width * depth);

		for (int dz = 0; dz < depth; dz++) {
			for (int dx = 0; dx < width; dx++) {
				this.initChunkSeed(dx + x, dz + z);

				if (this.nextInt(15) == 0) {
					dest[dz + dx * width] = rareBiomes[nextInt(rareBiomes.length)].biomeID;
				} else {
					dest[dz + dx * width] = commonBiomes[nextInt(commonBiomes.length)].biomeID;
				}
			}
		}
		return dest;
	}
}
