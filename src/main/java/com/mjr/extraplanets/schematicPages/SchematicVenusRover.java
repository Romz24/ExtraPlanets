package com.mjr.extraplanets.schematicPages;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.client.gui.vehicles.GuiSchematicVenusRover;
import com.mjr.extraplanets.inventory.vehicles.ContainerSchematicVenusRover;
import com.mjr.extraplanets.items.ExtraPlanets_Items;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.api.recipe.SchematicPage;

public class SchematicVenusRover extends SchematicPage {
	@Override
	public int getPageID() {
		return Config.SCHEMATIC_VENUS_ROVER_PAGE_ID;
	}

	@Override
	public int getGuiID() {
		return Config.SCHEMATIC_VENUS_ROVER_GUI_ID;
	}

	@Override
	public ItemStack getRequiredItem() {
		return new ItemStack(ExtraPlanets_Items.VENUS_ROVER_SCHEMATIC, 1, 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiScreen getResultScreen(EntityPlayer player, BlockPos pos) {
		return new GuiSchematicVenusRover(player.inventory, pos);
	}

	@Override
	public Container getResultContainer(EntityPlayer player, BlockPos pos) {
		return new ContainerSchematicVenusRover(player.inventory, pos);
	}
}