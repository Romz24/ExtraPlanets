package com.mjr.extraplanets.tileEntities.machines;

import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.blocks.machines.BasicSmasher;
import com.mjr.extraplanets.items.ExtraPlanets_Items;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;

import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.miccore.Annotations.NetworkedField;

public class TileEntityBasicSmasher extends TileBaseElectricBlockWithInventory implements ISidedInventory {
	public static final int PROCESS_TIME_REQUIRED = 50;
	@NetworkedField(targetSide = Side.CLIENT)
	public int processTicks = 0;
	private ItemStack[] containingItems = new ItemStack[3];

	private ItemStack producingStack = new ItemStack(ExtraPlanets_Items.POTASH_SHARDS, 3, 0);

	public TileEntityBasicSmasher() {
	}

	@Override
	public void update() {
		super.update();

		if (!this.worldObj.isRemote) {
			if (this.canProcess() && canOutput() && this.hasEnoughEnergyToRun) {
				if (this.processTicks == 0) {
					this.processTicks = TileEntityBasicSmasher.PROCESS_TIME_REQUIRED;
				} else {
					if (--this.processTicks <= 0) {
						this.smeltItem();
						this.processTicks = this.canProcess() ? TileEntityBasicSmasher.PROCESS_TIME_REQUIRED : 0;
					}
				}
			} else {
				this.processTicks = 0;
			}
		}
	}

	public boolean canProcess() {
		if (this.containingItems[1] == null)
			return false;
		if (this.containingItems[1].getItem() != Item.getItemFromBlock(ExtraPlanets_Blocks.ORE_POTASH))
			return false;
		return !this.getDisabled(0);
	}

	public boolean canOutput() {
		ItemStack itemStack = this.producingStack;
		if (itemStack == null) {
			return false;
		}
		if (this.containingItems[2] == null) {
			return true;
		}
		if (!this.containingItems[2].isItemEqual(itemStack)) {
			return false;
		}
		int result = this.containingItems[2].stackSize + itemStack.stackSize;
		return result <= this.getInventoryStackLimit() && result <= itemStack.getMaxStackSize();
	}

	public boolean hasInputs() {
		if (this.containingItems[1] != null && this.containingItems[1].getItem() == Item.getItemFromBlock(ExtraPlanets_Blocks.ORE_POTASH))
			return true;
		return false;
	}

	public void smeltItem() {
		ItemStack resultItemStack = this.producingStack;
		if (this.canProcess() && canOutput() && hasInputs()) {
			if (this.containingItems[2] == null) {
				this.containingItems[2] = resultItemStack.copy();
			} else if (this.containingItems[2].isItemEqual(resultItemStack)) {
				if (this.containingItems[2].stackSize + resultItemStack.stackSize > 64) {
					for (int i = 0; i < this.containingItems[2].stackSize + resultItemStack.stackSize - 64; i++) {
						float var = 0.7F;
						double dx = this.worldObj.rand.nextFloat() * var + (1.0F - var) * 0.5D;
						double dy = this.worldObj.rand.nextFloat() * var + (1.0F - var) * 0.5D;
						double dz = this.worldObj.rand.nextFloat() * var + (1.0F - var) * 0.5D;
						EntityItem entityitem = new EntityItem(this.worldObj, this.getPos().getX() + dx, this.getPos().getY() + dy, this.getPos().getZ() + dz, new ItemStack(resultItemStack.getItem(), 1, resultItemStack.getItemDamage()));
						entityitem.setPickupDelay(10);
						this.worldObj.spawnEntityInWorld(entityitem);
					}
					this.containingItems[2].stackSize = 64;
				} else {
					this.containingItems[2].stackSize += resultItemStack.stackSize;
				}
			}
			this.decrStackSize(1, 1);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.processTicks = nbt.getInteger("smeltingTicks");
		this.containingItems = this.readStandardItemsFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("smeltingTicks", this.processTicks);
		this.writeStandardItemsToNBT(nbt);
	}

	@Override
	protected ItemStack[] getContainingItems() {
		return this.containingItems;
	}

	@Override
	public String getName() {
		return TranslateUtilities.translate("container.basic.smasher.name");
	}

	@Override
	public boolean hasCustomName() {
		return true;
	}

	// ISidedInventory Implementation:

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		if (side == EnumFacing.DOWN) {
			return new int[] { 2 };
		} else if (side == EnumFacing.UP) {
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
			case 1:
				return itemStack.getItem() == Item.getItemFromBlock(ExtraPlanets_Blocks.ORE_POTASH);
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
			case 2:
				return itemStack.getItem() == ExtraPlanets_Items.POTASH_SHARDS;
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
			return itemStack.getItem() == Item.getItemFromBlock(ExtraPlanets_Blocks.ORE_POTASH);
		case 2:
			return itemStack.getItem() == ExtraPlanets_Items.POTASH_SHARDS;
		}

		return false;
	}

	@Override
	public boolean shouldUseEnergy() {
		return this.canProcess();
	}

	@Override
	public EnumFacing getElectricInputDirection() {
		return EnumFacing.getHorizontal(((this.getBlockMetadata() & 3) + 1) % 4);
	}

	@Override
	public EnumFacing getFront() {
		IBlockState state = this.worldObj.getBlockState(getPos());
		if (state.getBlock() instanceof BasicSmasher) {
			return state.getValue(BasicSmasher.FACING);
		}
		return EnumFacing.NORTH;
	}
}
