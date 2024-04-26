package com.mjr.extraplanets.planets.Ceres.worldgen;

import net.minecraft.world.biome.Biome;

import micdoodle8.mods.galacticraft.api.world.BiomeGenBaseGC;

public class CeresBiomes extends BiomeGenBaseGC {

	public static final Biome ceres = new BiomeGenCeres(new BiomeProperties("Ceres").setBaseHeight(2.5F).setHeightVariation(0.4F).setRainfall(0.0F).setRainDisabled());

	protected CeresBiomes(BiomeProperties properties) {
		super(properties);
	}
}
