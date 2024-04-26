package com.mjr.extraplanets.moons.Europa.worldgen.biomes;

import com.mjr.extraplanets.moons.Europa.worldgen.EuropaBiomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;

import micdoodle8.mods.miccore.IntCache;

public class GenLayerEuropaBiomes extends GenLayer {
	private static final Biome[] biomes = new Biome[] { EuropaBiomes.europa, EuropaBiomes.europaSaltSea, EuropaBiomes.europaIceValleys };
	private static final Biome[] biomesRare = new Biome[] { EuropaBiomes.europa };

	public GenLayerEuropaBiomes(long l, GenLayer parent) {
		super(l);
		this.parent = parent;
	}

	public GenLayerEuropaBiomes(long l) {
		super(l);
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth) {
		int[] dest = IntCache.getIntCache(width * depth);

		for (int k = 0; k < depth; ++k) {
			for (int i = 0; i < width; ++i) {
				initChunkSeed(x + i, z + k);
				if (this.nextInt(10) == 0) {
					dest[i + k * width] = Biome.getIdForBiome(biomesRare[nextInt(biomesRare.length)]);
				} else {
					dest[i + k * width] = Biome.getIdForBiome(biomes[nextInt(biomes.length)]);
				}
			}
		}

		return dest;
	}
}