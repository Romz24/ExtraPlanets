package com.mjr.extraplanets.items;

import java.util.List;

import com.mjr.extraplanets.ExtraPlanets;
import com.mjr.mjrlegendslib.item.ItemBasicMeta;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.venus.VenusItems;

public class ItemBasicKit extends ItemBasicMeta {
	public int tier;

	public ItemBasicKit(String name, int tier) {
		super(name, ExtraPlanets.ItemsTab, getItemList());
		this.tier = tier;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return ClientProxyCore.galacticraftItem;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List<String> tooltip, boolean par4) {
		tooltip.add(EnumColor.BRIGHT_GREEN + TranslateUtilities.translate("item.kit.information.desc"));
		tooltip.add(EnumColor.YELLOW + TranslateUtilities.translate("item.kit.information.use"));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
		if (player instanceof EntityPlayerMP) {
			GCPlayerStats stats = GCPlayerStats.get(player);
			// Oxygen Setup
			ItemStack gear = stats.getExtendedInventory().getStackInSlot(0);
			ItemStack gear1 = stats.getExtendedInventory().getStackInSlot(1);
			ItemStack gear2 = stats.getExtendedInventory().getStackInSlot(2);
			ItemStack gear3 = stats.getExtendedInventory().getStackInSlot(3);
			// Parachute
			ItemStack gear4 = stats.getExtendedInventory().getStackInSlot(4);
			// Frequency Module
			ItemStack gear5 = stats.getExtendedInventory().getStackInSlot(5);
			// Thermal Padding
			ItemStack gear6 = stats.getExtendedInventory().getStackInSlot(6);
			ItemStack gear7 = stats.getExtendedInventory().getStackInSlot(7);
			ItemStack gear8 = stats.getExtendedInventory().getStackInSlot(8);
			ItemStack gear9 = stats.getExtendedInventory().getStackInSlot(9);
			// Shield
			ItemStack gear10 = stats.getExtendedInventory().getStackInSlot(10);
			ItemStack thermalPadding1 = null;
			ItemStack thermalPadding2 = null;
			ItemStack thermalPadding3 = null;
			ItemStack thermalPadding4 = null;

			ItemStack oxygenTank1 = null;
			ItemStack oxygenTank2 = null;

			// Tier 1 = Overworld Space Station, Moon, Mars, Asteroids
			// Tier 2 = Venus
			// Tier 3 = Mercury, Mercury Space Station - Tier 1 Space Suit
			// Tier 4 = Jupiter and Saturn including their moons - Tier 2 Space Suit
			// Tier 5 = Uranus, Neptune, Pluto and Eris including their moons - Tier 3 Space Suit
			// Tier 6 = Same as Tier 5 kit - Tier 4 Space Suit

			if (this.tier == 1) {
				thermalPadding1 = new ItemStack(AsteroidsItems.thermalPadding, 1, 0);
				thermalPadding2 = new ItemStack(AsteroidsItems.thermalPadding, 1, 1);
				thermalPadding3 = new ItemStack(AsteroidsItems.thermalPadding, 1, 2);
				thermalPadding4 = new ItemStack(AsteroidsItems.thermalPadding, 1, 3);
				oxygenTank1 = new ItemStack(GCItems.oxTankLight);
				oxygenTank2 = new ItemStack(GCItems.oxTankLight);
			} else if (this.tier == 2) {
				thermalPadding1 = new ItemStack(VenusItems.thermalPaddingTier2, 1, 0);
				thermalPadding2 = new ItemStack(VenusItems.thermalPaddingTier2, 1, 1);
				thermalPadding3 = new ItemStack(VenusItems.thermalPaddingTier2, 1, 2);
				thermalPadding4 = new ItemStack(VenusItems.thermalPaddingTier2, 1, 3);
				oxygenTank1 = new ItemStack(GCItems.oxTankMedium);
				oxygenTank2 = new ItemStack(GCItems.oxTankMedium);
			} else if (this.tier == 3) {
				thermalPadding1 = new ItemStack(ExtraPlanets_Items.TIER_3_THERMAL_PADDING, 1, 0);
				thermalPadding2 = new ItemStack(ExtraPlanets_Items.TIER_3_THERMAL_PADDING, 1, 1);
				thermalPadding3 = new ItemStack(ExtraPlanets_Items.TIER_3_THERMAL_PADDING, 1, 2);
				thermalPadding4 = new ItemStack(ExtraPlanets_Items.TIER_3_THERMAL_PADDING, 1, 3);
				oxygenTank1 = new ItemStack(GCItems.oxTankHeavy);
				oxygenTank2 = new ItemStack(GCItems.oxTankHeavy);
			} else if (this.tier == 4) {
				thermalPadding1 = new ItemStack(ExtraPlanets_Items.TIER_4_THERMAL_PADDING, 1, 0);
				thermalPadding2 = new ItemStack(ExtraPlanets_Items.TIER_4_THERMAL_PADDING, 1, 1);
				thermalPadding3 = new ItemStack(ExtraPlanets_Items.TIER_4_THERMAL_PADDING, 1, 2);
				thermalPadding4 = new ItemStack(ExtraPlanets_Items.TIER_4_THERMAL_PADDING, 1, 3);
				oxygenTank1 = new ItemStack(ExtraPlanets_Items.OXYGEN_TANK_VERY_HEAVY);
				oxygenTank2 = new ItemStack(ExtraPlanets_Items.OXYGEN_TANK_VERY_HEAVY);
			} else if (this.tier == 5) {
				thermalPadding1 = new ItemStack(ExtraPlanets_Items.TIER_5_THERMAL_PADDING, 1, 0);
				thermalPadding2 = new ItemStack(ExtraPlanets_Items.TIER_5_THERMAL_PADDING, 1, 1);
				thermalPadding3 = new ItemStack(ExtraPlanets_Items.TIER_5_THERMAL_PADDING, 1, 2);
				thermalPadding4 = new ItemStack(ExtraPlanets_Items.TIER_5_THERMAL_PADDING, 1, 3);
				oxygenTank1 = new ItemStack(ExtraPlanets_Items.OXYGEN_TANK_EXTREMELY_HEAVY);
				oxygenTank2 = new ItemStack(ExtraPlanets_Items.OXYGEN_TANK_EXTREMELY_HEAVY);
			}

			// Basic Kit adding
			switch (itemStack.getMetadata()) {
			case 0:
				if (gear == null) {
					stats.getExtendedInventory().setInventorySlotContents(0, new ItemStack(GCItems.oxMask));
				}
				if (gear1 == null) {
					stats.getExtendedInventory().setInventorySlotContents(1, new ItemStack(GCItems.oxygenGear));
				}
				if (gear2 == null) {
					stats.getExtendedInventory().setInventorySlotContents(2, oxygenTank1);
				}
				if (gear3 == null) {
					stats.getExtendedInventory().setInventorySlotContents(3, oxygenTank2);
				}
				if (gear4 == null) {
					stats.getExtendedInventory().setInventorySlotContents(4, new ItemStack(GCItems.parachute));
				}
				if (gear5 == null) {
					stats.getExtendedInventory().setInventorySlotContents(5, new ItemStack(GCItems.basicItem, 1, 19));
				}
				if (gear6 == null) {
					stats.getExtendedInventory().setInventorySlotContents(6, thermalPadding1);
				}
				if (gear7 == null) {
					stats.getExtendedInventory().setInventorySlotContents(7, thermalPadding2);
				}
				if (gear8 == null) {
					stats.getExtendedInventory().setInventorySlotContents(8, thermalPadding3);
				}
				if (gear9 == null) {
					stats.getExtendedInventory().setInventorySlotContents(9, thermalPadding4);
				}
				if (gear10 == null && this.tier >= 2) {
					stats.getExtendedInventory().setInventorySlotContents(10, new ItemStack(VenusItems.basicItem, 1, 0));
				}
				break;
			case 1:
				if (gear == null) {
					stats.getExtendedInventory().setInventorySlotContents(0, new ItemStack(GCItems.oxMask));
				}
				if (gear1 == null) {
					stats.getExtendedInventory().setInventorySlotContents(1, new ItemStack(GCItems.oxygenGear));
				}
				if (gear2 == null) {
					stats.getExtendedInventory().setInventorySlotContents(2, oxygenTank1);
				}
				if (gear3 == null) {
					stats.getExtendedInventory().setInventorySlotContents(3, oxygenTank2);
				}
				if (gear4 == null) {
					stats.getExtendedInventory().setInventorySlotContents(4, new ItemStack(GCItems.parachute));
				}
				break;
			case 2:
				if (gear4 == null) {
					stats.getExtendedInventory().setInventorySlotContents(4, new ItemStack(GCItems.parachute));
				}
				if (gear5 == null) {
					stats.getExtendedInventory().setInventorySlotContents(5, new ItemStack(GCItems.basicItem, 1, 19));
				}
				if (gear6 == null) {
					stats.getExtendedInventory().setInventorySlotContents(6, thermalPadding1);
				}
				if (gear7 == null) {
					stats.getExtendedInventory().setInventorySlotContents(7, thermalPadding2);
				}
				if (gear8 == null) {
					stats.getExtendedInventory().setInventorySlotContents(8, thermalPadding3);
				}
				if (gear9 == null) {
					stats.getExtendedInventory().setInventorySlotContents(9, thermalPadding4);
				}
				if (gear10 == null && this.tier >= 2) {
					stats.getExtendedInventory().setInventorySlotContents(10, new ItemStack(VenusItems.basicItem, 1, 0));
				}
				break;
			case 3:
				if (gear == null) {
					stats.getExtendedInventory().setInventorySlotContents(0, new ItemStack(GCItems.oxMask));
				}
				if (gear1 == null) {
					stats.getExtendedInventory().setInventorySlotContents(1, new ItemStack(GCItems.oxygenGear));
				}
				if (gear2 == null) {
					stats.getExtendedInventory().setInventorySlotContents(2, oxygenTank1);
				}
				if (gear3 == null) {
					stats.getExtendedInventory().setInventorySlotContents(3, oxygenTank2);
				}
				if (gear4 == null) {
					stats.getExtendedInventory().setInventorySlotContents(4, new ItemStack(GCItems.parachute));
				}
				if (gear5 == null) {
					stats.getExtendedInventory().setInventorySlotContents(5, new ItemStack(GCItems.basicItem, 1, 19));
				}
				if (gear10 == null && this.tier >= 2) {
					stats.getExtendedInventory().setInventorySlotContents(10, new ItemStack(VenusItems.basicItem, 1, 0));
				}
				break;
			case 4:
				if (gear == null) {
					stats.getExtendedInventory().setInventorySlotContents(0, new ItemStack(GCItems.oxMask));
				}
				if (gear1 == null) {
					stats.getExtendedInventory().setInventorySlotContents(1, new ItemStack(GCItems.oxygenGear));
				}
				if (gear2 == null) {
					stats.getExtendedInventory().setInventorySlotContents(2, oxygenTank1);
				}
				if (gear3 == null) {
					stats.getExtendedInventory().setInventorySlotContents(3, oxygenTank2);
				}
				break;
			case 5:
				if (gear6 == null) {
					stats.getExtendedInventory().setInventorySlotContents(6, thermalPadding1);
				}
				if (gear7 == null) {
					stats.getExtendedInventory().setInventorySlotContents(7, thermalPadding2);
				}
				if (gear8 == null) {
					stats.getExtendedInventory().setInventorySlotContents(8, thermalPadding3);
				}
				if (gear9 == null) {
					stats.getExtendedInventory().setInventorySlotContents(9, thermalPadding4);
				}
				if (gear10 == null && this.tier >= 2) {
					stats.getExtendedInventory().setInventorySlotContents(10, new ItemStack(VenusItems.basicItem, 1, 0));
				}
				break;
			}
			itemStack.stackSize = 0;
		}
		return new ActionResult<>(EnumActionResult.PASS, itemStack);
	}

	public static String[] getItemList() {
		return new String[] { "full", "basic_setup", "without_oxygen_setup", "without_thermal_padding", "just_oxygen_setup", "just_protection" };
	}
}
