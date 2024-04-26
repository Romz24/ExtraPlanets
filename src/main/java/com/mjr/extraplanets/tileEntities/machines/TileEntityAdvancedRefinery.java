package com.mjr.extraplanets.tileEntities.machines;

import javax.annotation.Nullable;

import com.mjr.extraplanets.blocks.machines.AdvancedRefinery;

import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;

import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.core.GCFluids;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import micdoodle8.mods.galacticraft.core.wrappers.FluidHandlerWrapper;
import micdoodle8.mods.galacticraft.core.wrappers.IFluidHandlerWrapper;
import micdoodle8.mods.miccore.Annotations.NetworkedField;

public class TileEntityAdvancedRefinery extends TileBaseElectricBlockWithInventory implements ISidedInventory, IFluidHandlerWrapper {
	private final int tankCapacity = 24000 * 2;
	@NetworkedField(targetSide = Side.CLIENT)
	public FluidTank oilTank = new FluidTank(this.tankCapacity);
	@NetworkedField(targetSide = Side.CLIENT)
	public FluidTank fuelTank = new FluidTank(this.tankCapacity);

	public static final int PROCESS_TIME_REQUIRED = 1;
	public static final int OUTPUT_PER_SECOND = 2;
	@NetworkedField(targetSide = Side.CLIENT)
	public int processTicks = 0;

	public TileEntityAdvancedRefinery() {
		super("container.advanced.refinery.name");
		this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 90 : 60);
		this.oilTank.setFluid(new FluidStack(GCFluids.fluidOil, 0));
		this.fuelTank.setFluid(new FluidStack(GCFluids.fluidFuel, 0));
		this.inventory = NonNullList.withSize(3, ItemStack.EMPTY);
	}

	@Override
	public void update() {
		super.update();

		if (!this.world.isRemote) {
			final FluidStack liquid = FluidUtil.getFluidContained(this.getInventory().get(1));
			if (FluidUtil.isFluidFuzzy(liquid, "oil")) {
				FluidUtil.loadFromContainer(this.oilTank, GCFluids.fluidOil, this.getInventory(), 1, liquid.amount);
			}

			checkFluidTankTransfer(2, this.fuelTank);

			if (this.canProcess() && this.hasEnoughEnergyToRun) {
				if (this.processTicks == 0) {
					this.processTicks = this.getProcessTimeRequired();
				} else {
					if (--this.processTicks <= 0) {
						this.smeltItem();
						this.processTicks = this.canProcess() ? this.getProcessTimeRequired() : 0;
					}
				}
			} else {
				this.processTicks = 0;
			}
		}
	}

	private int getProcessTimeRequired() {
		return (this.poweredByTierGC > 1) ? 1 : TileEntityAdvancedRefinery.PROCESS_TIME_REQUIRED;
	}

	private void checkFluidTankTransfer(int slot, FluidTank tank) {
		FluidUtil.tryFillContainerFuel(tank, this.getInventory(), slot);
	}

	public int getScaledOilLevel(int i) {
		return this.oilTank.getFluidAmount() * i / this.oilTank.getCapacity();
	}

	public int getScaledFuelLevel(int i) {
		return this.fuelTank.getFluidAmount() * i / this.fuelTank.getCapacity();
	}

	public boolean canProcess() {
		if (this.oilTank.getFluidAmount() <= 0) {
			return false;
		}

		if (this.fuelTank.getFluidAmount() >= this.fuelTank.getCapacity()) {
			return false;
		}

		return !this.getDisabled(0);

	}

	public void smeltItem() {
		if (this.canProcess()) {
			final int oilAmount = this.oilTank.getFluidAmount();
			final int fuelSpace = this.fuelTank.getCapacity() - this.fuelTank.getFluidAmount();

			final int amountToDrain = Math.min(Math.min(oilAmount, fuelSpace), TileEntityAdvancedRefinery.OUTPUT_PER_SECOND);

			this.oilTank.drain(amountToDrain, true);
			this.fuelTank.fill(FluidRegistry.getFluidStack(ConfigManagerCore.useOldFuelFluidID ? "fuelgc" : "fuel", amountToDrain), true);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.processTicks = nbt.getInteger("smeltingTicks");

		if (nbt.hasKey("oilTank")) {
			this.oilTank.readFromNBT(nbt.getCompoundTag("oilTank"));
		}
		if (this.oilTank.getFluid() != null && this.oilTank.getFluid().getFluid() != GCFluids.fluidOil) {
			this.oilTank.setFluid(new FluidStack(GCFluids.fluidOil, this.oilTank.getFluidAmount()));
		}

		if (nbt.hasKey("fuelTank")) {
			this.fuelTank.readFromNBT(nbt.getCompoundTag("fuelTank"));
		}
		if (this.fuelTank.getFluid() != null && this.fuelTank.getFluid().getFluid() != GCFluids.fluidFuel) {
			this.fuelTank.setFluid(new FluidStack(GCFluids.fluidFuel, this.fuelTank.getFluidAmount()));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("smeltingTicks", this.processTicks);

		if (this.oilTank.getFluid() != null) {
			nbt.setTag("oilTank", this.oilTank.writeToNBT(new NBTTagCompound()));
		}

		if (this.fuelTank.getFluid() != null) {
			nbt.setTag("fuelTank", this.fuelTank.writeToNBT(new NBTTagCompound()));
		}
		return nbt;
	}

	// ISidedInventory Implementation:

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] { 0, 1, 2 };
	}

	@Override
	public boolean canInsertItem(int slotID, ItemStack itemStack, EnumFacing side) {
		if (itemStack != null && this.isItemValidForSlot(slotID, itemStack)) {
			switch (slotID) {
				case 0:
					return ItemElectricBase.isElectricItemCharged(itemStack);
				case 1:
					return FluidUtil.isOilContainerAny(itemStack);
				case 2:
					return FluidUtil.isPartialContainer(itemStack, GCItems.fuelCanister);
				default:
					return false;
			}
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slotID, ItemStack itemStack, EnumFacing side) {
		if (itemStack != null && this.isItemValidForSlot(slotID, itemStack)) {
			switch (slotID) {
				case 0:
					return ItemElectricBase.isElectricItemEmpty(itemStack) || !this.shouldPullEnergy();
				case 1:
					return FluidUtil.isEmptyContainer(itemStack);
				case 2:
					return FluidUtil.isFullContainer(itemStack);
				default:
					return false;
			}
		}
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slotID, ItemStack itemStack) {
		switch (slotID) {
			case 0:
				return itemStack != null && ItemElectricBase.isElectricItem(itemStack.getItem());
			case 1:
			case 2:
				return FluidUtil.isValidContainer(itemStack);
		}

		return false;
	}

	@Override
	public boolean shouldUseEnergy() {
		return this.canProcess();
	}

	@Override
	public EnumFacing getElectricInputDirection() {
		return EnumFacing.UP;
	}

	@Override
	public EnumFacing getFront() {
		IBlockState state = this.world.getBlockState(getPos());
		if (state.getBlock() instanceof AdvancedRefinery) {
			return state.getValue(AdvancedRefinery.FACING);
		}
		return EnumFacing.NORTH;
	}

	private EnumFacing getOilPipe() {
		return getFront().rotateY();
	}

	private EnumFacing getFuelPipe() {
		return getFront().rotateYCCW();
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		if (from == getFuelPipe()) {
			return this.fuelTank.getFluid() != null && this.fuelTank.getFluidAmount() > 0;
		}

		return false;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		if (from == getFuelPipe() && resource != null) {
			return this.fuelTank.drain(resource.amount, doDrain);
		}

		return null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		if (from == getFuelPipe()) {
			return this.drain(from, new FluidStack(GCFluids.fluidFuel, maxDrain), doDrain);
		}

		return null;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		if (from == getOilPipe()) {
			return this.oilTank.getFluid() == null || this.oilTank.getFluidAmount() < this.oilTank.getCapacity();
		}

		return false;
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		int used = 0;

		if (from == getOilPipe() && resource != null) {
			final String liquidName = FluidRegistry.getFluidName(resource);

			if (liquidName != null && FluidUtil.testOil(liquidName)) {
				if (liquidName.equals(GCFluids.fluidOil.getName())) {
					used = this.oilTank.fill(resource, doFill);
				} else {
					used = this.oilTank.fill(new FluidStack(GCFluids.fluidOil, resource.amount), doFill);
				}
			}
		}

		return used;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		FluidTankInfo[] tankInfo = new FluidTankInfo[] {};

		if (from == getOilPipe()) {
			tankInfo = new FluidTankInfo[] { new FluidTankInfo(this.oilTank) };
		} else if (from == getFuelPipe()) {
			tankInfo = new FluidTankInfo[] { new FluidTankInfo(this.fuelTank) };
		}

		return tankInfo;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T) new FluidHandlerWrapper(this, facing);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean canConnect(EnumFacing direction, NetworkType type) {
		if (direction == null) {
			return false;
		}
		if (type == NetworkType.POWER) {
			return direction == this.getElectricInputDirection();
		}
		if (type == NetworkType.FLUID) {
			EnumFacing pipeSide = getFuelPipe();
			return direction == pipeSide || direction == pipeSide.getOpposite();
		}
		return false;
	}
}
