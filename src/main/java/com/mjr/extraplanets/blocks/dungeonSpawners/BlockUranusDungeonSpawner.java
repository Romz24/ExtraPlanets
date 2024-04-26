package com.mjr.extraplanets.blocks.dungeonSpawners;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.tileEntities.dungeonSpawners.TileEntityDungeonSpawnerUranus;
import com.mjr.extraplanets.tileEntities.dungeonSpawners.TileEntityDungeonSpawnerUranusDefault;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import micdoodle8.mods.galacticraft.core.blocks.BlockBossSpawner;

public class BlockUranusDungeonSpawner extends BlockBossSpawner {
	public BlockUranusDungeonSpawner(String name) {
		super(name);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if (Config.USE_DEFAULT_BOSSES)
			return new TileEntityDungeonSpawnerUranusDefault();
		else
			return new TileEntityDungeonSpawnerUranus();
	}
}