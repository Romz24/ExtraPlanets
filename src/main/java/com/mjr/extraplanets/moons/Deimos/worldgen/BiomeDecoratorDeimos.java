package com.mjr.extraplanets.moons.Deimos.worldgen;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;

public class BiomeDecoratorDeimos extends BiomeDecoratorSpace {

	private WorldGenerator copperGen;
	private WorldGenerator tinGen;
	private WorldGenerator ironGen;
	private WorldGenerator gravelGen;
	private WorldGenerator fossilsGen;

	private World currentWorld;

	public BiomeDecoratorDeimos() {
		if (Config.GENERATE_ORES_DEIMOS) {
			this.copperGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.DEIMOS_BLOCKS, 4, 5, true, ExtraPlanets_Blocks.DEIMOS_BLOCKS, 2);
			this.tinGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.DEIMOS_BLOCKS, 4, 4, true, ExtraPlanets_Blocks.DEIMOS_BLOCKS, 2);
			this.ironGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.DEIMOS_BLOCKS, 8, 3, true, ExtraPlanets_Blocks.DEIMOS_BLOCKS, 2);
		}
		this.gravelGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.DEIMOS_GRAVEL, 12, 0, true, ExtraPlanets_Blocks.DEIMOS_BLOCKS, 2);
		this.fossilsGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.FOSSIL, 3, 0, true, ExtraPlanets_Blocks.DEIMOS_BLOCKS, 1);

		// WorldGenMinableMeta(Block OreBlock, int numberOfBlocks, int OreMeta, boolean usingMetaData, Block StoneBlock, int StoneMeta);
	}

	@Override
	protected void setCurrentWorld(World world) {
		this.currentWorld = world;
	}

	@Override
	protected World getCurrentWorld() {
		return this.currentWorld;
	}

	@Override
	protected void decorate() {
		if (Config.GENERATE_ORES_DEIMOS) {
			this.generateOre(26, this.copperGen, 0, 60);
			this.generateOre(23, this.tinGen, 0, 60);
			this.generateOre(20, this.ironGen, 0, 64);
		}
		this.generateOre(15, this.gravelGen, 0, 80);
		this.generateOre(10, this.fossilsGen, 0, 256);

		// generateOre(int amountPerChunk, WorldGenerator worldGenerator, int minY, int maxY);
	}
}
