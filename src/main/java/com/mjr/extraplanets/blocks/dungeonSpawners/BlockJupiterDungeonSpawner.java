package com.mjr.extraplanets.blocks.dungeonSpawners;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.tileEntities.dungeonSpawners.TileEntityDungeonSpawnerJupiter;
import com.mjr.extraplanets.tileEntities.dungeonSpawners.TileEntityDungeonSpawnerJupiterDefault;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import micdoodle8.mods.galacticraft.core.blocks.BlockBossSpawner;

public class BlockJupiterDungeonSpawner extends BlockBossSpawner {
	public BlockJupiterDungeonSpawner(String name) {
		super(name);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if (Config.USE_DEFAULT_BOSSES)
			return new TileEntityDungeonSpawnerJupiterDefault();
		else
			return new TileEntityDungeonSpawnerJupiter();
	}
}