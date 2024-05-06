package com.mjr.extraplanets.moons.Rhea.worldgen;

import com.mjr.extraplanets.Config;

import net.minecraft.world.biome.BiomeGenBase;

import micdoodle8.mods.galacticraft.api.world.BiomeGenBaseGC;

public class RheaBiomes extends BiomeGenBaseGC {
	public static final BiomeGenBase rhea = new BiomeGenRhea(Config.RHEA_BIOME_ID).setBiomeName("Rhea").setHeight(new Height(2.5F, 0.4F));

	protected RheaBiomes(int par1) {
		super(par1);
		this.rainfall = 0F;
	}
}
