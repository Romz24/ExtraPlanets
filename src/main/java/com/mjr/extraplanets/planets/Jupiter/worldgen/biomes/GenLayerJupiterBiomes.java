package com.mjr.extraplanets.planets.Jupiter.worldgen.biomes;

import com.mjr.extraplanets.planets.Jupiter.worldgen.JupiterBiomes;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;

import micdoodle8.mods.miccore.IntCache;

public class GenLayerJupiterBiomes extends GenLayer {
	private static final BiomeGenBase[] biomes = new BiomeGenBase[] { JupiterBiomes.jupiter, JupiterBiomes.jupiterMagmaSea, JupiterBiomes.jupiterSands };

	public GenLayerJupiterBiomes(long l, GenLayer parent) {
		super(l);
		this.parent = parent;
	}

	public GenLayerJupiterBiomes(long l) {
		super(l);
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth) {
		int[] dest = IntCache.getIntCache(width * depth);

		for (int k = 0; k < depth; ++k) {
			for (int i = 0; i < width; ++i) {
				initChunkSeed(x + i, z + k);
				dest[i + k * width] = biomes[nextInt(biomes.length)].biomeID;
			}
		}

		return dest;
	}
}