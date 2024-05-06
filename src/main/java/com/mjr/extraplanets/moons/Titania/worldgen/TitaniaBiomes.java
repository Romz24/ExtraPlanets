package com.mjr.extraplanets.moons.Titania.worldgen;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.moons.Ganymede.worldgen.BiomeGenGanymede;

import net.minecraft.world.biome.BiomeGenBase;

import micdoodle8.mods.galacticraft.api.world.BiomeGenBaseGC;

public class TitaniaBiomes extends BiomeGenBaseGC {
	public static final BiomeGenBase titania = new BiomeGenGanymede(Config.TITANIA_BIOME_ID).setBiomeName("Titania").setHeight(new Height(2.5F, 0.4F));

	protected TitaniaBiomes(int par1) {
		super(par1);
		this.rainfall = 0F;
	}
}
