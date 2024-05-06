package com.mjr.extraplanets.moons.Triton.worldgen;

import java.util.Random;

import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;

public class BiomeDecoratorTritonOther extends BiomeDecorator {
	private World worldObj;
	private Random randGenerator;

	private WorldGenerator snowGen;
	private WorldGenerator iceGen;

	public int generateSnowFeatures = 150;

	public BiomeDecoratorTritonOther() {
		this.snowGen = new WorldGenMinableMeta(Blocks.snow, 5, 0, false, ExtraPlanets_Blocks.FROZEN_NITROGEN, 0);
		this.iceGen = new WorldGenMinableMeta(Blocks.ice, 5, 0, false, ExtraPlanets_Blocks.FROZEN_NITROGEN, 0);
		// WorldGenMinableMeta(Block OreBlock, int numberOfBlocks, int OreMeta, boolean usingMetaData, Block StoneBlock, int StoneMeta);
	}

	@Override
	public void decorate(World world, Random rand, BiomeGenBase p_180292_3_, BlockPos pos) {
		if (this.worldObj != null) {
			throw new RuntimeException("Already decorating!!");
		} else {
			this.worldObj = world;
			this.randGenerator = rand;
			this.field_180294_c = pos;
			this.generate();
			this.worldObj = null;
			this.randGenerator = null;
		}
	}

	private void genStandardOre(int amountPerChunk, WorldGenerator worldGenerator, int minY, int maxY) {
		World currentWorld = this.worldObj;
		for (int var5 = 0; var5 < amountPerChunk; ++var5) {
			final int var6 = this.field_180294_c.getX() + this.randGenerator.nextInt(16);
			final int var7 = this.randGenerator.nextInt(maxY - minY) + minY;
			final int var8 = this.field_180294_c.getZ() + this.randGenerator.nextInt(16);
			worldGenerator.generate(currentWorld, this.randGenerator, new BlockPos(var6, var7, var8));
		}
	}

	private void generate() {
		this.genStandardOre(this.generateSnowFeatures, this.snowGen, 0, 256);
		this.genStandardOre(this.generateSnowFeatures, this.iceGen, 0, 256);
	}
}