package com.mjr.extraplanets.moons.Triton.worldgen.biomes;

import com.mjr.extraplanets.moons.Triton.worldgen.TritonBiomes;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;

import micdoodle8.mods.miccore.IntCache;

public class GenLayerTritonBiomes extends GenLayer {
	private static final BiomeGenBase[] biomes = new BiomeGenBase[] { TritonBiomes.triton, TritonBiomes.tritonIceLands, TritonBiomes.tritonIceSea };

	public GenLayerTritonBiomes(long l, GenLayer parent) {
		super(l);
		this.parent = parent;
	}

	public GenLayerTritonBiomes(long l) {
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
