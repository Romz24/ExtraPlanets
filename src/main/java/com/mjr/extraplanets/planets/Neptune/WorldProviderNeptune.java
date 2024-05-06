package com.mjr.extraplanets.planets.Neptune;

import java.util.LinkedList;
import java.util.List;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.api.prefabs.world.WorldProviderRealisticSpace;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.planets.ExtraPlanets_Planets;
import com.mjr.extraplanets.planets.Neptune.worldgen.ChunkProviderNeptune;
import com.mjr.extraplanets.planets.Neptune.worldgen.WorldChunkManagerNeptune;
import com.mjr.mjrlegendslib.util.MCUtilities;

import net.minecraft.block.Block;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.RoomChest;

public class WorldProviderNeptune extends WorldProviderRealisticSpace {

	@Override
	public Vector3 getFogColor() {
		float f = 1.0F - this.getStarBrightness(1.0F);
		return new Vector3(100f / 255F * f, 210f / 255F * f, 222f / 255F * f);
	}

	@Override
	public Vector3 getSkyColor() {
		float f = 1.0F - this.getStarBrightness(1.0F);
		return new Vector3(24f / 255.0F * f, 154f / 255.0F * f, 253f / 255.0F * f);
	}

	@Override
	public boolean hasSunset() {
		return false;
	}

	@Override
	public long getDayLength() {
		return 16660L;
	}

	@Override
	public Class<? extends IChunkProvider> getChunkProviderClass() {
		return ChunkProviderNeptune.class;
	}

	@Override
	public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
		return WorldChunkManagerNeptune.class;
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
		if (Config.OLD_STYLE_GRAVITY)
			return 0.058F;
		else
			return 0.010F;
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
		return 1.6D;
	}

	@Override
	public boolean canSpaceshipTierPass(int tier) {
		return tier >= ExtraPlanets_Planets.NEPTUNE.getTierRequirement();
	}

	@Override
	public float getFallDamageModifier() {
		if (Config.OLD_STYLE_GRAVITY)
			return 0.38F;
		else
			return 3.2F;
	}

	@Override
	public CelestialBody getCelestialBody() {
		return ExtraPlanets_Planets.NEPTUNE;
	}

	@Override
	public float getThermalLevelModifier() {
		if (Config.THERMAL_PADDINGS) {
			if (MCUtilities.isServer() && isDaytime()) {
				return -140.0F;
			}
			return -130.0F;
		}
		return 5.0F;
	}

	@Override
	public double getSolarEnergyMultiplier() {
		return 2.0D;
	}

	@Override
	public int getPressureLevel() {
		return 100;
	}

	@Override
	public int getSolarRadiationLevel() {
		return Config.NEPTUNE_RADIATION_AMOUNT;
	}

	@Override
	public int getDungeonSpacing() {
		return 800;
	}

	@Override
	public String getDungeonChestType() {
		return RoomChest.MOONCHEST;
	}

	@Override
	public String getInternalNameSuffix() {
		return "neptune";
	}

	@Override
	public List<Block> getSurfaceBlocks() {
		List<Block> list = new LinkedList<>();
		list.add(ExtraPlanets_Blocks.NEPTUNE_BLOCKS);
		list.add(ExtraPlanets_Blocks.DECORATIVE_BLOCKS2);
		return list;
	}
}