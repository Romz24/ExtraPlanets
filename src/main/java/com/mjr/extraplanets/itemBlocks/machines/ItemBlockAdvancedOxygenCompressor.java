package com.mjr.extraplanets.itemBlocks.machines;

import com.mjr.extraplanets.blocks.machines.AdvancedOxygenCompressor;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc;

public class ItemBlockAdvancedOxygenCompressor extends ItemBlockDesc {
	public ItemBlockAdvancedOxygenCompressor(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		int metadata = 0;

		if (itemstack.getItemDamage() >= AdvancedOxygenCompressor.OXYGEN_DECOMPRESSOR_METADATA) {
			metadata = 1;
		} else if (itemstack.getItemDamage() >= AdvancedOxygenCompressor.OXYGEN_COMPRESSOR_METADATA) {
			metadata = 0;
		}

		return this.getBlock().getUnlocalizedName() + "." + metadata;
	}

	@Override
	public String getUnlocalizedName() {
		return this.getBlock().getUnlocalizedName() + ".0";
	}
}
