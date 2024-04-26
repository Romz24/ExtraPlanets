package com.mjr.extraplanets.blocks.dungeonSpawners;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.tileEntities.dungeonSpawners.TileEntityDungeonSpawnerEris;
import com.mjr.extraplanets.tileEntities.dungeonSpawners.TileEntityDungeonSpawnerErisDefault;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import micdoodle8.mods.galacticraft.core.blocks.BlockBossSpawner;

public class BlockErisDungeonSpawner extends BlockBossSpawner {
	public BlockErisDungeonSpawner(String name) {
		super(name);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if (Config.USE_DEFAULT_BOSSES)
			return new TileEntityDungeonSpawnerErisDefault();
		else
			return new TileEntityDungeonSpawnerEris();
	}
}