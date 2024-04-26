package com.mjr.extraplanets.inventory.rockets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

import micdoodle8.mods.galacticraft.core.inventory.IInventoryDefaults;

public class InventorySchematicTier5Rocket implements IInventoryDefaults {
	public NonNullList<ItemStack> stacks;
	private final Container eventHandler;

	public InventorySchematicTier5Rocket(Container par1Container) {
		this.stacks = NonNullList.withSize(22, ItemStack.EMPTY);
		this.eventHandler = par1Container;
	}

	@Override
	public int getSizeInventory() {
		return this.stacks.size();
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return par1 >= this.getSizeInventory() ? ItemStack.EMPTY : this.stacks.get(par1);
	}

	@Override
	public String getName() {
		return "container.crafting";
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack oldstack = ItemStackHelper.getAndRemove(this.stacks, index);
		if (!oldstack.isEmpty()) {
			this.markDirty();
			this.eventHandler.onCraftMatrixChanged(this);
		}
		return oldstack;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack itemStack = ItemStackHelper.getAndSplit(this.stacks, index, count);

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

		this.stacks.set(index, stack);
		this.markDirty();
		this.eventHandler.onCraftMatrixChanged(this);
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemStack : this.stacks) {
			if (!itemStack.isEmpty()) {
				return false;
			}
		}

		return true;
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
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return false;
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
