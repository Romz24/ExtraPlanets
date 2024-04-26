package com.mjr.extraplanets.blocks.machines;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.itemBlocks.machines.ItemBlockAdvancedOxygenCompressor;
import com.mjr.extraplanets.itemBlocks.machines.ItemBlockBasicDecontaminationUnit;
import com.mjr.extraplanets.itemBlocks.machines.ItemBlockSolar;
import com.mjr.extraplanets.itemBlocks.machines.ItemBlockUltimateOxygenCompressor;
import com.mjr.extraplanets.tileEntities.machines.*;
import com.mjr.mjrlegendslib.util.RegisterUtilities;

import net.minecraft.block.Block;

import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc;

public class ExtraPlanets_Machines {
	// Machines
	public static Block REFINERY_ADVANCED;
	public static Block REFINERY_ULTIMATE;

	public static Block OXYGEN_COMPRESSOR_ADVANCED;
	public static Block OXYGEN_COMPRESSOR_ULTIMATE;

	public static Block SOLAR_PANEL;

	public static Block FUEL_LOADER_ADVANCED;
	public static Block FUEL_LOADER_ULTIMATE;

	public static Block BASIC_DECRYSALLIZER;
	public static Block BASIC_CRYSALLIZER;
	public static Block BASIC_SMASHER;
	public static Block BASIC_CHEMICAL_INJECTOR;
	public static Block BASIC_SOLAR_EVAPORTATION_CHAMBER;
	public static Block BASIC_PURIFIER;

	public static Block VEHICLE_CHARGER;

	public static Block BASIC_DENSIFIER;
	public static Block BASIC_DECONTAMINATION_UNIT;

	public static void init() {
		initializeMachinesBlocks();
		try {
			registerMachines();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		registerMachineTileEntitys();
	}

	private static void initializeMachinesBlocks() {
		if (Config.REFINERY_ADVANCED)
			REFINERY_ADVANCED = new AdvancedRefinery("advanced_refinery");
		if (Config.REFINERY_ULTIMATE)
			REFINERY_ULTIMATE = new UltimateRefinery("ultimate_refinery");
		if (Config.SOLAR_PANELS)
			SOLAR_PANEL = new BlockSolar("solar");
		if (Config.OXYGEN_COMPRESSOR_ADVANCED)
			OXYGEN_COMPRESSOR_ADVANCED = new AdvancedOxygenCompressor(false, "advanced_oxygen_compressor");
		if (Config.OXYGEN_COMPRESSOR_ULTIMATE)
			OXYGEN_COMPRESSOR_ULTIMATE = new UltimateOxygenCompressor(false, "ultimate_oxygen_compressor");
		if (Config.RADIATION) {
			if (Config.BASIC_DECRYSTALLIZER)
				BASIC_DECRYSALLIZER = new BasicDecrystallizer("basic_decrystallizer");
			if (Config.BASIC_CRYSTALLIZER)
				BASIC_CRYSALLIZER = new BasicCrystallizer("basic_crystallizer");
			if (Config.BASIC_SMASHER)
				BASIC_SMASHER = new BasicSmasher("basic_smasher");
			if (Config.BASIC_CHEMICAL_INJECTOR)
				BASIC_CHEMICAL_INJECTOR = new BasicChemicalInjector("basic_chemical_injector");
			if (Config.BASIC_SOLAR_EVAPORTATION_CHAMBER)
				BASIC_SOLAR_EVAPORTATION_CHAMBER = new BasicSolarEvaporationChamber("basic_solar_evaporation_chamber");
			if (Config.BASIC_PURIFIER)
				BASIC_PURIFIER = new BasicPurifier("basic_purifier");
			if (Config.BASIC_DECONTAMINATION_UNIT)
				BASIC_DECONTAMINATION_UNIT = new BasicDecontaminationUnit("basic_decontamination_unit");
		}
		if (Config.FUEL_LOADER_ADVANCED)
			FUEL_LOADER_ADVANCED = new AdvancedFuelLoader("advanced_fuel_loader");
		if (Config.FUEL_LOADER_ULTIMATE)
			FUEL_LOADER_ULTIMATE = new UltimateFuelLoader("ultimate_fuel_loader");
		if (Config.BASIC_DENSIFIER)
			BASIC_DENSIFIER = new BasicDensifier("basic_densifier");
		if (Config.VEHICLE_CHARGER)
			VEHICLE_CHARGER = new VehicleCharger("vehicle_charger");
	}

	private static void registerMachines() throws NoSuchMethodException {
		if (Config.REFINERY_ADVANCED)
			ExtraPlanets_Blocks.registerBlock(REFINERY_ADVANCED, ItemBlockDesc.class, "advanced_refinery");
		if (Config.REFINERY_ULTIMATE)
			ExtraPlanets_Blocks.registerBlock(REFINERY_ULTIMATE, ItemBlockDesc.class, "ultimate_refinery");
		if (Config.SOLAR_PANELS)
			ExtraPlanets_Blocks.registerBlock(SOLAR_PANEL, ItemBlockSolar.class, SOLAR_PANEL.getUnlocalizedName().substring(5));
		if (Config.OXYGEN_COMPRESSOR_ADVANCED)
			ExtraPlanets_Blocks.registerBlock(OXYGEN_COMPRESSOR_ADVANCED, ItemBlockAdvancedOxygenCompressor.class, OXYGEN_COMPRESSOR_ADVANCED.getUnlocalizedName().substring(5));
		if (Config.OXYGEN_COMPRESSOR_ULTIMATE)
			ExtraPlanets_Blocks.registerBlock(OXYGEN_COMPRESSOR_ULTIMATE, ItemBlockUltimateOxygenCompressor.class, OXYGEN_COMPRESSOR_ULTIMATE.getUnlocalizedName().substring(5));
		if (Config.RADIATION) {
			if (Config.BASIC_DECRYSTALLIZER)
				ExtraPlanets_Blocks.registerBlock(BASIC_DECRYSALLIZER, ItemBlockDesc.class, BASIC_DECRYSALLIZER.getUnlocalizedName().substring(5));
			if (Config.BASIC_CRYSTALLIZER)
				ExtraPlanets_Blocks.registerBlock(BASIC_CRYSALLIZER, ItemBlockDesc.class, BASIC_CRYSALLIZER.getUnlocalizedName().substring(5));
			if (Config.BASIC_SMASHER)
				ExtraPlanets_Blocks.registerBlock(BASIC_SMASHER, ItemBlockDesc.class, BASIC_SMASHER.getUnlocalizedName().substring(5));
			if (Config.BASIC_CHEMICAL_INJECTOR)
				ExtraPlanets_Blocks.registerBlock(BASIC_CHEMICAL_INJECTOR, ItemBlockDesc.class, BASIC_CHEMICAL_INJECTOR.getUnlocalizedName().substring(5));
			if (Config.BASIC_SOLAR_EVAPORTATION_CHAMBER)
				ExtraPlanets_Blocks.registerBlock(BASIC_SOLAR_EVAPORTATION_CHAMBER, ItemBlockDesc.class, BASIC_SOLAR_EVAPORTATION_CHAMBER.getUnlocalizedName().substring(5));
			if (Config.BASIC_PURIFIER)
				ExtraPlanets_Blocks.registerBlock(BASIC_PURIFIER, ItemBlockDesc.class, BASIC_PURIFIER.getUnlocalizedName().substring(5));
			if (Config.BASIC_DECONTAMINATION_UNIT)
				ExtraPlanets_Blocks.registerBlock(BASIC_DECONTAMINATION_UNIT, ItemBlockBasicDecontaminationUnit.class, BASIC_DECONTAMINATION_UNIT.getUnlocalizedName().substring(5));
		}
		if (Config.FUEL_LOADER_ADVANCED)
			ExtraPlanets_Blocks.registerBlock(FUEL_LOADER_ADVANCED, ItemBlockDesc.class, FUEL_LOADER_ADVANCED.getUnlocalizedName().substring(5));
		if (Config.FUEL_LOADER_ULTIMATE)
			ExtraPlanets_Blocks.registerBlock(FUEL_LOADER_ULTIMATE, ItemBlockDesc.class, FUEL_LOADER_ULTIMATE.getUnlocalizedName().substring(5));
		if (Config.BASIC_DENSIFIER)
			ExtraPlanets_Blocks.registerBlock(BASIC_DENSIFIER, ItemBlockDesc.class, BASIC_DENSIFIER.getUnlocalizedName().substring(5));
		if (Config.VEHICLE_CHARGER)
			ExtraPlanets_Blocks.registerBlock(VEHICLE_CHARGER, ItemBlockDesc.class, VEHICLE_CHARGER.getUnlocalizedName().substring(5));
	}

	private static void registerMachineTileEntitys() {
		if (Config.REFINERY_ADVANCED)
			RegisterUtilities.registerTileEntity(TileEntityAdvancedRefinery.class, Constants.modName + "AdvancedRefinery");
		if (Config.REFINERY_ULTIMATE)
			RegisterUtilities.registerTileEntity(TileEntityUltimateRefinery.class, Constants.modName + "UltimateRefinery");
		if (Config.SOLAR_PANELS)
			RegisterUtilities.registerTileEntity(TileEntitySolar.class, Constants.modName + "Solar Panel");
		if (Config.OXYGEN_COMPRESSOR_ADVANCED) {
			RegisterUtilities.registerTileEntity(TileEntityAdvancedOxygenCompressor.class, Constants.modName + "Advanced Oxygen Compressor");
			RegisterUtilities.registerTileEntity(TileEntityAdvancedOxygenDecompressor.class, Constants.modName + "Advanced Oxygen Decompressor");
		}
		if (Config.OXYGEN_COMPRESSOR_ULTIMATE) {
			RegisterUtilities.registerTileEntity(TileEntityUltimateOxygenCompressor.class, Constants.modName + "Ultimate Oxygen Compressor");
			RegisterUtilities.registerTileEntity(TileEntityUltimateOxygenDecompressor.class, Constants.modName + "Ultimate Oxygen Decompressor");
		}
		if (Config.RADIATION) {
			if (Config.BASIC_DECRYSTALLIZER)
				RegisterUtilities.registerTileEntity(TileEntityBasicDecrystallizer.class, Constants.modName + "Basic Decrystallizer");
			if (Config.BASIC_CRYSTALLIZER)
				RegisterUtilities.registerTileEntity(TileEntityBasicCrystallizer.class, Constants.modName + "Basic Crystallizer");
			if (Config.BASIC_SMASHER)
				RegisterUtilities.registerTileEntity(TileEntityBasicSmasher.class, Constants.modName + "Basic Smasher");
			if (Config.BASIC_CHEMICAL_INJECTOR)
				RegisterUtilities.registerTileEntity(TileEntityBasicChemicalInjector.class, Constants.modName + "Basic Chemical Injector");
			if (Config.BASIC_SOLAR_EVAPORTATION_CHAMBER)
				RegisterUtilities.registerTileEntity(TileEntityBasicSolarEvaporationChamber.class, Constants.modName + "Basic Solar Evaporation Chamber");
			if (Config.BASIC_PURIFIER)
				RegisterUtilities.registerTileEntity(TileEntityBasicPurifier.class, Constants.modName + "Basic Purifier");
			if (Config.BASIC_DECONTAMINATION_UNIT)
				RegisterUtilities.registerTileEntity(TileEntityBasicDecontaminationUnit.class, Constants.modName + "Basic Decontamination Unit");
		}
		if (Config.FUEL_LOADER_ADVANCED)
			RegisterUtilities.registerTileEntity(TileEntityAdvancedFuelLoader.class, Constants.modName + "AdvancedFuelLoader");
		if (Config.FUEL_LOADER_ULTIMATE)
			RegisterUtilities.registerTileEntity(TileEntityUltimateFuelLoader.class, Constants.modName + "UltimateFuelLoader");
		if (Config.BASIC_DENSIFIER)
			RegisterUtilities.registerTileEntity(TileEntityBasicDensifier.class, Constants.modName + "Basic Densifier");
		if (Config.VEHICLE_CHARGER)
			RegisterUtilities.registerTileEntity(TileEntityVehicleChanger.class, Constants.modName + "VehicleChanger");
	}
}
