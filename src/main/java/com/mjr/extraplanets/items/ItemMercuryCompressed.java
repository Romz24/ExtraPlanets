package com.mjr.extraplanets.items;

import com.mjr.extraplanets.ExtraPlanets;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;

public class ItemMercuryCompressed extends Item {
	public ItemMercuryCompressed(String name) {
		super();
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setUnlocalizedName(name);
		this.setCreativeTab(ExtraPlanets.ItemsTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return ClientProxyCore.galacticraftItem;
	}
}