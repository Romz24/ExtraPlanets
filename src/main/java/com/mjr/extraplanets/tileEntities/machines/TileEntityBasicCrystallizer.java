package com.mjr.extraplanets.tileEntities.machines;

import com.mjr.extraplanets.blocks.fluid.ExtraPlanets_Fluids;
import com.mjr.extraplanets.blocks.machines.BasicCrystallizer;
import com.mjr.extraplanets.items.ExtraPlanets_Items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
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
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import micdoodle8.mods.galacticraft.core.wrappers.FluidHandlerWrapper;
import micdoodle8.mods.galacticraft.core.wrappers.IFluidHandlerWrapper;
import micdoodle8.mods.miccore.Annotations.NetworkedField;

public class TileEntityBasicCrystallizer extends TileBaseElectricBlockWithInventory implements ISidedInventory, IFluidHandlerWrapper {
	private final int tankCapacity = 20000;
	private int amountDrain = 0;
	@NetworkedField(targetSide = Side.CLIENT)
	public FluidTank inputTank = new FluidTank(this.tankCapacity);

	public static final int PROCESS_TIME_REQUIRED = 5;
	@NetworkedField(targetSide = Side.CLIENT)
	public int processTicks = 0;

	private ItemStack producingStack = new ItemStack(ExtraPlanets_Items.IODIDE_SALT, 6, 0);

	public TileEntityBasicCrystallizer() {
		super("container.basic.crystallizer.name");
		this.inputTank.setFluid(new FluidStack(ExtraPlanets_Fluids.SALT_FLUID, 0));
		this.inventory = NonNullList.withSize(3, ItemStack.EMPTY);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T) new FluidHandlerWrapper(this, facing);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void update() {
		super.update();

		if (!this.world.isRemote) {
			checkFluidTankTransfer(2, this.inputTank);

			if (this.canProcess() && this.hasEnoughEnergyToRun) {
				if (this.processTicks == 0) {
					this.processTicks = TileEntityBasicCrystallizer.PROCESS_TIME_REQUIRED;
				} else {
					if (--this.processTicks <= 0) {
						this.smeltItem();
						this.processTicks = this.canProcess() ? TileEntityBasicCrystallizer.PROCESS_TIME_REQUIRED : 0;
					}
				}
			} else {
				this.processTicks = 0;
			}
		}
	}

	private void checkFluidTankTransfer(int slot, FluidTank tank) {
		ItemStack item = this.getStackInSlot(slot);
		if (item != null && FluidUtil.isValidContainer(item)) {
			FluidStack stack = FluidUtil.getFluidContained(item);
			if (FluidUtil.isEmptyContainer(this.getStackInSlot(slot)) == false && stack != null && stack.getFluid() != null && stack.getFluid().equals(ExtraPlanets_Fluids.SALT_FLUID)) {
				FluidUtil.loadFromContainer(inputTank, ExtraPlanets_Fluids.SALT_FLUID, this.getInventory(), slot, stack.amount);
			}
		}
	}

	public int getScaledFuelLevel(int i) {
		return this.inputTank.getFluidAmount() * i / this.inputTank.getCapacity();
	}

	public boolean canProcess() {
		if (this.inputTank.getFluidAmount() <= 0 && amountDrain <= 0) {
			return false;
		}
		return !this.getDisabled(0);

	}

	public boolean canCrystallize() {
		if (this.producingStack.isEmpty()) {
			return false;
		}
		if (this.getInventory().get(1).isEmpty()) {
			return true;
		}
		if (!this.getInventory().get(1).isEmpty() && !this.getInventory().get(1).isItemEqual(this.producingStack)) {
			return false;
		}
		int result = this.getInventory().get(1).isEmpty() ? 0 : this.getInventory().get(1).getCount() + this.producingStack.getCount();
		return result <= this.getInventoryStackLimit() && result <= this.producingStack.getMaxStackSize();
	}

	public boolean hasInputs() {
		if (this.inputTank.getFluidAmount() >= 50)
			return true;
		return false;
	}

	public void smeltItem() {
		ItemStack resultItemStack = this.producingStack;
		if (this.canProcess() && canCrystallize() && hasInputs()) {
			final int amountToDrain = 50;
			amountDrain = amountDrain + amountToDrain;
			this.inputTank.drain(amountToDrain, true);
			if (amountDrain >= 1000) {
				amountDrain = 0;
				if (this.getInventory().get(1).isEmpty()) {
					this.getInventory().set(1, resultItemStack.copy());
				} else if (this.getInventory().get(1).isItemEqual(resultItemStack)) {
					if (this.getInventory().get(1).getCount() + resultItemStack.getCount() > 64) {
						for (int i = 0; i < this.getInventory().get(1).getCount() + resultItemStack.getCount() - 64; i++) {
							float var = 0.7F;
							double dx = this.world.rand.nextFloat() * var + (1.0F - var) * 0.5D;
							double dy = this.world.rand.nextFloat() * var + (1.0F - var) * 0.5D;
							double dz = this.world.rand.nextFloat() * var + (1.0F - var) * 0.5D;
							EntityItem entityitem = new EntityItem(this.world, this.getPos().getX() + dx, this.getPos().getY() + dy, this.getPos().getZ() + dz, new ItemStack(resultItemStack.getItem(), 1, resultItemStack.getItemDamage()));
							entityitem.setPickupDelay(10);
							this.world.spawnEntity(entityitem);
						}
						this.getInventory().get(1).setCount(64);
					} else {
						this.getInventory().get(1).grow(resultItemStack.getCount());
					}
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.processTicks = nbt.getInteger("smeltingTicks");

		if (nbt.hasKey("inputTank")) {
			this.inputTank.readFromNBT(nbt.getCompoundTag("inputTank"));
		}
		if (this.inputTank.getFluid() != null && this.inputTank.getFluid().getFluid() != ExtraPlanets_Fluids.SALT_FLUID) {
			this.inputTank.setFluid(new FluidStack(ExtraPlanets_Fluids.SALT_FLUID, this.inputTank.getFluidAmount()));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("smeltingTicks", this.processTicks);

		if (this.inputTank.getFluid() != null) {
			nbt.setTag("inputTank", this.inputTank.writeToNBT(new NBTTagCompound()));
		}
		return nbt;
	}

	// ISidedInventory Implementation:

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		if (side == EnumFacing.DOWN) {
			return new int[] { 1 };
		}
		return new int[] { 0, 1, 2 };
	}

	@Override
	public boolean canInsertItem(int slotID, ItemStack itemStack, EnumFacing side) {
		if (itemStack != null && this.isItemValidForSlot(slotID, itemStack)) {
			switch (slotID) {
				case 0:
					return ItemElectricBase.isElectricItemCharged(itemStack);
				case 2:
					return true;
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
					return itemStack.getItem() == ExtraPlanets_Items.IODIDE_SALT;
				case 2:
					return itemStack == new ItemStack(Items.BUCKET);
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
				return itemStack.getItem() == ExtraPlanets_Items.IODIDE_SALT;
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
		return getFront().rotateYCCW();
	}

	@Override
	public EnumFacing getFront() {
		IBlockState state = this.world.getBlockState(getPos());
		if (state.getBlock() instanceof BasicCrystallizer) {
			return state.getValue(BasicCrystallizer.FACING);
		}
		return EnumFacing.NORTH;
	}

	@Override
	public boolean canConnect(EnumFacing direction, NetworkType type) {
		if (type == NetworkType.POWER) {
			return direction == this.getElectricInputDirection();
		}
		if (type == NetworkType.FLUID) {
			return direction == this.getInputPipe();
		}
		return false;
	}

	private EnumFacing getInputPipe() {
		return getFront().rotateY();
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		if (from == getInputPipe()) {
			return this.inputTank.getFluid() == null || this.inputTank.getFluidAmount() < this.inputTank.getCapacity();
		}

		return false;
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		int used = 0;

		if (from == getInputPipe() && resource != null) {
			final String liquidName = FluidRegistry.getFluidName(resource);

			if (liquidName != null && liquidName.contains("salt")) {
				if (liquidName.equals("salt")) {
					used = this.inputTank.fill(resource, doFill);
				} else {
					used = this.inputTank.fill(new FluidStack(ExtraPlanets_Fluids.SALT_FLUID, resource.amount), doFill);
				}
			}
		}

		return used;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		return new FluidTankInfo[] { new FluidTankInfo(this.inputTank) };
	}
}
