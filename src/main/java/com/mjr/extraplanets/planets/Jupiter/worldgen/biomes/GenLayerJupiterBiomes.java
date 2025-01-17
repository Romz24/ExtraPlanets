package com.mjr.extraplanets.planets.Jupiter.worldgen.biomes;

import com.mjr.extraplanets.planets.ExtraPlanets_Planets;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeAdaptive;
import micdoodle8.mods.miccore.IntCache;

public class GenLayerJupiterBiomes extends GenLayer {
	private static final Biome[] biomes = BiomeAdaptive.getBiomesListFor(ExtraPlanets_Planets.JUPITER).toArray(new Biome[0]);

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
				dest[i + k * width] = Biome.getIdForBiome(biomes[nextInt(biomes.length)]);
			}
		}

		return dest;
	}
}