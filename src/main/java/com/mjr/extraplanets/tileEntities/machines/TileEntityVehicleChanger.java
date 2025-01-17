package com.mjr.extraplanets.tileEntities.machines;

import com.mjr.extraplanets.api.block.IPowerable;
import com.mjr.extraplanets.blocks.machines.AdvancedFuelLoader;
import com.mjr.extraplanets.blocks.machines.VehicleCharger;

import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import micdoodle8.mods.galacticraft.api.tile.ILandingPadAttachable;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.tile.IMachineSides;
import micdoodle8.mods.galacticraft.core.tile.IMachineSidesProperties;
import micdoodle8.mods.galacticraft.core.tile.TileEntityMulti;

public class TileEntityVehicleChanger extends TileBaseElectricBlockWithInventory implements ISidedInventory, ILandingPadAttachable, IMachineSides {
	public IPowerable attachedPowerable;
	private boolean loadedPowerLastTick = false;

	public TileEntityVehicleChanger() {
		super("container.vehicle.charger.name");
		this.storage.setMaxExtract(30);
		this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
	}

	@Override
	public void update() {
		super.update();

		if (!this.world.isRemote) {
			this.loadedPowerLastTick = false;

			if (this.ticks % 100 == 0) {
				this.attachedPowerable = null;

				for (final EnumFacing dir : EnumFacing.values()) {
					final TileEntity pad = new BlockVec3(this).getTileEntityOnSide(this.world, dir);

					if (pad instanceof TileEntityMulti) {
						final TileEntity mainTile = ((TileEntityMulti) pad).getMainBlockTile();

						if (mainTile instanceof IPowerable) {
							this.attachedPowerable = (IPowerable) mainTile;
							break;
						}
					} else if (pad instanceof IPowerable) {
						this.attachedPowerable = (IPowerable) pad;
						break;
					}
				}

			}

			if (this.storage.getEnergyStoredGC() > 10) {
				if (this.attachedPowerable != null && this.hasEnoughEnergyToRun && !this.disabled) {
					float filled = this.attachedPowerable.addPower(10, true);
					this.loadedPowerLastTick = filled == 10;
					this.storage.setEnergyStored(this.storage.getEnergyStoredGC() - filled);
				}
			}
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	// ISidedInventory Implementation:

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slotID, ItemStack itemStack) {
		return slotID == 0 ? ItemElectricBase.isElectricItem(itemStack.getItem()) : false;
	}

	@Override
	public boolean shouldUseEnergy() {
		return !this.getDisabled(0) && loadedPowerLastTick;
	}

	// ------------------
	// Added these methods and field to implement IMachineSides properly
	// ------------------
	@Override
	public MachineSide[] listConfigurableSides() {
		return new MachineSide[] { MachineSide.ELECTRIC_IN };
	}

	@Override
	public Face[] listDefaultFaces() {
		return new Face[] { Face.LEFT };
	}

	private MachineSidePack[] machineSides;

	@Override
	public MachineSidePack[] getAllMachineSides() {
		if (this.machineSides == null) {
			this.initialiseSides();
		}

		return this.machineSides;
	}

	@Override
	public void setupMachineSides(int length) {
		this.machineSides = new MachineSidePack[length];
	}

	@Override
	public void onLoad() {
		this.clientOnLoad();
	}

	@Override
	public IMachineSidesProperties getConfigurationType() {
		return AdvancedFuelLoader.MACHINESIDES_RENDERTYPE;
	}

	// ------------------END OF IMachineSides implementation

	@Override
	public boolean canAttachToLandingPad(IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public EnumFacing getFront() {
		IBlockState state = this.world.getBlockState(getPos());
		if (state.getBlock() instanceof VehicleCharger) {
			return state.getValue(VehicleCharger.FACING);
		}
		return EnumFacing.NORTH;
	}
}