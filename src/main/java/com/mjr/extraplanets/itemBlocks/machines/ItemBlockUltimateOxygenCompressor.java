package com.mjr.extraplanets.itemBlocks.machines;

import com.mjr.extraplanets.blocks.machines.UltimateOxygenCompressor;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc;

public class ItemBlockUltimateOxygenCompressor extends ItemBlockDesc {
	public ItemBlockUltimateOxygenCompressor(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		int metadata = 0;

		if (itemStack.getItemDamage() >= UltimateOxygenCompressor.OXYGEN_DECOMPRESSOR_METADATA) {
			metadata = 1;
		} else if (itemStack.getItemDamage() >= UltimateOxygenCompressor.OXYGEN_COMPRESSOR_METADATA) {
			metadata = 0;
		}

		return this.getBlock().getUnlocalizedName() + "." + metadata;
	}

	@Override
	public String getUnlocalizedName() {
		return this.getBlock().getUnlocalizedName() + ".0";
	}
}
