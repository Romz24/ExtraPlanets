package com.mjr.extraplanets.inventory.rockets;

import com.mjr.extraplanets.recipes.Tier4RocketRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import micdoodle8.mods.galacticraft.core.inventory.SlotRocketBenchResult;

public class ContainerSchematicTier4Rocket extends Container {
	public InventorySchematicTier4Rocket craftMatrix = new InventorySchematicTier4Rocket(this);
	public IInventory craftResult = new InventoryCraftResult();
	private final World worldObj;

	public ContainerSchematicTier4Rocket(InventoryPlayer inventoryPlayer, BlockPos pos) {
		final int change = 27;
		this.worldObj = inventoryPlayer.player.world;
		this.addSlotToContainer(new SlotRocketBenchResult(inventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 142, 18 + 69 + change));
		int var6;
		int var7;

		// Cone
		this.addSlotToContainer(new SlotSchematicTier4Rocket(this.craftMatrix, 1, 48, -8 + change, pos, inventoryPlayer.player));

		// Body
		for (var6 = 0; var6 < 5; ++var6) {
			this.addSlotToContainer(new SlotSchematicTier4Rocket(this.craftMatrix, 2 + var6, 39, -6 + var6 * 18 + 16 + change, pos, inventoryPlayer.player));
		}

		// Body Right
		for (var6 = 0; var6 < 5; ++var6) {
			this.addSlotToContainer(new SlotSchematicTier4Rocket(this.craftMatrix, 7 + var6, 57, -6 + var6 * 18 + 16 + change, pos, inventoryPlayer.player));
		}

		// Left fins
		this.addSlotToContainer(new SlotSchematicTier4Rocket(this.craftMatrix, 12, 21, 64 + change, pos, inventoryPlayer.player));
		this.addSlotToContainer(new SlotSchematicTier4Rocket(this.craftMatrix, 13, 21, 82 + change, pos, inventoryPlayer.player));
		this.addSlotToContainer(new SlotSchematicTier4Rocket(this.craftMatrix, 14, 21, 100 + change, pos, inventoryPlayer.player));

		// Engine
		this.addSlotToContainer(new SlotSchematicTier4Rocket(this.craftMatrix, 15, 48, 100 + change, pos, inventoryPlayer.player));

		// Right fins
		this.addSlotToContainer(new SlotSchematicTier4Rocket(this.craftMatrix, 16, 75, 64 + change, pos, inventoryPlayer.player));
		this.addSlotToContainer(new SlotSchematicTier4Rocket(this.craftMatrix, 17, 75, 82 + change, pos, inventoryPlayer.player));
		this.addSlotToContainer(new SlotSchematicTier4Rocket(this.craftMatrix, 18, 75, 100 + change, pos, inventoryPlayer.player));

		// Addons
		for (int var8 = 0; var8 < 3; var8++) {
			this.addSlotToContainer(new SlotSchematicTier4Rocket(this.craftMatrix, 19 + var8, 93 + var8 * 26, -15 + change, pos, inventoryPlayer.player));
		}

		// Player inv:

		for (var6 = 0; var6 < 3; ++var6) {
			for (var7 = 0; var7 < 9; ++var7) {
				this.addSlotToContainer(new Slot(inventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 129 + var6 * 18 + change));
			}
		}

		for (var6 = 0; var6 < 9; ++var6) {
			this.addSlotToContainer(new Slot(inventoryPlayer, var6, 8 + var6 * 18, 18 + 169 + change));
		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	@Override
	public void onContainerClosed(EntityPlayer entityPlayer) {
		super.onContainerClosed(entityPlayer);

		if (!this.worldObj.isRemote) {
			for (int var2 = 1; var2 < this.craftMatrix.getSizeInventory(); ++var2) {
				final ItemStack var3 = this.craftMatrix.removeStackFromSlot(var2);

				if (!var3.isEmpty()) {
					entityPlayer.entityDropItem(var3, 0.0F);
				}
			}
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory par1IInventory) {
		this.craftResult.setInventorySlotContents(0, Tier4RocketRecipes.findMatchingTier4RocketRecipe(this.craftMatrix));
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int par1) {
		ItemStack var2 = ItemStack.EMPTY;
		final Slot var3 = this.inventorySlots.get(par1);

		if (var3 != null && var3.getHasStack()) {
			final ItemStack var4 = var3.getStack();
			var2 = var4.copy();

			if (par1 <= 21) {
				if (!this.mergeItemStack(var4, 22, 58, false)) {
					return ItemStack.EMPTY;
				}

				var3.onSlotChange(var4, var2);
			} else {
				boolean valid = false;
				for (int i = 1; i < 19; i++) {
					Slot testSlot = this.inventorySlots.get(i);
					if (!testSlot.getHasStack() && testSlot.isItemValid(var2)) {
						valid = true;
						break;
					}
				}
				if (valid) {
					if (!this.mergeOneItemTestValid(var4, 1, 19, false)) {
						return ItemStack.EMPTY;
					}
				} else {
					if (var2.getItem() == Item.getItemFromBlock(Blocks.CHEST)) {
						if (!this.mergeOneItemTestValid(var4, 19, 22, false)) {
							return ItemStack.EMPTY;
						}
					} else if (par1 >= 22 && par1 < 49) {
						if (!this.mergeItemStack(var4, 49, 58, false)) {
							return ItemStack.EMPTY;
						}
					} else if (par1 >= 49 && par1 < 58) {
						if (!this.mergeItemStack(var4, 22, 49, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!this.mergeItemStack(var4, 22, 58, false)) {
						return ItemStack.EMPTY;
					}
				}
			}

			if (var4.isEmpty()) {
				var3.putStack(ItemStack.EMPTY);
			} else {
				var3.onSlotChanged();
			}

			if (var4.getCount() == var2.getCount()) {
				return ItemStack.EMPTY;
			}

			var3.onTake(entityPlayer, var4);
		}

		return var2;
	}

	protected boolean mergeOneItemTestValid(ItemStack itemStack, int par2, int par3, boolean par4) {
		boolean flag1 = false;
		if (!itemStack.isEmpty()) {
			Slot slot;
			ItemStack slotStack;

			for (int k = par2; k < par3; k++) {
				slot = this.inventorySlots.get(k);
				slotStack = slot.getStack();

				if (slotStack.isEmpty() && slot.isItemValid(itemStack)) {
					ItemStack stackOneItem = itemStack.copy();
					stackOneItem.setCount(1);
					itemStack.shrink(1);
					slot.putStack(stackOneItem);
					slot.onSlotChanged();
					flag1 = true;
					break;
				}
			}
		}

		return flag1;
	}
}