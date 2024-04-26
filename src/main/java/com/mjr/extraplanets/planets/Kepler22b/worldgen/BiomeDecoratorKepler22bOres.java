package com.mjr.extraplanets.planets.Kepler22b.worldgen;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;

public class BiomeDecoratorKepler22bOres extends BiomeDecoratorSpace {

	private WorldGenerator copperGen;
	private WorldGenerator tinGen;
	private WorldGenerator ironGen;
	private WorldGenerator denseCoal;
	private WorldGenerator blueDiamondOre;
	private WorldGenerator redDiamondOre;
	private WorldGenerator yellowDiamondOre;
	private WorldGenerator purpleDiamondOre;
	private WorldGenerator greenDiamondOre;
	private WorldGenerator platinumOre;
	private WorldGenerator marbleGen;
	private World currentWorld;

	private boolean isDecorating = false;

	public BiomeDecoratorKepler22bOres() {
		if (Config.GENERATE_ORES_KEPLER22B) {
			this.copperGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 4, 3, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
			this.tinGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 4, 4, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
			this.ironGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 8, 2, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
			this.denseCoal = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 8, 5, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
			this.blueDiamondOre = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 8, 6, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
			this.redDiamondOre = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 8, 7, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
			this.yellowDiamondOre = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 8, 8, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
			this.purpleDiamondOre = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 8, 9, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
			this.greenDiamondOre = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 8, 10, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
			this.platinumOre = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 3, 13, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
		}
		this.marbleGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.DECORATIVE_BLOCKS, 10, 0, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
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
	public void decorate() {
		if (isDecorating)
			return;
		isDecorating = true;
		if (Config.GENERATE_ORES_KEPLER22B) {
			this.generateOre(26, this.copperGen, 0, 60);
			this.generateOre(23, this.tinGen, 0, 60);
			this.generateOre(20, this.ironGen, 0, 64);
			this.generateOre(4, this.denseCoal, 0, 25);
			this.generateOre(4, this.blueDiamondOre, 0, 10);
			this.generateOre(4, this.redDiamondOre, 0, 10);
			this.generateOre(4, this.yellowDiamondOre, 0, 10);
			this.generateOre(4, this.purpleDiamondOre, 0, 10);
			this.generateOre(4, this.greenDiamondOre, 0, 10);
			this.generateOre(4, this.platinumOre, 0, 10);
		}
		this.generateOre(20, this.marbleGen, 0, 30);

		// generateOre(int amountPerChunk, WorldGenerator worldGenerator, int minY, int maxY);

		isDecorating = false;
	}
}