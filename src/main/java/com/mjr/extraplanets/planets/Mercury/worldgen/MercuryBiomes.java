package com.mjr.extraplanets.planets.Mercury.worldgen;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.planets.Eris.worldgen.BiomeGenEris;

import net.minecraft.world.biome.BiomeGenBase;

import micdoodle8.mods.galacticraft.api.world.BiomeGenBaseGC;

public class MercuryBiomes extends BiomeGenBaseGC {
	public static final BiomeGenBase mercury = new BiomeGenEris(Config.MERCURY_BIOME_ID).setBiomeName("Mercury").setHeight(new Height(2.5F, 0.4F));

	protected MercuryBiomes(int var1) {
		super(var1);
		this.rainfall = 0F;
	}
}
