package com.mjr.extraplanets.planets.Ceres.worldgen;

import net.minecraft.world.biome.BiomeGenBase;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldChunkManagerSpace;

public class WorldChunkManagerCeres extends WorldChunkManagerSpace {

	@Override
	public BiomeGenBase getBiome() {
		return CeresBiomes.ceres;
	}

}
