package com.mjr.extraplanets.inventory.vehicles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

import micdoodle8.mods.galacticraft.core.inventory.IInventoryDefaults;

public class InventoryMarsRover implements IInventoryDefaults {
	private final NonNullList<ItemStack> stackList;
	private final int inventoryWidth;
	private final Container eventHandler;

	public InventoryMarsRover(Container par1Container) {
		final int size = 32;
		this.stackList = NonNullList.<ItemStack> withSize(size, ItemStack.EMPTY);
		this.eventHandler = par1Container;
		this.inventoryWidth = 5;
	}

	@Override
	public int getSizeInventory() {
		return this.stackList.size();
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return par1 >= this.getSizeInventory() ? ItemStack.EMPTY : this.stackList.get(par1);
	}

	public ItemStack getStackInRowAndColumn(int par1, int par2) {
		if (par1 >= 0 && par1 < this.inventoryWidth) {
			final int var3 = par1 + par2 * this.inventoryWidth;
			return this.getStackInSlot(var3);
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public String getName() {
		return "container.crafting";
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack oldstack = ItemStackHelper.getAndRemove(this.stackList, index);
		if (!oldstack.isEmpty()) {
			this.markDirty();
			this.eventHandler.onCraftMatrixChanged(this);
		}
		return oldstack;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack itemStack = ItemStackHelper.getAndSplit(this.stackList, index, count);

		if (!itemStack.isEmpty()) {
			this.markDirty();
			this.eventHandler.onCraftMatrixChanged(this);
		}

		return itemStack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		this.stackList.set(index, stack);
		this.markDirty();
		this.eventHandler.onCraftMatrixChanged(this);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return false;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemStack : this.stackList) {
			if (!itemStack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return null;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}
}