package com.mjr.extraplanets.moons.Iapetus.worldgen;

import com.mjr.extraplanets.Config;

import net.minecraft.world.biome.BiomeGenBase;

import micdoodle8.mods.galacticraft.api.world.BiomeGenBaseGC;

public class IapetusBiomes extends BiomeGenBaseGC {
	public static final BiomeGenBase iapetus = new BiomeGenIapetus(Config.IAPETUS_BIOME_ID).setBiomeName("Iapetus").setHeight(new Height(2.5F, 0.4F));

	protected IapetusBiomes(int par1) {
		super(par1);
		this.rainfall = 0F;
	}
}
