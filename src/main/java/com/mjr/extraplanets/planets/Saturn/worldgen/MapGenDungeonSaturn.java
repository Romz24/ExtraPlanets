package com.mjr.extraplanets.planets.Saturn.worldgen;

import net.minecraft.world.gen.structure.MapGenStructureIO;

import micdoodle8.mods.galacticraft.core.world.gen.dungeon.DungeonConfiguration;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.MapGenDungeon;

public class MapGenDungeonSaturn extends MapGenDungeon {
	private static boolean initialized;

	static {
		try {
			MapGenDungeonSaturn.initiateStructures();
		} catch (Throwable e) {

		}
	}

	public MapGenDungeonSaturn(DungeonConfiguration configuration) {
		super(configuration);
	}

	public static void initiateStructures() throws Throwable {
		if (!MapGenDungeonSaturn.initialized) {
			MapGenStructureIO.registerStructureComponent(RoomBossSaturn.class, "SaturnDungeonBossRoom");
			MapGenStructureIO.registerStructureComponent(RoomTreasureSaturn.class, "SaturnDungeonTreasureRoom");
		}

		MapGenDungeonSaturn.initialized = true;
	}
}