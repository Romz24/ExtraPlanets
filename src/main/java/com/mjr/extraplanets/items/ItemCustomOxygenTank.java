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
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
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

	@SuppressWarnings("deprecation")
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, this.getMaxDamage()));
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
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerIn, EnumHand hand) {
		ItemStack itemStackIn = playerIn.getHeldItem(hand);
		if (playerIn instanceof EntityPlayerMP) {
			GCPlayerStats stats = GCPlayerStats.get(playerIn);
			ItemStack gear = stats.getExtendedInventory().getStackInSlot(2);
			ItemStack gear1 = stats.getExtendedInventory().getStackInSlot(3);

			if (gear.isEmpty()) {
				stats.getExtendedInventory().setInventorySlotContents(2, itemStackIn.copy());
				itemStackIn = ItemStack.EMPTY;
			} else if (gear1.isEmpty()) {
				stats.getExtendedInventory().setInventorySlotContents(3, itemStackIn.copy());
				itemStackIn = ItemStack.EMPTY;
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
	}
}