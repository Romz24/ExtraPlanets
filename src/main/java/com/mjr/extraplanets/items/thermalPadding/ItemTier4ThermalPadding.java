package com.mjr.extraplanets.items.thermalPadding;

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
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.api.item.IItemThermal;
import micdoodle8.mods.galacticraft.core.entities.player.GCCapabilities;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.EnumColor;

public class ItemTier4ThermalPadding extends Item implements IItemThermal {
	public static String[] names = { "tier4_thermal_helm", "tier4_thermal_chestplate", "tier4_thermal_leggings", "tier4_thermal_boots" };

	public ItemTier4ThermalPadding(String name) {
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
		return ExtraPlanets.ArmorTab;
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List) {
		for (int i = 0; i < ItemTier4ThermalPadding.names.length; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		if (names.length > itemStack.getItemDamage()) {
			return "item." + ItemTier4ThermalPadding.names[itemStack.getItemDamage()];
		}

		return "unnamed";
	}

	@Override
	public int getMetadata(int par1) {
		return par1;
	}

	@Override
	public int getThermalStrength() {
		return 100;
	}

	@Override
	public boolean isValidForSlot(ItemStack stack, int armorSlot) {
		return stack.getItemDamage() == armorSlot;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
		if (player instanceof EntityPlayerMP) {
			GCPlayerStats stats = player.getCapability(GCCapabilities.GC_STATS_CAPABILITY, null);
			ItemStack gear = stats.getExtendedInventory().getStackInSlot(6);
			ItemStack gear1 = stats.getExtendedInventory().getStackInSlot(7);
			ItemStack gear2 = stats.getExtendedInventory().getStackInSlot(8);
			ItemStack gear3 = stats.getExtendedInventory().getStackInSlot(9);

			if (itemStack.getItemDamage() == 0) {
				if (gear == null) {
					stats.getExtendedInventory().setInventorySlotContents(6, itemStack.copy());
					itemStack.stackSize = 0;
				}
			} else if (itemStack.getItemDamage() == 1) {
				if (gear1 == null) {
					stats.getExtendedInventory().setInventorySlotContents(7, itemStack.copy());
					itemStack.stackSize = 0;
				}
			} else if (itemStack.getItemDamage() == 2) {
				if (gear2 == null) {
					stats.getExtendedInventory().setInventorySlotContents(8, itemStack.copy());
					itemStack.stackSize = 0;
				}
			} else if (itemStack.getItemDamage() == 3) {
				if (gear3 == null) {
					stats.getExtendedInventory().setInventorySlotContents(9, itemStack.copy());
					itemStack.stackSize = 0;
				}
			}
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean par4) {
		if (player.worldObj.isRemote) {
			list.add(EnumColor.AQUA + TranslateUtilities.translate("tier4.thermal.padding.information"));
		}
	}
}