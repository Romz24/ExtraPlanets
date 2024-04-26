package com.mjr.extraplanets.planets.Pluto.worldgen;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;

public class BiomeDecoratorPluto extends BiomeDecoratorSpace {

	private WorldGenerator copperGen;
	private WorldGenerator tinGen;
	private WorldGenerator ironGen;
	private WorldGenerator gravelGen;
	private WorldGenerator fossilsGen;
	private WorldGenerator tungstenGen;

	private World currentWorld;

	private boolean isDecorating = false;

	public BiomeDecoratorPluto() {
		if (Config.GENERATE_ORES_PLUTO) {
			this.copperGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.PLUTO_BLOCKS, 4, 5, true, ExtraPlanets_Blocks.PLUTO_BLOCKS, 2);
			this.tinGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.PLUTO_BLOCKS, 4, 4, true, ExtraPlanets_Blocks.PLUTO_BLOCKS, 2);
			this.ironGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.PLUTO_BLOCKS, 8, 3, true, ExtraPlanets_Blocks.PLUTO_BLOCKS, 2);
			this.tungstenGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.PLUTO_BLOCKS, 4, 6, true, ExtraPlanets_Blocks.PLUTO_BLOCKS, 2);
		}
		this.gravelGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.PLUTO_GRAVEL, 12, 0, true, ExtraPlanets_Blocks.PLUTO_BLOCKS, 2);
		this.fossilsGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.FOSSIL, 3, 0, true, ExtraPlanets_Blocks.PLUTO_BLOCKS, 1);
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
		if (isDecorating)
			return;
		isDecorating = true;
		if (Config.GENERATE_ORES_PLUTO) {
			this.generateOre(26, this.copperGen, 0, 60);
			this.generateOre(23, this.tinGen, 0, 60);
			this.generateOre(20, this.ironGen, 0, 64);
			this.generateOre(20, this.tungstenGen, 0, 32);
		}
		this.generateOre(15, this.gravelGen, 0, 80);
		this.generateOre(10, this.fossilsGen, 0, 256);

		// generateOre(int amountPerChunk, WorldGenerator worldGenerator, int minY, int maxY);

		isDecorating = false;
	}
}
