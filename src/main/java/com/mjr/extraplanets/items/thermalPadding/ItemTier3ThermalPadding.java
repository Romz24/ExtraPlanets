package com.mjr.extraplanets.items.thermalPadding;

import java.util.List;

import javax.annotation.Nullable;

import com.mjr.extraplanets.ExtraPlanets;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.client.util.ITooltipFlag;
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

import micdoodle8.mods.galacticraft.api.item.IItemThermal;
import micdoodle8.mods.galacticraft.core.entities.player.GCCapabilities;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.EnumColor;

public class ItemTier3ThermalPadding extends Item implements IItemThermal {
	public static String[] names = { "tier3_thermal_helm", "tier3_thermal_chestplate", "tier3_thermal_leggings", "tier3_thermal_boots" };

	public ItemTier3ThermalPadding(String name) {
		super();
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return ClientProxyCore.galacticraftItem;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public CreativeTabs getCreativeTab() {
		return ExtraPlanets.ItemsTab;
	}

	@Override
	public void getSubItems(CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
		if (!this.isInCreativeTab(par2CreativeTabs))
			return;
		for (int i = 0; i < ItemTier3ThermalPadding.names.length; i++) {
			par3List.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		if (names.length > itemStack.getItemDamage()) {
			return "item." + ItemTier3ThermalPadding.names[itemStack.getItemDamage()];
		}

		return "unnamed";
	}

	@Override
	public int getMetadata(int par1) {
		return par1;
	}

	@Override
	public int getThermalStrength() {
		return 50;
	}

	@Override
	public boolean isValidForSlot(ItemStack stack, int armorSlot) {
		return stack.getItemDamage() == armorSlot;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerIn, EnumHand hand) {
		ItemStack itemStackIn = playerIn.getHeldItem(hand);
		if (playerIn instanceof EntityPlayerMP) {
			GCPlayerStats stats = playerIn.getCapability(GCCapabilities.GC_STATS_CAPABILITY, null);
			ItemStack gear = stats.getExtendedInventory().getStackInSlot(6);
			ItemStack gear1 = stats.getExtendedInventory().getStackInSlot(7);
			ItemStack gear2 = stats.getExtendedInventory().getStackInSlot(8);
			ItemStack gear3 = stats.getExtendedInventory().getStackInSlot(9);

			if (itemStackIn.getItemDamage() == 0) {
				if (gear.isEmpty()) {
					stats.getExtendedInventory().setInventorySlotContents(6, itemStackIn.copy());
					itemStackIn.setCount(0);

				}
			} else if (itemStackIn.getItemDamage() == 1) {
				if (gear1.isEmpty()) {
					stats.getExtendedInventory().setInventorySlotContents(7, itemStackIn.copy());
					itemStackIn.setCount(0);

				}
			} else if (itemStackIn.getItemDamage() == 2) {
				if (gear2.isEmpty()) {
					stats.getExtendedInventory().setInventorySlotContents(8, itemStackIn.copy());
					itemStackIn.setCount(0);

				}
			} else if (itemStackIn.getItemDamage() == 3) {
				if (gear3.isEmpty()) {
					stats.getExtendedInventory().setInventorySlotContents(9, itemStackIn.copy());
					itemStackIn.setCount(0);

				}
			}
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, @Nullable World world, List<String> list, ITooltipFlag flagIn) {
		list.add(EnumColor.AQUA + TranslateUtilities.translate("tier3.thermal.padding.information"));
	}
}