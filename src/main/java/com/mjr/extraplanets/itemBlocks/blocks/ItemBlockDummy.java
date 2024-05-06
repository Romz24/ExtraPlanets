package com.mjr.extraplanets.itemBlocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;

public class ItemBlockDummy extends ItemBlock {
	public ItemBlockDummy(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return ClientProxyCore.galacticraftItem;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		int metadata = itemStack.getItemDamage();
		String blockName = "";

		switch (metadata) {
		case 0:
			blockName = "tier2LaunchPad";
			break;
		case 1:
			blockName = "tier3LaunchPad";
			break;
		default:
			blockName = null;
			break;
		}

		return this.getBlock().getUnlocalizedName() + "." + blockName;
	}

	@Override
	public String getUnlocalizedName() {
		return this.getBlock().getUnlocalizedName() + ".0";
	}
}
