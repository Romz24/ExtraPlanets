package com.mjr.extraplanets.planets.Jupiter.worldgen.dungeon;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.template.TemplateManager;

import micdoodle8.mods.galacticraft.core.world.gen.dungeon.DungeonConfiguration;

public abstract class SizedPieceJupiter extends DirectionalPieceJupiter {
	protected int sizeX;
	protected int sizeY;
	protected int sizeZ;

	public SizedPieceJupiter() {
	}

	public SizedPieceJupiter(DungeonConfiguration configuration, int sizeX, int sizeY, int sizeZ, EnumFacing direction) {
		super(configuration, direction);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
	}

	@Override
	protected void writeStructureToNBT(NBTTagCompound tagCompound) {
		super.writeStructureToNBT(tagCompound);

		tagCompound.setInteger("sizeX", this.sizeX);
		tagCompound.setInteger("sizeY", this.sizeY);
		tagCompound.setInteger("sizeZ", this.sizeZ);
	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager manager) {
		super.readStructureFromNBT(nbt, manager);

		this.sizeX = nbt.getInteger("sizeX");
		this.sizeY = nbt.getInteger("sizeY");
		this.sizeZ = nbt.getInteger("sizeZ");
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public int getSizeZ() {
		return sizeZ;
	}
}
