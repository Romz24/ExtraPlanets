package com.mjr.extraplanets.jei;

import javax.annotation.Nonnull;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.blocks.machines.ExtraPlanets_Machines;
import com.mjr.extraplanets.jei.blocksmasher.BlockSmasherRecipeCategory;
import com.mjr.extraplanets.jei.blocksmasher.BlockSmasherRecipeHandler;
import com.mjr.extraplanets.jei.blocksmasher.BlockSmasherRecipeMaker;
import com.mjr.extraplanets.jei.chemicalInjector.ChemicalInjectorRecipeCategory;
import com.mjr.extraplanets.jei.chemicalInjector.ChemicalInjectorRecipeHandler;
import com.mjr.extraplanets.jei.chemicalInjector.ChemicalInjectorRecipeMaker;
import com.mjr.extraplanets.jei.crystallizer.CrystallizerRecipeCategory;
import com.mjr.extraplanets.jei.crystallizer.CrystallizerRecipeHandler;
import com.mjr.extraplanets.jei.crystallizer.CrystallizerRecipeMaker;
import com.mjr.extraplanets.jei.densifier.DensifierRecipeCategory;
import com.mjr.extraplanets.jei.densifier.DensifierRecipeHandler;
import com.mjr.extraplanets.jei.densifier.DensifierRecipeMaker;
import com.mjr.extraplanets.jei.purifier.PurifierRecipeCategory;
import com.mjr.extraplanets.jei.purifier.PurifierRecipeHandler;
import com.mjr.extraplanets.jei.purifier.PurifierRecipeMaker;
import com.mjr.extraplanets.jei.rockets.tier10.Tier10RocketRecipeCategory;
import com.mjr.extraplanets.jei.rockets.tier10.Tier10RocketRecipeHandler;
import com.mjr.extraplanets.jei.rockets.tier10.Tier10RocketRecipeMaker;
import com.mjr.extraplanets.jei.rockets.tier10Electric.Tier10ElectricRocketRecipeCategory;
import com.mjr.extraplanets.jei.rockets.tier10Electric.Tier10ElectricRocketRecipeHandler;
import com.mjr.extraplanets.jei.rockets.tier10Electric.Tier10ElectricRocketRecipeMaker;
import com.mjr.extraplanets.jei.rockets.tier4.Tier4RocketRecipeCategory;
import com.mjr.extraplanets.jei.rockets.tier4.Tier4RocketRecipeHandler;
import com.mjr.extraplanets.jei.rockets.tier4.Tier4RocketRecipeMaker;
import com.mjr.extraplanets.jei.rockets.tier5.Tier5RocketRecipeCategory;
import com.mjr.extraplanets.jei.rockets.tier5.Tier5RocketRecipeHandler;
import com.mjr.extraplanets.jei.rockets.tier5.Tier5RocketRecipeMaker;
import com.mjr.extraplanets.jei.rockets.tier6.Tier6RocketRecipeCategory;
import com.mjr.extraplanets.jei.rockets.tier6.Tier6RocketRecipeHandler;
import com.mjr.extraplanets.jei.rockets.tier6.Tier6RocketRecipeMaker;
import com.mjr.extraplanets.jei.rockets.tier7.Tier7RocketRecipeCategory;
import com.mjr.extraplanets.jei.rockets.tier7.Tier7RocketRecipeHandler;
import com.mjr.extraplanets.jei.rockets.tier7.Tier7RocketRecipeMaker;
import com.mjr.extraplanets.jei.rockets.tier8.Tier8RocketRecipeCategory;
import com.mjr.extraplanets.jei.rockets.tier8.Tier8RocketRecipeHandler;
import com.mjr.extraplanets.jei.rockets.tier8.Tier8RocketRecipeMaker;
import com.mjr.extraplanets.jei.rockets.tier9.Tier9RocketRecipeCategory;
import com.mjr.extraplanets.jei.rockets.tier9.Tier9RocketRecipeHandler;
import com.mjr.extraplanets.jei.rockets.tier9.Tier9RocketRecipeMaker;
import com.mjr.extraplanets.jei.solarEvaporationChamber.SolarEvaporationChamberRecipeCategory;
import com.mjr.extraplanets.jei.solarEvaporationChamber.SolarEvaporationChamberRecipeHandler;
import com.mjr.extraplanets.jei.solarEvaporationChamber.SolarEvaporationChamberRecipeMaker;
import com.mjr.extraplanets.jei.vehicles.marsRover.MarsRoverRecipeCategory;
import com.mjr.extraplanets.jei.vehicles.marsRover.MarsRoverRecipeHandler;
import com.mjr.extraplanets.jei.vehicles.marsRover.MarsRoverRecipeMaker;
import com.mjr.extraplanets.jei.vehicles.venusRover.VenusRoverRecipeCategory;
import com.mjr.extraplanets.jei.vehicles.venusRover.VenusRoverRecipeHandler;
import com.mjr.extraplanets.jei.vehicles.venusRover.VenusRoverRecipeMaker;

import net.minecraft.item.ItemStack;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import micdoodle8.mods.galacticraft.core.GCBlocks;

@JEIPlugin
public class ExtraPlanetsJEI extends BlankModPlugin {
	@Override
	public void register(@Nonnull IModRegistry registry) {
		if (Config.JEI_SUPPORT) {
			IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
			registry.addRecipeCategories(new Tier4RocketRecipeCategory(guiHelper), new Tier5RocketRecipeCategory(guiHelper), new Tier6RocketRecipeCategory(guiHelper), new Tier7RocketRecipeCategory(guiHelper), new Tier8RocketRecipeCategory(guiHelper),
					new Tier9RocketRecipeCategory(guiHelper), new Tier10RocketRecipeCategory(guiHelper), new MarsRoverRecipeCategory(guiHelper), new VenusRoverRecipeCategory(guiHelper), new Tier10ElectricRocketRecipeCategory(guiHelper));
			if (Config.RADIATION)
				registry.addRecipeCategories(new BlockSmasherRecipeCategory(guiHelper), new SolarEvaporationChamberRecipeCategory(guiHelper), new ChemicalInjectorRecipeCategory(guiHelper), new CrystallizerRecipeCategory(guiHelper),
						new PurifierRecipeCategory(guiHelper), new DensifierRecipeCategory(guiHelper));

			registry.addRecipeHandlers(new Tier4RocketRecipeHandler(), new Tier5RocketRecipeHandler(), new Tier6RocketRecipeHandler(), new Tier7RocketRecipeHandler(), new Tier8RocketRecipeHandler(), new Tier9RocketRecipeHandler(),
					new Tier10RocketRecipeHandler(), new MarsRoverRecipeHandler(), new VenusRoverRecipeHandler(), new Tier10ElectricRocketRecipeHandler());

			if (Config.RADIATION)
				registry.addRecipeHandlers(new BlockSmasherRecipeHandler(), new SolarEvaporationChamberRecipeHandler(), new ChemicalInjectorRecipeHandler(), new CrystallizerRecipeHandler(), new PurifierRecipeHandler(), new DensifierRecipeHandler());
			registry.addRecipes(Tier4RocketRecipeMaker.getRecipesList());
			registry.addRecipes(Tier5RocketRecipeMaker.getRecipesList());
			registry.addRecipes(Tier6RocketRecipeMaker.getRecipesList());
			registry.addRecipes(Tier7RocketRecipeMaker.getRecipesList());
			registry.addRecipes(Tier8RocketRecipeMaker.getRecipesList());
			registry.addRecipes(Tier9RocketRecipeMaker.getRecipesList());
			registry.addRecipes(Tier10RocketRecipeMaker.getRecipesList());
			registry.addRecipes(Tier10ElectricRocketRecipeMaker.getRecipesList());
			registry.addRecipes(MarsRoverRecipeMaker.getRecipesList());
			registry.addRecipes(VenusRoverRecipeMaker.getRecipesList());
			if (Config.RADIATION) {
				registry.addRecipes(BlockSmasherRecipeMaker.getRecipesList());
				registry.addRecipes(SolarEvaporationChamberRecipeMaker.getRecipesList());
				registry.addRecipes(ChemicalInjectorRecipeMaker.getRecipesList());
				registry.addRecipes(CrystallizerRecipeMaker.getRecipesList());
				registry.addRecipes(PurifierRecipeMaker.getRecipesList());
				registry.addRecipes(DensifierRecipeMaker.getRecipesList());
			}

			ItemStack nasaWorkbench = new ItemStack(GCBlocks.nasaWorkbench);
			registry.addRecipeCategoryCraftingItem(nasaWorkbench, RecipeCategories.ROCKET_T4_ID);
			registry.addRecipeCategoryCraftingItem(nasaWorkbench, RecipeCategories.ROCKET_T5_ID);
			registry.addRecipeCategoryCraftingItem(nasaWorkbench, RecipeCategories.ROCKET_T6_ID);
			registry.addRecipeCategoryCraftingItem(nasaWorkbench, RecipeCategories.ROCKET_T7_ID);
			registry.addRecipeCategoryCraftingItem(nasaWorkbench, RecipeCategories.ROCKET_T8_ID);
			registry.addRecipeCategoryCraftingItem(nasaWorkbench, RecipeCategories.ROCKET_T9_ID);
			registry.addRecipeCategoryCraftingItem(nasaWorkbench, RecipeCategories.ROCKET_T10_ID);
			registry.addRecipeCategoryCraftingItem(nasaWorkbench, RecipeCategories.ROCKET_T10_ELECTRIC_ID);
			registry.addRecipeCategoryCraftingItem(nasaWorkbench, RecipeCategories.MARS_ROVER_ID);
			registry.addRecipeCategoryCraftingItem(nasaWorkbench, RecipeCategories.VENUS_ROVER_ID);
			if (Config.RADIATION) {
				registry.addRecipeCategoryCraftingItem(new ItemStack(ExtraPlanets_Machines.BASIC_CHEMICAL_INJECTOR), RecipeCategories.CHEMAICAL_INJECTOR_ID);
				registry.addRecipeCategoryCraftingItem(new ItemStack(ExtraPlanets_Machines.BASIC_CRYSALLIZER), RecipeCategories.CRYSTALLIZER_ID);
				registry.addRecipeCategoryCraftingItem(new ItemStack(ExtraPlanets_Machines.BASIC_DENSIFIER), RecipeCategories.DENSIFIER_ID);
				registry.addRecipeCategoryCraftingItem(new ItemStack(ExtraPlanets_Machines.BASIC_PURIFIER), RecipeCategories.PURIFIER_ID);
				registry.addRecipeCategoryCraftingItem(new ItemStack(ExtraPlanets_Machines.BASIC_SMASHER), RecipeCategories.BLOCK_SMASHER_ID);
				registry.addRecipeCategoryCraftingItem(new ItemStack(ExtraPlanets_Machines.BASIC_SOLAR_EVAPORTATION_CHAMBER), RecipeCategories.SOLAR_EVAPORTATION_CHAMBER_ID);
			}
		}
	}
}