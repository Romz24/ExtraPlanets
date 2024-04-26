package com.mjr.extraplanets.items;

import java.util.List;

import com.mjr.extraplanets.ExtraPlanets;
import com.mjr.mjrlegendslib.item.ItemBasicMeta;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.EnumColor;

public class ItemWafers extends ItemBasicMeta {

	public ItemWafers(String name) {
		super(name, ExtraPlanets.ItemsTab, getItemList());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean par4) {
		if (player.world.isRemote) {
			if (itemStack != null) {
				if (itemStack.getItemDamage() == 0 || itemStack.getItemDamage() == 1)
					list.add(EnumColor.YELLOW + TranslateUtilities.translate("currently.unused.name"));
				else
					list.add(EnumColor.AQUA + TranslateUtilities.translate("wafter.uses.information"));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return ClientProxyCore.galacticraftItem;
	}

	public static String[] getItemList() {
		return new String[] { "diamond_wafer", "carbon_wafer", "titanium_wafer", "red_gem_wafer", "blue_gem_wafer", "white_gem_wafer" };
	}
}