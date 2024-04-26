package com.mjr.extraplanets.items;

import com.mjr.extraplanets.ExtraPlanets;
import com.mjr.mjrlegendslib.item.ItemBasicMeta;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;

public class ItemModuleItems extends ItemBasicMeta {

	public ItemModuleItems(String name) {
		super(name, ExtraPlanets.ItemsTab, getItemList());
		this.setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return ClientProxyCore.galacticraftItem;
	}

	public static String[] getItemList() {
		return new String[] { "no_fall_boots", "oxygen_controller", "space_gear_controller" };
	}
}
