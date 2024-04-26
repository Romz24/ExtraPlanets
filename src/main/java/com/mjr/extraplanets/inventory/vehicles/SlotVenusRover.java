package com.mjr.extraplanets.inventory.vehicles;

import java.util.List;

import com.mjr.extraplanets.recipes.VenusRoverRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

public class SlotVenusRover extends Slot {
	private final int index;
	private final BlockPos pos;
	private final EntityPlayer player;

	public SlotVenusRover(IInventory par2IInventory, int par3, int par4, int par5, BlockPos pos, EntityPlayer player) {
		super(par2IInventory, par3, par4, par5);
		this.index = par3;
		this.pos = pos;
		this.player = player;
	}

	@Override
	public void onSlotChanged() {
		if (this.player instanceof EntityPlayerMP) {
			int dimID = GCCoreUtil.getDimensionID(this.player.world);
			GCCoreUtil.sendToAllAround(new PacketSimple(EnumSimplePacket.C_SPAWN_SPARK_PARTICLES, dimID, new Object[] { this.pos }), this.player.world, dimID, this.pos, 20);
		}
	}

	@Override
	public boolean isItemValid(ItemStack itemStack) {
		if (itemStack == null)
			return false;

		List<INasaWorkbenchRecipe> recipes = VenusRoverRecipes.getVenusRoverRecipes();
		for (INasaWorkbenchRecipe recipe : recipes) {
			if (ItemStack.areItemsEqual(itemStack, recipe.getRecipeInput().get(this.index)))
				return true;
		}
		return false;
	}

	/**
	 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case of armor slots)
	 */
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}