package com.mjr.extraplanets.planets.Uranus.worldgen.biomes;

import com.mjr.extraplanets.planets.ExtraPlanets_Planets;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeAdaptive;
import micdoodle8.mods.miccore.IntCache;

public class GenLayerUranusBiomes extends GenLayer {
	private static final Biome[] biomes = BiomeAdaptive.getBiomesListFor(ExtraPlanets_Planets.URANUS).toArray(new Biome[0]);

	public GenLayerUranusBiomes(long l, GenLayer parent) {
		super(l);
		this.parent = parent;
	}

	public GenLayerUranusBiomes(long l) {
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