package com.mjr.extraplanets.items.thermalPadding;

import java.util.List;

import com.mjr.extraplanets.ExtraPlanets;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;

public class ItemThermalCloth extends Item {
	public static String[] names = { "tier3_thermal_cloth", "tier4_thermal_cloth", "tier5_thermal_cloth" };

	public ItemThermalCloth(String name) {
		super();
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setCreativeTab(ExtraPlanets.ItemsTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return ClientProxyCore.galacticraftItem;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return this.getUnlocalizedName() + "." + ItemThermalCloth.names[itemStack.getItemDamage()];
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List<ItemStack> list) {
		for (int i = 0; i < ItemThermalCloth.names.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int getMetadata(int item) {
		return item;
	}
}