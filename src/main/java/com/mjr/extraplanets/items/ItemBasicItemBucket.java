package com.mjr.extraplanets.items;

import com.mjr.extraplanets.ExtraPlanets;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;

public class ItemBasicItemBucket extends ItemBucket {
	public ItemBasicItemBucket(String name, Block block) {
		super(block);
		setMaxStackSize(1);
		setUnlocalizedName(name);
		setContainerItem(Items.BUCKET);
	}

	@SideOnly(Side.CLIENT)
	public EnumRarity func_77613_e(ItemStack itemStack) {
		return ClientProxyCore.galacticraftItem;
	}

	public CreativeTabs func_77640_w() {
		return ExtraPlanets.ItemsTab;
	}
}
