package com.mjr.extraplanets.moons.Titan;

import java.util.Random;

import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.blocks.planetAndMoonBlocks.BlockBasicTitan;
import com.mjr.extraplanets.entities.landers.EntityGeneralLander;
import com.mjr.extraplanets.util.LanderUtil;
import com.mjr.mjrlegendslib.util.MessageUtilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.core.entities.player.GCCapabilities;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.util.CompatibilityManager;

public class TeleportTypeTitan implements ITeleportType {
	@Override
	public boolean useParachute() {
		return false;
	}

	@Override
	public Vector3 getPlayerSpawnLocation(WorldServer world, EntityPlayerMP player) {
		if (player != null) {
			GCPlayerStats stats = player.getCapability(GCCapabilities.GC_STATS_CAPABILITY, null);
			return new Vector3(stats.getCoordsTeleportedFromX(), Constants.PLANET_AND_MOON_SPAWN_HEIGHT, stats.getCoordsTeleportedFromZ());
		}

		return null;
	}

	@Override
	public Vector3 getEntitySpawnLocation(WorldServer world, Entity entity) {
		return new Vector3(entity.posX, Constants.PLANET_AND_MOON_SPAWN_HEIGHT_D, entity.posZ);
	}

	@Override
	public Vector3 getParaChestSpawnLocation(WorldServer world, EntityPlayerMP player, Random rand) {
		return null;
	}

	@Override
	public void onSpaceDimensionChanged(World newWorld, EntityPlayerMP player, boolean ridingAutoRocket) {
		if (!ridingAutoRocket && player != null) {
			GCPlayerStats stats = GCPlayerStats.get(player);

			if (stats.getTeleportCooldown() <= 0) {
				if (player.capabilities.isFlying) {
					player.capabilities.isFlying = false;
				}

				EntityGeneralLander lander = new EntityGeneralLander(player);
				lander.setPosition(player.posX, player.posY, player.posZ);

				if (!newWorld.isRemote) {
					boolean previous = CompatibilityManager.forceLoadChunks((WorldServer) newWorld);
					lander.forceSpawn = true;
					newWorld.spawnEntity(lander);
					lander.setWorld(newWorld);
					newWorld.updateEntityWithOptionalForce(lander, true);
					player.startRiding(lander);
					LanderUtil.makeSmallLandingSpot(newWorld, (int) lander.posX, (int) lander.posZ, ExtraPlanets_Blocks.TITAN_BLOCKS.getDefaultState().withProperty(BlockBasicTitan.BASIC_TYPE, BlockBasicTitan.EnumBlockBasic.STONE), false);
					CompatibilityManager.forceLoadChunksEnd((WorldServer) newWorld, previous);
					MessageUtilities.debugMessageToLog(Constants.modName, "Entering lander at : " + player.posX + "," + player.posZ + " lander spawn at: " + lander.posX + "," + lander.posZ);
				}

				stats.setTeleportCooldown(10);
			}
		}
	}

	@Override
	public void setupAdventureSpawn(EntityPlayerMP player) {

	}
}
