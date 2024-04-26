package com.mjr.extraplanets.planets.Kepler22b;

import java.util.LinkedList;
import java.util.List;

import com.mjr.extraplanets.ExtraPlanetsDimensions;
import com.mjr.extraplanets.api.prefabs.world.WorldProviderRealisticSpace;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.planets.ExtraPlanets_Planets;
import com.mjr.extraplanets.planets.Kepler22b.worldgen.BiomeProviderKepler22b;
import com.mjr.extraplanets.planets.Kepler22b.worldgen.ChunkProviderKepler22b;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;

import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeAdaptive;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.RoomTreasure;

public class WorldProviderKepler22b extends WorldProviderRealisticSpace {

	@Override
	public Vector3 getFogColor() {
		float f = 1.0F - this.getStarBrightness(1.0F);
		return new Vector3(102 / 255.0F * f, 178 / 255.0F * f, 205.0F / 255.0F * f);
	}

	@Override
	public Vector3 getSkyColor() {
		float f = 1.0F - this.getStarBrightness(1.0F);
		return new Vector3(102 / 255.0F * f, 178 / 255.0F * f, 205.0F / 255.0F * f);
	}

	@Override
	public boolean hasSunset() {
		return true;
	}

	@Override
	public long getDayLength() {
		return 20000L;
	}

	@Override
	public Class<? extends IChunkGenerator> getChunkProviderClass() {
		return ChunkProviderKepler22b.class;
	}

	@Override
	public Class<? extends BiomeProvider> getBiomeProviderClass() {
		BiomeAdaptive.setBodyMultiBiome(ExtraPlanets_Planets.KEPLER22B);
		return BiomeProviderKepler22b.class;
	}

	@Override
	public double getHorizon() {
		return 44.0D;
	}

	@Override
	public int getAverageGroundLevel() {
		return 44;
	}

	@Override
	public boolean canCoordinateBeSpawn(int var1, int var2) {
		return true;
	}

	@Override
	public float getGravity() {
		return 0.030F;
	}

	@Override
	public int getHeight() {
		return 800;
	}

	@Override
	public double getMeteorFrequency() {
		return 0.0D;
	}

	@Override
	public double getFuelUsageMultiplier() {
		return 1.0D;
	}

	@Override
	public boolean canSpaceshipTierPass(int tier) {
		return tier >= ExtraPlanets_Planets.KEPLER22B.getTierRequirement();
	}

	@Override
	public float getFallDamageModifier() {
		return 0.95F;
	}

	@Override
	public CelestialBody getCelestialBody() {
		return ExtraPlanets_Planets.KEPLER22B;
	}

	@Override
	public float getWindLevel() {
		return 3.0F;
	}

	@Override
	public double getSolarEnergyMultiplier() {
		return 6.5D;
	}

	@Override
	public int getPressureLevel() {
		return 0;
	}

	@Override
	public int getSolarRadiationLevel() {
		return 0;
	}

	@Override
	public DimensionType getDimensionType() {
		return ExtraPlanetsDimensions.KEPLER22B;
	}

	@Override
	public int getDungeonSpacing() {
		return 800;
	}

	@Override
	public boolean canBlockFreeze(BlockPos pos, boolean byWater) {
		return false;
	}

	@Override
	public ResourceLocation getDungeonChestType() {
		return RoomTreasure.MOONCHEST;
	}

	@Override
	public List<Block> getSurfaceBlocks() {
		List<Block> list = new LinkedList<>();
		list.add(ExtraPlanets_Blocks.KEPLER22B_GRASS_BLUE);
		list.add(ExtraPlanets_Blocks.KEPLER22B_GRASS_GREEN);
		list.add(ExtraPlanets_Blocks.KEPLER22B_GRASS_INFECTED);
		list.add(ExtraPlanets_Blocks.KEPLER22B_GRASS_PURPLE);
		list.add(ExtraPlanets_Blocks.KEPLER22B_GRASS_RED);
		list.add(ExtraPlanets_Blocks.KEPLER22B_GRASS_YELLOW);
		list.add(ExtraPlanets_Blocks.KEPLER22B_BLOCKS);
		list.add(ExtraPlanets_Blocks.CAKE_BLOCKS);
		return list;
	}
}