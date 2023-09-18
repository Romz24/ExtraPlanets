package com.mjr.extraplanets.inventory.machines;

import com.mjr.extraplanets.tileEntities.machines.TileEntitySolar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.energy.EnergyUtil;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;

public class ContainerSolar extends Container {
	private TileEntitySolar tileEntity;

	public ContainerSolar(InventoryPlayer par1InventoryPlayer, TileEntitySolar solarGen) {
		this.tileEntity = solarGen;
		this.addSlotToContainer(new SlotSpecific(solarGen, 0, 152, 83, IItemElectric.class));

		int var6;
		int var7;

		// Player inv:

		for (var6 = 0; var6 < 3; ++var6) {
			for (var7 = 0; var7 < 9; ++var7) {
				this.addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 51 + 68 + var6 * 18));
			}
		}

		for (var6 = 0; var6 < 9; ++var6) {
			this.addSlotToContainer(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 61 + 116));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return this.tileEntity.isUseableByPlayer(var1);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int par1) {
		ItemStack var2 = null;
		final Slot slot = this.inventorySlots.get(par1);
		final int b = this.inventorySlots.size();

		if (slot != null && slot.getHasStack()) {
			final ItemStack stack = slot.getStack();
			var2 = stack.copy();
			boolean movedToMachineSlot = false;

			if (par1 == 0) {
				if (!this.mergeItemStack(stack, b - 36, b, true)) {
					return null;
				}
			} else {
				if (EnergyUtil.isElectricItem(stack.getItem())) {
					if (!this.mergeItemStack(stack, 0, 1, false)) {
						return null;
					}
					movedToMachineSlot = true;
				} else {
					if (par1 < b - 9) {
						if (!this.mergeItemStack(stack, b - 9, b, false)) {
							return null;
						}
					} else if (!this.mergeItemStack(stack, b - 36, b - 9, false)) {
						return null;
					}
				}
			}

			if (stack.stackSize == 0) {
				// Needed where tile has inventoryStackLimit of 1
				if (movedToMachineSlot && var2.stackSize > 1) {
					ItemStack remainder = var2.copy();
					--remainder.stackSize;
					slot.putStack(remainder);
				} else {
					slot.putStack((ItemStack) null);
				}
			} else {
				slot.onSlotChanged();
			}

			if (stack.stackSize == var2.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(entityPlayer, stack);
		}

		return var2;
	}
}
