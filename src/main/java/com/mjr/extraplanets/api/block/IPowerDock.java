package com.mjr.extraplanets.api.block;

import java.util.HashSet;

import com.mjr.extraplanets.api.enitity.IPoweredDockable;

import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

import micdoodle8.mods.galacticraft.api.tile.ILandingPadAttachable;

public interface IPowerDock {
	public HashSet<ILandingPadAttachable> getConnectedTiles();

	public boolean isBlockAttachable(IBlockAccess world, BlockPos pos);

	public IPoweredDockable getDockedEntity();

	void dockEntity(IPoweredDockable entity);
}