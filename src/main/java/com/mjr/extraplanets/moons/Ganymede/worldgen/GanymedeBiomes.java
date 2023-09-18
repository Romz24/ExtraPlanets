package com.mjr.extraplanets.moons.Ganymede.worldgen;

import com.mjr.extraplanets.Config;

import net.minecraft.world.biome.BiomeGenBase;

import micdoodle8.mods.galacticraft.api.world.BiomeGenBaseGC;

public class GanymedeBiomes extends BiomeGenBaseGC {

	public static final BiomeGenBase ganymede = new BiomeGenGanymede(Config.GANYMEDE_BIOME_ID).setBiomeName("Ganymede").setHeight(new Height(2.5F, 0.4F));

	protected GanymedeBiomes(int par1) {
		super(par1);
		this.rainfall = 0F;
	}
}
