package com.mjr.extraplanets.items.planetAndMoonItems;

import java.util.List;

import javax.annotation.Nullable;

import com.mjr.extraplanets.ExtraPlanets;
import com.mjr.mjrlegendslib.item.ItemBasicMeta;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.EnumColor;

public class ItemTier7Items extends ItemBasicMeta {

	public ItemTier7Items(String name) {
		super(name, ExtraPlanets.ItemsTab, getItemList());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return ClientProxyCore.galacticraftItem;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, @Nullable World world, List<String> list, ITooltipFlag flagIn) {
		if (itemStack != null && itemStack.getItemDamage() == 3) {
			list.add(EnumColor.GREY + TranslateUtilities.translate("tier7.heavy_duty_plate.name"));
		}
	}

	public static String[] getItemList() {
		return new String[] { "tier7engine", "tier7booster", "tier7fin", "tier7heavy_duty_plate", "compressed_crystal", "ingot_crystal", "compressed_reinforced_crystal", "white_gem" };
	}
}
