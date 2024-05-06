package com.mjr.extraplanets.moons.Io.worldgen;

import java.util.Random;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.moons.Io.worldgen.biomes.BiomeGenIOBurningPlains;
import com.mjr.extraplanets.moons.Io.worldgen.biomes.BiomeGenIo;
import com.mjr.extraplanets.moons.Io.worldgen.biomes.BiomeGenIoAshLands;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.ChunkPrimer;

import micdoodle8.mods.galacticraft.api.world.BiomeGenBaseGC;

public class IoBiomes extends BiomeGenBaseGC {
	public static final BiomeGenBase io = new BiomeGenIo(Config.IO_BIOME_ID).setBiomeName("Io").setHeight(new Height(0.125F, 0.05F));
	public static final BiomeGenBase ioAshLands = new BiomeGenIoAshLands(Config.IO_ASH_LANDS_BIOME_ID).setBiomeName("Io Ash Lands").setHeight(new Height(2.0F, 2.0F));
	public static final BiomeGenBase ioBurningPlains = new BiomeGenIOBurningPlains(Config.IO_BURNING_PLAINS_BIOME_ID).setBiomeName("Io Burning Plains").setHeight(new Height(0.125F, 0.015F));

	protected IoBiomes(int par1) {
		super(par1);
		this.rainfall = 0F;
	}

	@Override
	public BiomeDecorator createBiomeDecorator() {
		return new BiomeDecoratorIoOther();
	}

	protected BiomeDecoratorIoOther getBiomeDecorator() {
		return (BiomeDecoratorIoOther) this.theBiomeDecorator;
	}

	@Override
	public void genTerrainBlocks(World world, Random rand, ChunkPrimer chunk, int x, int z, double stoneNoise) {
		generateIoBiomeTerrain(rand, chunk, x, z, stoneNoise);
	}

	public final void generateIoBiomeTerrain(Random rand, ChunkPrimer chunk, int x, int z, double stoneNoise) {
		IBlockState iblockstate = this.topBlock;
		IBlockState iblockstate1 = this.fillerBlock;
		int j = -1;
		int k = (int) (stoneNoise / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		int l = x & 15;
		int i1 = z & 15;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for (int j1 = 255; j1 >= 0; --j1) {
			if (j1 <= rand.nextInt(5)) {
				chunk.setBlockState(i1, j1, l, Blocks.bedrock.getDefaultState());
			} else {
				IBlockState iblockstate2 = chunk.getBlockState(i1, j1, l);
				if (iblockstate2.getBlock().getMaterial() == Material.air) {
					j = -1;
				} else if (iblockstate2.getBlock() == ExtraPlanets_Blocks.IO_BLOCKS.getStateFromMeta(2).getBlock()) {
					if (j == -1) {
						if (k <= 0) {
							iblockstate = null;
							iblockstate1 = ExtraPlanets_Blocks.IO_BLOCKS.getStateFromMeta(2);
						} else if (j1 >= 63 - 4 && j1 <= 63 + 1) {
							iblockstate = this.topBlock;
							iblockstate1 = this.fillerBlock;
						}

						if (j1 < 63 && (iblockstate == null || iblockstate.getBlock().getMaterial() == Material.air)) {
							if (this.getFloatTemperature(blockpos$mutableblockpos.set(x, j1, z)) < 0.15F) {
								iblockstate = Blocks.ice.getDefaultState();
							} else {
								iblockstate = Blocks.water.getDefaultState();
							}
						}

						j = k;

						if (j1 >= 63 - 1) {
							chunk.setBlockState(i1, j1, l, iblockstate);
						} else if (j1 < 63 - 7 - k) {
							iblockstate = null;
							iblockstate1 = ExtraPlanets_Blocks.IO_BLOCKS.getStateFromMeta(2);
							chunk.setBlockState(i1, j1, l, Blocks.gravel.getDefaultState());
						} else {
							chunk.setBlockState(i1, j1, l, iblockstate1);
						}
					} else if (j > 0) {
						--j;
						chunk.setBlockState(i1, j1, l, iblockstate1);
					}
				}
			}
		}
	}
}
