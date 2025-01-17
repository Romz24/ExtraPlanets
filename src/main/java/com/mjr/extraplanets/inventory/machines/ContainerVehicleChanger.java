package com.mjr.extraplanets.inventory.machines;

import com.mjr.extraplanets.tileEntities.machines.TileEntityVehicleChanger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlock;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;

public class ContainerVehicleChanger extends Container {
	private TileBaseElectricBlock tileEntity;

	public ContainerVehicleChanger(InventoryPlayer inventoryPlayer, TileEntityVehicleChanger vehicleChanger) {
		this.tileEntity = vehicleChanger;
		this.addSlotToContainer(new SlotSpecific(vehicleChanger, 0, 51, 55, IItemElectric.class));

		int var6;
		int var7;

		// Player inv:

		for (var6 = 0; var6 < 3; ++var6) {
			for (var7 = 0; var7 < 9; ++var7) {
				this.addSlotToContainer(new Slot(inventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 31 + 58 + var6 * 18));
			}
		}

		for (var6 = 0; var6 < 9; ++var6) {
			this.addSlotToContainer(new Slot(inventoryPlayer, var6, 8 + var6 * 18, 31 + 116));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return this.tileEntity.isUsableByPlayer(var1);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int par2) {
		ItemStack var3 = ItemStack.EMPTY;
		final Slot slot = this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			final ItemStack var5 = slot.getStack();
			var3 = var5.copy();

			if (par2 < 2) {
				if (!this.mergeItemStack(var5, 1, 37, true)) {
					return ItemStack.EMPTY;
				}
			} else {
				if (var5.getItem() instanceof IItemElectric) {
					if (!this.mergeItemStack(var5, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else {
					if (FluidUtil.isFuelContainerAny(var5)) {
						if (!this.mergeItemStack(var5, 1, 2, false)) {
							return ItemStack.EMPTY;
						}
					} else if (par2 < 29) {
						if (!this.mergeItemStack(var5, 29, 37, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!this.mergeItemStack(var5, 2, 29, false)) {
						return ItemStack.EMPTY;
					}
				}
			}

			if (var5.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (var5.getCount() == var3.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(entityPlayer, var5);
		}

		return var3;
	}
}
