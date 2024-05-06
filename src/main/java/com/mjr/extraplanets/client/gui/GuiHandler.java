package com.mjr.extraplanets.client.gui;

import com.mjr.extraplanets.api.prefabs.entity.EntityElectricRocketBase;
import com.mjr.extraplanets.client.gui.machines.*;
import com.mjr.extraplanets.client.gui.rockets.GuiElectricRocketInventory;
import com.mjr.extraplanets.inventory.machines.*;
import com.mjr.extraplanets.inventory.rockets.ContainerElectricRocketInventory;
import com.mjr.extraplanets.tileEntities.machines.*;
import com.mjr.mjrlegendslib.util.MCUtilities;
import com.mjr.mjrlegendslib.util.PlayerUtilties;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.core.client.gui.GuiIdsCore;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		EntityPlayerMP playerBase = PlayerUtil.getPlayerBaseServerFromPlayer(player, false);

		if (playerBase == null) {
			PlayerUtilties.sendMessage(player, "ExtraPlanets player instance null server-side. This is a bug.");
			return null;
		}

		if (ID == GuiIdsCore.ROCKET_INVENTORY && player.ridingEntity instanceof EntityElectricRocketBase) {
			return new ContainerElectricRocketInventory(player.inventory, (EntityElectricRocketBase) player.ridingEntity, ((EntityElectricRocketBase) player.ridingEntity).getType(), player);
		}

		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);

		if (tile != null) {
			if (tile instanceof TileEntityAdvancedRefinery) {
				return new ContainerAdvancedRefinery(player.inventory, (TileEntityAdvancedRefinery) tile, player);
			} else if (tile instanceof TileEntityUltimateRefinery) {
				return new ContainerUltimateRefinery(player.inventory, (TileEntityUltimateRefinery) tile, player);
			} else if (tile instanceof TileEntitySolar) {
				return new ContainerSolar(player.inventory, (TileEntitySolar) tile);
			} else if (tile instanceof TileEntityAdvancedOxygenCompressor) {
				return new ContainerAdvancedOxygenCompressor(player.inventory, (TileEntityAdvancedOxygenCompressor) tile, player);
			} else if (tile instanceof TileEntityAdvancedOxygenDecompressor) {
				return new ContainerAdvancedOxygenDecompressor(player.inventory, (TileEntityAdvancedOxygenDecompressor) tile, player);
			} else if (tile instanceof TileEntityUltimateOxygenCompressor) {
				return new ContainerUltimateOxygenCompressor(player.inventory, (TileEntityUltimateOxygenCompressor) tile, player);
			} else if (tile instanceof TileEntityUltimateOxygenDecompressor) {
				return new ContainerUltimateOxygenDecompressor(player.inventory, (TileEntityUltimateOxygenDecompressor) tile, player);
			} else if (tile instanceof TileEntityBasicDecrystallizer) {
				return new ContainerBasicDecrystallizer(player.inventory, (TileEntityBasicDecrystallizer) tile, player);
			} else if (tile instanceof TileEntityBasicCrystallizer) {
				return new ContainerBasicCrystallizer(player.inventory, (TileEntityBasicCrystallizer) tile, player);
			} else if (tile instanceof TileEntityBasicSmasher) {
				return new ContainerBasicSmasher(player.inventory, (TileEntityBasicSmasher) tile, player);
			} else if (tile instanceof TileEntityBasicChemicalInjector) {
				return new ContainerBasicChemicalInjector(player.inventory, (TileEntityBasicChemicalInjector) tile, player);
			} else if (tile instanceof TileEntityBasicSolarEvaporationChamber) {
				return new ContainerBasicSolarEvaporationChamber(player.inventory, (TileEntityBasicSolarEvaporationChamber) tile, player);
			} else if (tile instanceof TileEntityAdvancedFuelLoader) {
				return new ContainerAdvancedFuelLoader(player.inventory, (TileEntityAdvancedFuelLoader) tile);
			} else if (tile instanceof TileEntityUltimateFuelLoader) {
				return new ContainerUltimateFuelLoader(player.inventory, (TileEntityUltimateFuelLoader) tile);
			} else if (tile instanceof TileEntityVehicleChanger) {
				return new ContainerVehicleChanger(player.inventory, (TileEntityVehicleChanger) tile);
			} else if (tile instanceof TileEntityBasicPurifier) {
				return new ContainerBasicPurifier(player.inventory, (TileEntityBasicPurifier) tile, player);
			} else if (tile instanceof TileEntityBasicDensifier) {
				return new ContainerBasicDensifier(player.inventory, (TileEntityBasicDensifier) tile, player);
			} else if (tile instanceof TileEntityBasicDecontaminationUnit) {
				return new ContainerBasicDecontaminationUnit(player.inventory, (TileEntityBasicDecontaminationUnit) tile, player);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (MCUtilities.isClient()) {
			return this.getClientGuiElement(ID, player, world, new BlockPos(x, y, z));
		}

		return null;
	}

	@SideOnly(Side.CLIENT)
	private Object getClientGuiElement(int ID, EntityPlayer player, World world, BlockPos position) {
		if (ID == GuiIdsCore.ROCKET_INVENTORY && player.ridingEntity instanceof EntityElectricRocketBase) {
			return new GuiElectricRocketInventory(player.inventory, (EntityElectricRocketBase) player.ridingEntity, ((EntityElectricRocketBase) player.ridingEntity).getType());

		}
		TileEntity tile = world.getTileEntity(position);

		if (tile != null) {
			if (tile instanceof TileEntityAdvancedRefinery) {
				return new GuiAdvancedRefinery(player.inventory, (TileEntityAdvancedRefinery) world.getTileEntity(position));
			} else if (tile instanceof TileEntityUltimateRefinery) {
				return new GuiUltimateRefinery(player.inventory, (TileEntityUltimateRefinery) world.getTileEntity(position));
			} else if (tile instanceof TileEntitySolar) {
				return new GuiSolar(player.inventory, (TileEntitySolar) tile);
			} else if (tile instanceof TileEntityAdvancedOxygenCompressor) {
				return new GuiAdvancedOxygenCompressor(player.inventory, (TileEntityAdvancedOxygenCompressor) tile);
			} else if (tile instanceof TileEntityAdvancedOxygenDecompressor) {
				return new GuiAdvancedOxygenDecompressor(player.inventory, (TileEntityAdvancedOxygenDecompressor) tile);
			} else if (tile instanceof TileEntityUltimateOxygenCompressor) {
				return new GuiUltimateOxygenCompressor(player.inventory, (TileEntityUltimateOxygenCompressor) tile);
			} else if (tile instanceof TileEntityUltimateOxygenDecompressor) {
				return new GuiUltimateOxygenDecompressor(player.inventory, (TileEntityUltimateOxygenDecompressor) tile);
			} else if (tile instanceof TileEntityBasicDecrystallizer) {
				return new GuiBasicDecrystallizer(player.inventory, (TileEntityBasicDecrystallizer) tile);
			} else if (tile instanceof TileEntityBasicCrystallizer) {
				return new GuiBasicCrystallizer(player.inventory, (TileEntityBasicCrystallizer) tile);
			} else if (tile instanceof TileEntityBasicSmasher) {
				return new GuiBasicSmasher(player.inventory, (TileEntityBasicSmasher) tile);
			} else if (tile instanceof TileEntityBasicChemicalInjector) {
				return new GuiBasicChemicalInjector(player.inventory, (TileEntityBasicChemicalInjector) tile);
			} else if (tile instanceof TileEntityBasicSolarEvaporationChamber) {
				return new GuiBasicSolarEvaporationChamber(player.inventory, (TileEntityBasicSolarEvaporationChamber) tile);
			} else if (tile instanceof TileEntityAdvancedFuelLoader) {
				return new GuiAdvancedFuelLoader(player.inventory, (TileEntityAdvancedFuelLoader) tile);
			} else if (tile instanceof TileEntityUltimateFuelLoader) {
				return new GuiUltimateFuelLoader(player.inventory, (TileEntityUltimateFuelLoader) tile);
			} else if (tile instanceof TileEntityVehicleChanger) {
				return new GuiVehicleChanger(player.inventory, (TileEntityVehicleChanger) tile);
			} else if (tile instanceof TileEntityBasicPurifier) {
				return new GuiBasicPurifier(player.inventory, (TileEntityBasicPurifier) tile);
			} else if (tile instanceof TileEntityBasicDensifier) {
				return new GuiBasicDensifier(player.inventory, (TileEntityBasicDensifier) tile);
			} else if (tile instanceof TileEntityBasicDecontaminationUnit) {
				return new GuiBasicDecontaminationUnit(player.inventory, (TileEntityBasicDecontaminationUnit) tile);
			}
		}
		return null;
	}
}