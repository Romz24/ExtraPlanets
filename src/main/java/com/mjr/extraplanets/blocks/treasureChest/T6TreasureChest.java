package com.mjr.extraplanets.blocks.treasureChest;

import com.mjr.extraplanets.ExtraPlanets;
import com.mjr.extraplanets.tileEntities.treasureChests.TileEntityT6TreasureChest;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import micdoodle8.mods.galacticraft.core.blocks.BlockTier1TreasureChest;

public class T6TreasureChest extends BlockTier1TreasureChest {
	public T6TreasureChest(String name) {
		super(name);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityT6TreasureChest();
	}

	@Override
	public CreativeTabs getCreativeTabToDisplayOn() {
		return ExtraPlanets.BlocksTab;
	}
}