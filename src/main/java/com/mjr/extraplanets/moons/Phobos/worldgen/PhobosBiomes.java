package com.mjr.extraplanets.moons.Phobos.worldgen;

import com.mjr.extraplanets.Config;

import net.minecraft.world.biome.BiomeGenBase;

import micdoodle8.mods.galacticraft.api.world.BiomeGenBaseGC;

public class PhobosBiomes extends BiomeGenBaseGC {
	public static final BiomeGenBase phobos = new BiomeGenPhobos(Config.PHOBOS_BIOME_ID).setBiomeName("Phobos").setHeight(new Height(2.5F, 0.4F));

	protected PhobosBiomes(int par1) {
		super(par1);
		this.rainfall = 0F;
	}
}
