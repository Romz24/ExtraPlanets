package com.mjr.extraplanets.items.planetAndMoonItems;

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

public class ItemTier6Items extends ItemBasicMeta {

	public ItemTier6Items(String name) {
		super(name, ExtraPlanets.ItemsTab, getItemList());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return ClientProxyCore.galacticraftItem;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean par4) {
		if (itemStack != null && itemStack.getItemDamage() == 3) {
			if (player.world.isRemote) {
				list.add(EnumColor.GREY + TranslateUtilities.translate("tier6.heavy_duty_plate.name"));
			}
		}
	}

	public static String[] getItemList() {
		return new String[] { "tier6engine", "tier6booster", "tier6fin", "tier6heavy_duty_plate", "compressed_magnesium", "ingot_magnesium" };
	}
}
