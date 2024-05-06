package com.mjr.extraplanets.items;

import java.util.List;

import com.mjr.extraplanets.ExtraPlanets;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.items.ItemOxygenTank;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;

public class ItemCustomOxygenTank extends ItemOxygenTank {
	public ItemCustomOxygenTank(int tier, String name) {
		super(tier, name);
		this.setMaxStackSize(1);
		this.setMaxDamage(tier * 900);
		this.setUnlocalizedName(name);
		this.setNoRepair();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, this.getMaxDamage()));
	}

	@Override
	public CreativeTabs getCreativeTab() {
		return ExtraPlanets.ItemsTab;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return ClientProxyCore.galacticraftItem;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> tooltip, boolean b) {
		tooltip.add(TranslateUtilities.translate("gui.tank.oxygen_remaining") + ": " + (itemStack.getMaxDamage() - itemStack.getItemDamage()));
	}

	@Override
	public EnumSortCategoryItem getCategory(int meta) {
		return EnumSortCategoryItem.GEAR;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (player instanceof EntityPlayerMP) {
			GCPlayerStats stats = GCPlayerStats.get(player);
			ItemStack gear = stats.getExtendedInventory().getStackInSlot(2);
			ItemStack gear1 = stats.getExtendedInventory().getStackInSlot(3);

			if (gear == null) {
				stats.getExtendedInventory().setInventorySlotContents(2, itemStack.copy());
				itemStack.stackSize = 0;
			} else if (gear1 == null) {
				stats.getExtendedInventory().setInventorySlotContents(3, itemStack.copy());
				itemStack.stackSize = 0;
			}
		}
		return itemStack;
	}
}