package com.mjr.extraplanets.planets.Mercury;

import java.util.LinkedList;
import java.util.List;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.ExtraPlanetsDimensions;
import com.mjr.extraplanets.api.prefabs.world.WorldProviderRealisticSpace;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.planets.ExtraPlanets_Planets;
import com.mjr.extraplanets.planets.Mercury.worldgen.ChunkProviderMercury;
import com.mjr.extraplanets.planets.Mercury.worldgen.dungeon.RoomTreasureMercuryBase;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.gen.IChunkGenerator;

import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;

public class WorldProviderMercury extends WorldProviderRealisticSpace {

	@Override
	public Vector3 getFogColor() {
		float f = 1.0F - this.getStarBrightness(1.0F);
		return new Vector3(119 / 255F * f, 119 / 255F * f, 119 / 255F * f);
	}

	@Override
	public Vector3 getSkyColor() {
		float f = 1.0F - this.getStarBrightness(1.0F);
		return new Vector3(0 / 255.0F * f, 0 / 255.0F * f, 0 / 255.0F * f);
	}

	@Override
	public boolean hasSunset() {
		return false;
	}

	@Override
	public long getDayLength() {
		return 820000L;
	}

	@Override
	public Class<? extends IChunkGenerator> getChunkProviderClass() {
		return ChunkProviderMercury.class;
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
		return 0.058F;
	}

	@Override
	public int getHeight() {
		return 800;
	}

	@Override
	public double getMeteorFrequency() {
		return 10.0D;
	}

	@Override
	public double getFuelUsageMultiplier() {
		return 1.0D;
	}

	@Override
	public boolean canSpaceshipTierPass(int tier) {
		return tier >= 3;
	}

	@Override
	public float getFallDamageModifier() {
		return 0.38F;
	}

	@Override
	public CelestialBody getCelestialBody() {
		return ExtraPlanets_Planets.MERCURY;
	}

	@Override
	public double getSolarEnergyMultiplier() {
		return 6.0D;
	}

	@Override
	public int getPressureLevel() {
		return 20;
	}

	@Override
	public int getSolarRadiationLevel() {
		return Config.MERCURY_RADIATION_AMOUNT;
	}

	@Override
	public DimensionType getDimensionType() {
		return ExtraPlanetsDimensions.MERCURY;
	}

	@Override
	public int getDungeonSpacing() {
		return 800;
	}

	@Override
	public ResourceLocation getDungeonChestType() {
		return RoomTreasureMercuryBase.MOONCHEST;
	}

	@Override
	public List<Block> getSurfaceBlocks() {
		List<Block> list = new LinkedList<>();
		list.add(ExtraPlanets_Blocks.MERCURY_BLOCKS);
		return list;
	}
}